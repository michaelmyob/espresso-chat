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

public class ChatServer implements Server {

    private final int DEFAULT_PORT = 30000;
    private final int NUM_OF_SERVER_THREADS = 20;
    private final String SERVER_QUIT_RESPONSE = "QUIT";

    private int port;
    private ExecutorService numberOfServerThreadsAvailable;
    private DataStoreHandler dataStoreHandler;
    private MessageSender messageSender;

    public ChatServer(int port) {
        if (port == 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = port;
        }
        initialise();
    }

    private void initialise() {
        numberOfServerThreadsAvailable = Executors.newFixedThreadPool(NUM_OF_SERVER_THREADS);
        Map listOfClients = HashMapDataStore.getInstance().getClientsMap();
        dataStoreHandler = new HashMapDataStoreHandler(listOfClients);
        messageSender = new TextMessageSender();
    }

    public void run() {

        System.out.println("Server is running and ready for chatting...");

        try (ServerSocket socket = new ServerSocket(port)) {

            while (true) {

                Socket incomingConnection = socket.accept();

                InputStream inputStream = incomingConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String clientNickname = reader.readLine();
                MessageChannel messageChannel = new MessageChannel(clientNickname, incomingConnection);

                attemptClientRegistration(incomingConnection, clientNickname, messageChannel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            numberOfServerThreadsAvailable.shutdown();
        }
    }

    private void attemptClientRegistration(Socket incomingConnection, String clientNickname, MessageChannel messageChannel) throws IOException {

        boolean clientCanBeRegistered = dataStoreHandler.addClient(clientNickname, messageChannel);

        if (clientCanBeRegistered) {
            ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(messageChannel, dataStoreHandler, messageSender);
            numberOfServerThreadsAvailable.submit(consoleInputHandler);
        }
        else {
            Message msg = new TextMessage("server", SERVER_QUIT_RESPONSE);
            messageSender.send(msg, messageChannel);
            incomingConnection.close();
        }
    }
}
