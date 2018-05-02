package Server;

import Comms.TextMessageSender;
import Data.HashMapDataStore;
import Data.HashMapDataStoreHandler;
import Interfaces.DataStoreHandler;
import Interfaces.Message;
import Interfaces.MessageSender;
import Interfaces.Server;
import Comms.MessageChannel;
import Message.TextMessage;

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
    DataStoreHandler dataStoreHandler;
    MessageSender messageSender;


    public ChatServer(int port) {
        if (port == 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = port;
        }
        initialise();
    }

    public void run() {

        System.out.println("Server is running and ready for chatting...");

        try (ServerSocket socket = new ServerSocket(port)) {

            while (true) {

                Socket incomingConnection = socket.accept();

                InputStream inputStream = incomingConnection.getInputStream();
                BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));
                String nickName = readFromClient.readLine();

                MessageChannel messageChannel = new MessageChannel(nickName, incomingConnection);

                if (dataStoreHandler.addClient(nickName, messageChannel)) {
                    ConsoleInputHandler worker = new ConsoleInputHandler(messageChannel, dataStoreHandler, messageSender);
                    executorService.submit(worker);
                }
                else {
                    Message msg = new TextMessage("server", SERVER_QUIT_RESPONSE);
                    messageSender.send(msg, messageChannel);
                    incomingConnection.close();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
        }

    }


    private void initialise() {
        executorService = Executors.newFixedThreadPool(MAX_NUM_OF_THREADS);
        HashMapDataStore hashMapDataStore = HashMapDataStore.getInstance();
        Map listOfClients = hashMapDataStore.getClientsMap();
        dataStoreHandler = new HashMapDataStoreHandler(listOfClients);
        messageSender = new TextMessageSender();
    }


}
