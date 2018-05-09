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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatApplicationTest {
    private static int port;
    private static ExecutorService threadPool;
    private static Map listOfClients;
    private static DataStoreHandler dataStoreHandler;
    private static MessageSender messageSender;
    private static Server server;

    private static void initialise() {
        port = 8080;
        threadPool = Executors.newFixedThreadPool(20);
        listOfClients = HashMapDataStore.getInstance().getClientsMap();
        dataStoreHandler = new HashMapDataStoreHandler(listOfClients);
        messageSender = new TextMessageSender();
        server = new ChatServer(port, threadPool, dataStoreHandler, messageSender);
    }


    @BeforeClass
    public static void startServer() {
        initialise();

        new Thread(() -> {
            server.run();
        }).start();
    }


    @AfterClass
    public static void stopServer() {
        server.stop();
    }

    private Socket connectAClient(String nickname) throws IOException {
        Socket clientSocket = new Socket("localhost", port);

        ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
        clientOutput.writeObject(new TextMessage(nickname, nickname));
        clientOutput.flush();
        return clientSocket;
    }

    @Test
    public void canClientSendAMessageToAnotherClient() throws IOException {


// SINA'S IDEA:
//
//        Socket a = connectAClient("a");
//        Socket b = connectAClient("b");
//MessageChannel des = dataStoreHandler.getClient("b");
//messageSender.send(new TextMessage("a", "hey"), des);
//        //TODO - refactor to store nickname registration within inputhandler, so we can replicate the inputhandler's behaviour here
//
//b.getInputStream().read()




//         connectAClient();
//         connectAClient();
//         connectAClient();
//         connectAClient();

//         ChatClient client1 = new ChatClient("localhost", port, "a");
//         client1.startClient();

//        Socket clientSocket = new Socket("localhost", port);
//        MessageChannel messageChannel = new MessageChannel(clientSocket);
//        messageChannel.addNicknameToChannel("f");
//
//        dataStoreHandler.addClient(messageChannel);

        Socket clientSocket = new Socket("localhost", port);
        ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
        clientOutput.flush();
        clientOutput.writeObject(new TextMessage("a", "a"));
        clientOutput.flush();

        Socket clientSocket2 = new Socket("localhost", port);
        ObjectOutputStream clientOutput2 = new ObjectOutputStream(clientSocket2.getOutputStream());
        clientOutput2.flush();
        clientOutput2.writeObject(new TextMessage("b", "b"));
        clientOutput2.flush();

//        System.out.println(dataStoreHandler.getClient("a"));
        System.out.println(dataStoreHandler.getAllClients());


//        Interfaces.Message msg = new Message.TextMessage("Hi there!");
//        assertTrue(server.send("sampleClient", msg));
    }
}
