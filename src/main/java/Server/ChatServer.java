package Server;

import Interfaces.DataStoreHandler;
import Interfaces.MessageSender;
import Interfaces.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ChatServer implements Server {

    private final int DEFAULT_PORT = 30000;

    private boolean isRunning = true;
    private final int port;
    private final ExecutorService threadPool;
    private final DataStoreHandler dataStoreHandler;
    private final MessageSender messageSender;

    public ChatServer(int port, ExecutorService threadPool, DataStoreHandler dataStoreHandler, MessageSender messageSender) {
        if (port == 0) {
            this.port = DEFAULT_PORT;
        } else {
            this.port = port;
        }
        this.threadPool = threadPool;
        this.dataStoreHandler = dataStoreHandler;
        this.messageSender = messageSender;
    }

    public void stop() {
        System.out.println("Flagging server to stop now... Stopping now");
        isRunning = false;
    }

    public void run() {

        System.out.println("Server is running and ready for chatting...");

        try (ServerSocket socket = new ServerSocket(port)) {

            while (isRunning) {

                Socket incomingConnection = socket.accept();
                ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(incomingConnection, dataStoreHandler, messageSender);
                threadPool.submit(consoleInputHandler);            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            threadPool.shutdown();
        }
    }
}
