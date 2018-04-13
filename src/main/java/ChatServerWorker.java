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

        try {
            InetSocketAddress address = server.lookupClient(clientName);
            Socket destinationSocket = new Socket(address.getHostName(), address.getPort());
            ChatUtilities.sendAMessageThroughSocket(destinationSocket, message);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void run() {

        try {
            InputStream inputStream = connectionSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            Message messageReceivedFromClient, messageSentToClient, clientNickName;


            while (true) {
                if (!connectionSocket.isClosed() && connectionSocket != null) {

                    Message msg = new TextMessage("Hooray, client is now connected and registered! Please choose from options below:");
                    ChatUtilities.sendAMessageThroughSocket(connectionSocket, msg);
                    ChatUtilities.sendAMessageThroughSocket(connectionSocket, new TextMessage(displayOptions()));

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
        stringBuilder.append("[3] Quit\n");
        return stringBuilder.toString();
    }

    private void processClientsSelection(Message messageReceivedFromClient) throws IOException{
        if (messageReceivedFromClient.equals("1")) {

        } else if (messageReceivedFromClient.equals("2")) {
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

