import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import Interfaces.*;
import Comms.*;
import Message.*;
import Server.*;
import Client.*;
import Data.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatApplicationTest {
    private static int port;
    private static ExecutorService threadPool;
    private static Map listOfClients;
    private static DataStoreHandler dataStoreHandler;
    private static MessageSender messageSender;
    private static Server server;

//    private static void initialise() {
//        port = 8080;
//        threadPool = Executors.newFixedThreadPool(20);
//        listOfClients = HashMapDataStore.getInstance().getClientsMap();
//        dataStoreHandler = new HashMapDataStoreHandler(listOfClients);
//        messageSender = new TextMessageSender();
//        server = new ChatServer(port, threadPool, dataStoreHandler, messageSender);
//        System.out.println("[Server] Initialised");
//    }
//
//    @BeforeClass
//    public static void startServer() {
//        initialise();
//
//        new Thread(() -> {
//            server.run();
//        }).start();
//        System.out.println("[Server] Started");
//    }
//
//    @AfterClass
//    public static void stopServer() {
//        server.stop();
//    }
//
//    private MessageChannel connectAClient(String nickname) throws IOException {
//        Socket clientSocket = new Socket("localhost", port);
//
//        MessageChannel messageChannel = new MessageChannel(clientSocket);
//
//        messageChannel.addNicknameToChannel(nickname);
//
//        return messageChannel;
//    }
//
//    @Test
//    public void canClientSendAMessageToAnotherClient() throws IOException {
//
//
//// SINA'S IDEA: {pseudocode below}
////
////        Socket a = connectAClient("a");
////        Socket b = connectAClient("b");
////        MessageChannel des = dataStoreHandler.getClient("b");
////        messageSender.send(new TextMessage("a", "hey"), des);
////        //TODO - refactor to store nickname registration within inputhandler, so we can replicate the inputhandler's behaviour here
////
////        b.getInputStream().read()
//
//
//
//        // "Dummy" client representation:
//        //      we do this because if we made a real client it would block on system.in
//
//        MessageChannel a = connectAClient("a");
//        MessageChannel b = connectAClient("b");
//
//        MessageSender sender = new TextMessageSender();
//        Message theMessage = new TextMessage("a", "Test");
//
//        sender.send(theMessage, b);
//
////        Socket clientSocket = new Socket("localhost", port);
////        MessageChannel messageChannel = new MessageChannel(clientSocket);
////        messageChannel.addNicknameToChannel("f");
////
////        dataStoreHandler.addClient(messageChannel);
////
////        System.out.println(dataStoreHandler.getAllClients());
//
//    }
}