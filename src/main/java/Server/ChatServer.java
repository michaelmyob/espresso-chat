package Server;

import Data.HashMapDataStore;
import Data.HashMapDataStoreHandler;
import Interfaces.Server;
import Channel.MessageChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements Server, Runnable {

    private final int DEFAULT_PORT = 30000;
    private int port;
    private final int MAX_NUM_OF_THREADS = 20;
    ExecutorService executorService;
    private final String SERVER_QUIT_RESPONSE = "QUIT";


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
                HashMapDataStore hashMapDataStore = HashMapDataStore.getInstance();
                Map listOfClients = hashMapDataStore.getClientsMap();
                HashMapDataStoreHandler dataStoreHandler = new HashMapDataStoreHandler(listOfClients);

                Socket incomingConnection = socket.accept();

                InputStream inputStream = incomingConnection.getInputStream();
                BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));
                String nickName = readFromClient.readLine();
                MessageChannel client = new MessageChannel(nickName, incomingConnection);

                if (dataStoreHandler.addClient(nickName, client)) {
                    ConsoleInputHandler worker = new ConsoleInputHandler(client, dataStoreHandler);
                    executorService.submit(worker);
                }
                else {
                    client.sendATextMessage(SERVER_QUIT_RESPONSE);
                    incomingConnection.close();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
