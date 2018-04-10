
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        }
        else {
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


    private boolean register() {
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
            String clientSocketAddress = (String)clientsMap.get(clientName);
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
                System.out.println("New socket connection accepted");
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
