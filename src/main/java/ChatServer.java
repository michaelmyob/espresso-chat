
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements Server, Runnable {

    private final String DEFAULT_IP_ADDRESS = "localhost";
    private final int DEFAULT_PORT = 30000;
    private ExecutorService executorService = Executors.newFixedThreadPool(12);
    private String IPaddress;
    private int port;
    private static Map clientsMap;

    public void addClientIntoMap(String clientNickName, ClientSocket clientSocket) {
        if (clientsMap.containsKey(clientNickName)) {
            System.out.println("Client exists, please choose another nick name");
        }
        clientsMap.put(clientNickName, clientSocket);
    }

    public ChatServer(int port) {
        if (port == 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = port;
        }
        this.IPaddress = DEFAULT_IP_ADDRESS;
        clientsMap = new ConcurrentHashMap<String, ClientSocket>();
    }

    public ClientSocket lookupClient(String clientName) {
        if (clientsMap.containsKey(clientName)) {
            return (ClientSocket) clientsMap.get(clientName);

        } else {
            return null;
        }
    }


    public void run() {

        System.out.println("Server is running and ready for chatting...");

        try (ServerSocket socket = new ServerSocket(port)) {

            while (true) {

                Socket incomingConnection = socket.accept();
                new Thread(new ChatServerWorker(new ClientSocket(incomingConnection), this)).start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
