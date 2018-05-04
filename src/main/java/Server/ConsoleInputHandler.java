package Server;

import Comms.MessageChannel;
import Interfaces.DataStoreHandler;
import Interfaces.InputHandler;
import Interfaces.MessageSender;
import Interfaces.Message;
import Message.TextMessage;

import java.io.*;
import java.util.List;

public class ConsoleInputHandler implements Runnable, InputHandler {

    private final String SERVER_MENU_OPTION_1 = "LIST";
    private final String SERVER_MENU_OPTION_2 = "SEND";
    private final String SERVER_MENU_OPTION_3 = "QUIT";

    private MessageChannel messageChannel;
    private String connectedClientsNickname;
    private DataStoreHandler hashMapDataStoreHandler;
    private MessageSender messageSender;

    public ConsoleInputHandler(MessageChannel messageChannel, DataStoreHandler hashMapDataStoreHandler, MessageSender messageSender) {
        this.messageChannel = messageChannel;
        this.hashMapDataStoreHandler = hashMapDataStoreHandler;
        this.connectedClientsNickname = messageChannel.clientNickName;
        this.messageSender = messageSender;
    }

    public void run() {

        TextMessage deserialisedMessage;
        String messageReceivedFromClient;

        try {
            sendResponse("Please choose from options below:");
            sendResponse(displayOptions());

            while (true) {

                if (!messageChannel.getSocket().isClosed() && messageChannel.getSocket() != null) {

                    deserialisedMessage = (TextMessage) messageChannel.getInputStream().readObject();

                    messageReceivedFromClient = deserialisedMessage.messageContents;

                    System.out.println(messageReceivedFromClient);
                    processClientsSelection(messageReceivedFromClient);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            hashMapDataStoreHandler.removeClient(messageChannel);
            Thread.currentThread().interrupt();
            messageChannel.closeStreams();

            sendResponse("shutting down now...");
            System.exit(0);

            // TODO - Fix this quitting the thread @ the server level
        }
    }

    private String displayOptions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" + SERVER_MENU_OPTION_1 + ": List all clients online\n");
        stringBuilder.append(SERVER_MENU_OPTION_2 + ": Send a message\n");
        stringBuilder.append(SERVER_MENU_OPTION_3 + ": Quit");
        return stringBuilder.toString();
    }


    private String listAllClientsOnline() {
        List<String> clientsList = hashMapDataStoreHandler.getAllClients();
        clientsList.remove(connectedClientsNickname);
        StringBuilder result = new StringBuilder();

        if (clientsList.size() == 0) {
            result.append("There are no other clients online at the moment!" + "\n");
        }
        else {
            result.append("List of online clients: \n");
            for (String client : clientsList) {
                result.append(client + "\n");
            }
        }
        return result.toString();
    }

    private void processClientsSelection(String messageReceivedFromClient) throws IOException, ClassNotFoundException {

        String clientResponse = messageReceivedFromClient.toUpperCase();

        if (clientResponse.equals(SERVER_MENU_OPTION_1)) {
            sendResponse(listAllClientsOnline());

        } else if (clientResponse.equals(SERVER_MENU_OPTION_2)) {
            sendResponse("Please enter a client name:");

            TextMessage clientNickNameResponse = (TextMessage)messageChannel.getInputStream().readObject();
            String clientNickName = clientNickNameResponse.messageContents;

            sendResponse("Please write a message:");

            TextMessage clientMsgToBeSentResponse = (TextMessage)messageChannel.getInputStream().readObject();
            String messageToBeSent = clientMsgToBeSentResponse.messageContents;

            MessageChannel destinationMessageChannel = hashMapDataStoreHandler.getClient(clientNickName);
            Message destinationMessage = new TextMessage(messageChannel.clientNickName, messageToBeSent);
            messageSender.send(destinationMessage, destinationMessageChannel);
            sendResponse("Message '" + messageToBeSent + "' was sent to " + destinationMessageChannel.clientNickName);

        } else if (clientResponse.equals(SERVER_MENU_OPTION_3)) {
            sendResponse("Thank you for using Espresso Chat.\nQuitting now...");
            hashMapDataStoreHandler.removeClient(messageChannel);

            messageChannel.closeStreams();
            messageChannel.getSocket().close();
        }
        else {
            sendResponse("Invalid option selected, please try again!\n");
        }
    }

    private void sendResponse(String message) {
        Message msg = new TextMessage("server", message);
        messageSender.send(msg, messageChannel);

    }
}

