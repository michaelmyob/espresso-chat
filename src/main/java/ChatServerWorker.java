import java.io.*;
import java.net.ServerSocket;
import java.util.Map;

public class ChatServerWorker implements Runnable, ServerWorker {

    ClientSocket clientSocket;
    String connectedClientsNickname;
    ChatServer server;

    public ChatServerWorker(ClientSocket clientSocket, ChatServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    private boolean send(String clientName, Message message) {
        ClientSocket clientSocket = server.lookupClient(clientName);
        clientSocket.sendAMessageThroughSocket(message);
        return true;
    }

    private synchronized boolean register(String clientNickName, ClientSocket clientSocket) {
        if (server.lookupClient(clientNickName) == null && !clientNickName.isEmpty()) {
            server.addClientIntoMap(clientNickName, clientSocket);
            return true;
        }
        return false;
    }

    private synchronized boolean remove(String clientNickName) {
        if (server.lookupClient(clientNickName) != null) {
            server.removeClientFromMap(clientNickName);
            return true;
        }
        return false;
    }

    public void run() {

        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            signup(readFromClient);
            Message messageReceivedFromClient;

            Message msg = new TextMessage("Please choose from options below:");
            clientSocket.sendAMessageThroughSocket(msg);
            clientSocket.sendAMessageThroughSocket(new TextMessage(displayOptions()));

            while (true) {
                if (!clientSocket.getSocket().isClosed() && clientSocket.getSocket() != null) {

                    messageReceivedFromClient = new TextMessage(readFromClient.readLine());

                    processClientsSelection(messageReceivedFromClient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            remove(connectedClientsNickname);
        }
    }

    private void signup(BufferedReader readFromClient) throws IOException {
        boolean isSignedUp = false;
        while (!isSignedUp) {
            Message msg = new TextMessage("Please choose a nickname: ");
            clientSocket.sendAMessageThroughSocket(msg);
            String clientNickName = readFromClient.readLine();

            isSignedUp = register(clientNickName, clientSocket);

            if (isSignedUp) {
                connectedClientsNickname = clientNickName;
            }
            else {
                msg = new TextMessage("Nickname exists in the database, please choose another nickname");
                clientSocket.sendAMessageThroughSocket(msg);
            }
        }
    }

    private String displayOptions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[1] List all clients online\n");
        stringBuilder.append("[2] Send a message\n");
        stringBuilder.append("[3] Quit");
        return stringBuilder.toString();
    }

    private void processClientsSelection(Message messageReceivedFromClient) throws IOException {

        if (messageReceivedFromClient.toString().equals("1")) {
            Message list = new TextMessage(server.listAllClientsRegistered());
            clientSocket.sendAMessageThroughSocket(list);


        } else if (messageReceivedFromClient.toString().equals("2")) {
            Message msg = new TextMessage("Please enter a client name:");
            clientSocket.sendAMessageThroughSocket(msg);

            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            Message clientNickName = new TextMessage(readFromClient.readLine());

            msg = new TextMessage("Please write a message:");
            clientSocket.sendAMessageThroughSocket(msg);

            String messageToBeSent = readFromClient.readLine();
            Message message = new TextMessage(connectedClientsNickname + " says: " + messageToBeSent);
            send(clientNickName.toString(), message);

            msg = new TextMessage("Message '" + messageToBeSent + "' was sent to " + clientNickName.toString() + ".");
            clientSocket.sendAMessageThroughSocket(msg);

        } else if (messageReceivedFromClient.toString().equals("3")) {
            Message msg = new TextMessage("Thank you for using Espresso Chat.\nQuitting now...");
            clientSocket.sendAMessageThroughSocket(msg);

            remove(connectedClientsNickname);
            clientSocket.getSocket().close();

        }
    }
}

