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
    private boolean isRunning = true;

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

            while (isRunning) {
                deserialisedMessage = (TextMessage) messageChannel.getInputStream().readObject();
                messageReceivedFromClient = deserialisedMessage.messageContents;
                processClientsSelection(messageReceivedFromClient);
            }
        } catch (IOException e) {
            //TODO - add logger here - client has disconnected
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();
            hashMapDataStoreHandler.removeClient(messageChannel);
            messageChannel.closeConnection();
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
            isRunning = false;
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

