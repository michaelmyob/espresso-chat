
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer implements Server, Runnable {

    private final String DEFAULT_IP_ADDRESS = "localhost";
    private final int DEFAULT_PORT = 30000;

    private String IPaddress;
    private int port;
    private Map clientsMap;

    public ChatServer(int port) {
        if (port == 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = port;
        }
        this.IPaddress = DEFAULT_IP_ADDRESS;
        clientsMap = new ConcurrentHashMap();
    }

    public String getIP() {
        return IPaddress;
    }

    public int getPort() {
        return port;
    }

    public Message respond(Client client, Message message) {
        return message;
    }


    public boolean register(String clientNickName, InetSocketAddress clientSocketAddress) {
        if (clientsMap.containsKey(clientNickName)) {
            System.out.println("Client exists, please choose another nick name");
            return false;
        }
        clientsMap.put(clientNickName, clientSocketAddress);
        return true;
    }

    public void listen(int port) throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        System.out.println("Server is up...");


        socket.close();

    }

    public boolean send(String clientName, Message message) {

        if (clientsMap.containsKey(clientName)) {
            String clientSocketAddress = (String) clientsMap.get(clientName);
        } else {
            System.out.println("Error :( Client not found");
            return false;
        }

        return true;
    }


    public void run() {

        System.out.println("Server is running and ready for chatting...");

        ServerSocket socket = null;
        try {
            socket = new ServerSocket(this.getPort());
        } catch (IOException e) {

        }
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = socket.accept();

                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

                String messageReceivedFromClient, messageSentToClient, clientNickName;

                while (true) {
                    Message msg = new TextMessage("Please choose a nickname: ");
                    ChatUtilities.sendAMessageThroughSocket(clientSocket, msg);

                    if ((clientNickName = readFromClient.readLine()) != null) {

                        InetSocketAddress addressToStore =
                                new InetSocketAddress(clientSocket.getInetAddress(), clientSocket.getPort());
                        if (register(clientNickName, addressToStore)) {
                            break;
                        }
                        else {
                            msg = new TextMessage("Nickname exists in the database, please choose another nickname");
                            ChatUtilities.sendAMessageThroughSocket(clientSocket, msg);
                        }
                    }
                }


            } catch (IOException e) {

                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

            try {
                new Thread(new ServerWorker(clientSocket)).start();
            } catch (IOException e) {

            }
        }
    }


}
