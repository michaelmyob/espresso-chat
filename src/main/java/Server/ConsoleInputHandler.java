package Server;

import Channel.MessageChannel;
import Interfaces.InputHandler;
import Data.HashMapDataStoreHandler;

import java.io.*;
import java.util.List;

public class ConsoleInputHandler implements Runnable, InputHandler {

    private final String SERVER_MENU_OPTION_1 = "LIST";
    private final String SERVER_MENU_OPTION_2 = "SEND";
    private final String SERVER_MENU_OPTION_3 = "QUIT";

    MessageChannel messageChannel;
    String connectedClientsNickname;
    HashMapDataStoreHandler hashmapDatastoreHandler;

    public ConsoleInputHandler(MessageChannel messageChannel, HashMapDataStoreHandler hashmapDatastoreHandler) {
        this.messageChannel = messageChannel;
        this.hashmapDatastoreHandler = hashmapDatastoreHandler;
        this.connectedClientsNickname = messageChannel.clientNickName;
    }

    private void send(String clientName, String message) {
        MessageChannel destinationMessageChannel = hashmapDatastoreHandler.getClient(clientName);
        if (destinationMessageChannel != null) {
            String destinationMessage = connectedClientsNickname + " says: " + message;
            destinationMessageChannel.sendATextMessage(destinationMessage);
            messageChannel.sendATextMessage("Message '" + message + "' was sent to " + clientName + ".");
        }
        else {
            messageChannel.sendATextMessage(clientName + " was not found, please choose an online client. Use command LIST to see all clients online");
        }
    }

    public void run() {

        try {
            InputStream inputStream = messageChannel.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            String messageReceivedFromClient;

            messageChannel.sendATextMessage("Please choose from options below:");
            messageChannel.sendATextMessage(displayOptions());

            while (true) {
                if (!messageChannel.getSocket().isClosed() && messageChannel.getSocket() != null) {

                    messageReceivedFromClient = readFromClient.readLine();

                    processClientsSelection(messageReceivedFromClient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            hashmapDatastoreHandler.removeClient(connectedClientsNickname);
            Thread.currentThread().interrupt();
            messageChannel.sendATextMessage("shutting down now...");
            System.exit(0);
            // TODO - Fix this quitting the thread @ the server level
        }
    }

    private String displayOptions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVER_MENU_OPTION_1 + ": List all clients online\n");
        stringBuilder.append(SERVER_MENU_OPTION_2 + ": Send a message\n");
        stringBuilder.append(SERVER_MENU_OPTION_3 + ": Quit");
        return stringBuilder.toString();
    }


    private String listAllClientsOnline() {
        List<String> clientsList = hashmapDatastoreHandler.getAllClients();
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

    private void processClientsSelection(String messageReceivedFromClient) throws IOException {

        String clientResponse = messageReceivedFromClient.toUpperCase();

        if (clientResponse.equals(SERVER_MENU_OPTION_1)) {
            messageChannel.sendATextMessage(listAllClientsOnline());

        } else if (clientResponse.equals(SERVER_MENU_OPTION_2)) {
            messageChannel.sendATextMessage("Please enter a client name:");

            InputStream inputStream = messageChannel.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            String clientNickName = readFromClient.readLine();
            messageChannel.sendATextMessage("Please write a message:");

            String messageToBeSent = readFromClient.readLine();
            send(clientNickName, messageToBeSent);

        } else if (clientResponse.equals(SERVER_MENU_OPTION_3)) {
            messageChannel.sendATextMessage("Thank you for using Espresso Chat.\nQuitting now...");
            hashmapDatastoreHandler.removeClient(connectedClientsNickname);
            messageChannel.getSocket().close();

        }
        else {
            messageChannel.sendATextMessage("Invalid option selected, please try again!\n");
        }
    }
}

