import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements Server, Runnable {

    private final int DEFAULT_PORT = 30000;
    private int port;
    private final int MAX_NUM_OF_THREADS = 20;
    ExecutorService executorService;

    public ChatServer(int port) {
        if (port == 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = port;
        }
        executorService = Executors.newFixedThreadPool(MAX_NUM_OF_THREADS);
    }

    public void run() {

        System.out.println("Server is running and ready for chatting...");

        try (ServerSocket socket = new ServerSocket(port)) {

            while (true) {

                Socket incomingConnection = socket.accept();
                ClientSocket client = new ClientSocket(incomingConnection);
                MapDataStorage listOfClients = MapDataStorage.getInstance();
                ChatServerWorker worker = new ChatServerWorker(client, listOfClients);
                executorService.submit(worker);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
