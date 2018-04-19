import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements Server, Runnable {

    private final int DEFAULT_PORT = 30000;
    private int port;
    private static Map<String, ClientSocket> clientsMap;
    private final int MAX_NUM_OF_THREADS = 20;
    ExecutorService executorService;

    public ChatServer(int port) {
        if (port == 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = port;
        }
        clientsMap = new ConcurrentHashMap<String, ClientSocket>();
        executorService = Executors.newFixedThreadPool(MAX_NUM_OF_THREADS);
    }

    public void addClientIntoMap(String clientNickName, ClientSocket clientSocket) {
        if (clientsMap.containsKey(clientNickName)) {
            System.out.println("Client exists, please choose another nick name");
        }
        clientsMap.put(clientNickName, clientSocket);
    }

    public void removeClientFromMap(String clientNickName) {
        clientsMap.remove(clientNickName);
    }


    public String listAllClientsRegistered() {
        StringBuilder listofEntries = new StringBuilder();
        listofEntries.append("List of clients currently online:");

        for (Map.Entry<String, ClientSocket> e: clientsMap.entrySet()) {
            listofEntries.append("\n" + e.getKey());
        }
        return listofEntries.toString();
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
                executorService.submit(new ChatServerWorker(new ClientSocket(incomingConnection), this));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
