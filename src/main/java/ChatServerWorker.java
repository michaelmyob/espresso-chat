import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatServerWorker implements Runnable {

    //        ServerSocket socket = new ServerSocket(this.getPort());
//        Socket connectionSocket = socket.accept();
    Socket connectionSocket;
    ChatServer server;

    public ChatServerWorker(Socket connectionSocket, ChatServer server) throws IOException {
        this.connectionSocket = connectionSocket;
        this.server = server;
    }

    private boolean send(String clientName, Message message) {
        Socket clientSocket = server.lookupClient(clientName);
        ChatUtilities.sendAMessageThroughSocket(clientSocket, message);
        return true;
    }

    public synchronized boolean register(String clientNickName, Socket clientSocket) {
        if (server.lookupClient(clientNickName) == null) {
            server.addClientIntoMap(clientNickName, clientSocket);
            return true;
        }
        return false;
    }

    public void run() {

        try {
            InputStream inputStream = connectionSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            Message messageReceivedFromClient;

            while (true) {
                Message msg = new TextMessage("Please choose a nickname: ");
                ChatUtilities.sendAMessageThroughSocket(connectionSocket, msg);
                String clientNickName;

                if ((clientNickName = readFromClient.readLine()) != null) {

                    if (register(clientNickName, connectionSocket)) {
                        break;
                    }
                    else {
                        msg = new TextMessage("Nickname exists in the database, please choose another nickname");
                        ChatUtilities.sendAMessageThroughSocket(connectionSocket, msg);
                    }
                }
            }

            Message msg = new TextMessage("Please choose from options below:");
            ChatUtilities.sendAMessageThroughSocket(connectionSocket, msg);
            ChatUtilities.sendAMessageThroughSocket(connectionSocket, new TextMessage(displayOptions()));

            while (true) {
                if (!connectionSocket.isClosed() && connectionSocket != null) {

                    messageReceivedFromClient = new TextMessage(readFromClient.readLine());

                    processClientsSelection(messageReceivedFromClient);


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //TODO - remove client from hashmap if the connection dies etc
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

        } else if (messageReceivedFromClient.toString().equals("2")) {
            Message msg = new TextMessage("Please enter a client name:");
            ChatUtilities.sendAMessageThroughSocket(connectionSocket, msg);

            InputStream inputStream = connectionSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            Message clientNickName = new TextMessage(readFromClient.readLine());

            msg = new TextMessage("Please write a message:");
            ChatUtilities.sendAMessageThroughSocket(connectionSocket, msg);
            Message message = new TextMessage(readFromClient.readLine());
            send(clientNickName.toString(), message);
        }
    }
}

