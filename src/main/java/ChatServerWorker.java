import java.io.*;
import java.util.List;

public class ChatServerWorker implements Runnable, ServerWorker {

    private final String SERVER_MENU_OPTION_1 = "LIST";
    private final String SERVER_MENU_OPTION_2 = "SEND";
    private final String SERVER_MENU_OPTION_3 = "QUIT";

    ClientSocket clientSocket;
    String connectedClientsNickname;
    MapDataStorage mapDataStorage;

    public ChatServerWorker(ClientSocket clientSocket, MapDataStorage mapDataStorage) {
        this.clientSocket = clientSocket;
        this.mapDataStorage = mapDataStorage;
        this.connectedClientsNickname = clientSocket.clientNickName;
    }

    private void send(String clientName, String message) {
        ClientSocket clientSocket = mapDataStorage.getClient(clientName);
        clientSocket.sendATextMessage(message);
    }

    public void run() {

        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

//            signup(readFromClient);
            String messageReceivedFromClient;

            clientSocket.sendATextMessage("Please choose from options below:");
            clientSocket.sendATextMessage(displayOptions());

            while (true) {
                if (!clientSocket.getSocket().isClosed() && clientSocket.getSocket() != null) {

                    messageReceivedFromClient = readFromClient.readLine();

                    processClientsSelection(messageReceivedFromClient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mapDataStorage.removeClient(connectedClientsNickname);
            Thread.currentThread().interrupt();
            clientSocket.sendATextMessage("shutting down now...");
            System.exit(0);
            // TODO - Fix this quitting the thread @ the server level
        }
    }

//    private void signup(BufferedReader readFromClient) throws IOException {
//        boolean isSignedUp = false;
//        while (!isSignedUp) {
//            clientSocket.sendATextMessage("Please choose a nickname: ");
//            String clientNickName = readFromClient.readLine();
//
//            isSignedUp = mapDataStorage.addClient(clientNickName, clientSocket);
//
//            if (isSignedUp) {
//                connectedClientsNickname = clientNickName;
//            } else {
//                clientSocket.sendATextMessage("Nickname exists in the database, please choose another nickname");
//            }
//        }
//    }

    private String displayOptions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SERVER_MENU_OPTION_1 + ": List all clients online\n");
        stringBuilder.append(SERVER_MENU_OPTION_2 + ": Send a message\n");
        stringBuilder.append(SERVER_MENU_OPTION_3 + ": Quit");
        return stringBuilder.toString();
    }


    private String listAllClientsOnline() {
        List<String> clientsList = mapDataStorage.getAllClients();
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
            clientSocket.sendATextMessage(listAllClientsOnline());

        } else if (clientResponse.equals(SERVER_MENU_OPTION_2)) {
            clientSocket.sendATextMessage("Please enter a client name:");

            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            String clientNickName = readFromClient.readLine();
            clientSocket.sendATextMessage("Please write a message:");

            String messageToBeSent = readFromClient.readLine();
            String destinationMessage = connectedClientsNickname + " says: " + messageToBeSent;
            send(clientNickName, destinationMessage);

            String confirmationMessage =  "Message '" + messageToBeSent + "' was sent to " + clientNickName + ".";
            clientSocket.sendATextMessage(confirmationMessage);

        } else if (clientResponse.equals(SERVER_MENU_OPTION_3)) {
            clientSocket.sendATextMessage("Thank you for using Espresso Chat.\nQuitting now...");
            mapDataStorage.removeClient(connectedClientsNickname);
            clientSocket.getSocket().close();

        }
        else {
            clientSocket.sendATextMessage("Invalid option selected, please try again!\n");
        }
    }
}

