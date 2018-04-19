import java.io.*;
import java.net.ServerSocket;

public class ChatServerWorker implements Runnable, ServerWorker {

    ClientSocket clientSocket;
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
        if (server.lookupClient(clientNickName) == null) {
            server.addClientIntoMap(clientNickName, clientSocket);
            return true;
        }
        return false;
    }

    public void run() {

        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            Message messageReceivedFromClient;

            while (true) {
                Message msg = new TextMessage("Please choose a nickname: ");
                clientSocket.sendAMessageThroughSocket(msg);
                String clientNickName;

                if ((clientNickName = readFromClient.readLine()) != null) {

                    if (register(clientNickName, clientSocket)) {
                        break;
                    }
                    else {
                        msg = new TextMessage("Nickname exists in the database, please choose another nickname");
                        clientSocket.sendAMessageThroughSocket(msg);
                    }
                }
            }

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
            clientSocket.sendAMessageThroughSocket(msg);

            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            Message clientNickName = new TextMessage(readFromClient.readLine());

            msg = new TextMessage("Please write a message:");
            clientSocket.sendAMessageThroughSocket(msg);

            Message message = new TextMessage(readFromClient.readLine());
            send(clientNickName.toString(), message);

            msg = new TextMessage("Message sent to " + clientNickName.toString());
            clientSocket.sendAMessageThroughSocket(msg);

        }
    }
}

