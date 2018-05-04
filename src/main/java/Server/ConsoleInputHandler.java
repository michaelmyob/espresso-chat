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

    MessageChannel messageChannel;
    String connectedClientsNickname;
    DataStoreHandler hashMapDataStoreHandler;
    MessageSender messageSender;

    public ConsoleInputHandler(MessageChannel messageChannel, DataStoreHandler hashMapDataStoreHandler, MessageSender messageSender) {
        this.messageChannel = messageChannel;
        this.hashMapDataStoreHandler = hashMapDataStoreHandler;
        this.connectedClientsNickname = messageChannel.clientNickName;
        this.messageSender = messageSender;
    }

//    private void send(String clientName, String message) {
//        MessageChannel destinationMessageChannel = hashMapDataStoreHandler.getClient(clientName);
//        if (destinationMessageChannel != null) {
//            String destinationMessage = connectedClientsNickname + " says: " + message;
//            destinationMessageChannel.sendATextMessage(destinationMessage);
//            sendResponse("Message '" + message + "' was sent to " + clientName + ".");
//        }
//        else {
//            sendResponse(clientName + " was not found, please choose an online client. Use command LIST to see all clients online");
//        }
//    }

    public void run() {

        TextMessage deserialisedMessage = null;
        String messageReceivedFromClient;

        try {
            sendResponse("Please choose from options below:");
            sendResponse(displayOptions());

            System.out.println("[DEBUG] STARTING WHILE TRUE NOW:");
            while (true) {

//                System.out.println("[DEBUG] messageChannel.getSocket().isClosed(): " + messageChannel.getSocket().isClosed());
//                System.out.println("[DEBUG] mmessageChannel.getSocket(): " + messageChannel.getSocket());
//                System.out.println();

                if (!messageChannel.getSocket().isClosed() && messageChannel.getSocket() != null) {

                    deserialisedMessage = (TextMessage) messageChannel.getInputStream().readObject();
                    System.out.println("[DEBUG] deserialisedMessage: " + deserialisedMessage.messageContents);

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

            InputStream inputStream = messageChannel.getInputStream();
//            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

//            String clientNickName = readFromClient.readLine();
            TextMessage clientNickNameResponse = (TextMessage)messageChannel.getInputStream().readObject();
            String clientNickName = clientNickNameResponse.messageContents;

            sendResponse("Please write a message:");

//            String messageToBeSent = readFromClient.readLine();
            TextMessage clientMsgToBeSentResponse = (TextMessage)messageChannel.getInputStream().readObject();
            String messageToBeSent = clientMsgToBeSentResponse.messageContents;

            MessageChannel destinationMessageChannel = hashMapDataStoreHandler.getClient(clientNickName);
            Message destinationMessage = new TextMessage(messageChannel.clientNickName, messageToBeSent);
            messageSender.send(destinationMessage, destinationMessageChannel);

        } else if (clientResponse.equals(SERVER_MENU_OPTION_3)) {
            sendResponse("Thank you for using Espresso Chat.\nQuitting now...");
            hashMapDataStoreHandler.removeClient(messageChannel);
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

