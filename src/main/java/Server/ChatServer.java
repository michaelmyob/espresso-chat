package Server;

import Interfaces.DataStoreHandler;
import Interfaces.Message;
import Interfaces.MessageSender;
import Interfaces.Server;
import Comms.MessageChannel;
import Message.TextMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ChatServer implements Server {

    private final int DEFAULT_PORT = 30000;
    private final String SERVER_QUIT_RESPONSE = "QUIT";

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
        isRunning = false;
    }

    public void run() {

        System.out.println("Server is running and ready for chatting...");

        try (ServerSocket socket = new ServerSocket(port)) {

            while (isRunning) {

                Socket incomingConnection = socket.accept();

                MessageChannel messageChannel = new MessageChannel(incomingConnection);

                Message clientMessage = readClientNickName(messageChannel.getInputStream());
                TextMessage clientNicknameMessageObject = (TextMessage) clientMessage;
                String clientNickname = clientNicknameMessageObject.messageContents;
                messageChannel.addNicknameToChannel(clientNickname);

                attemptClientRegistration(messageChannel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            threadPool.shutdown();
        }
    }


    private Message readClientNickName(ObjectInputStream inputStream) {

        try {
            Object clientNickname = inputStream.readObject();
            if (clientNickname instanceof TextMessage) {
                return (TextMessage) clientNickname;
            }
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void attemptClientRegistration(MessageChannel messageChannel) throws IOException {

        boolean clientCanBeRegistered = dataStoreHandler.addClient(messageChannel);

        if (clientCanBeRegistered) {
            createClientHandler(messageChannel);
        }
        else {
            Message msg = new TextMessage("server", SERVER_QUIT_RESPONSE);
            messageSender.send(msg, messageChannel);
            messageChannel.closeConnection();
        }
    }

    private void createClientHandler(MessageChannel messageChannel) {
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(messageChannel, dataStoreHandler, messageSender);
        threadPool.submit(consoleInputHandler);
    }
}
