import Comms.TextMessageSender;
import Data.HashMapDataStore;
import Data.HashMapDataStoreHandler;
import Interfaces.Client;
import Interfaces.DataStoreHandler;
import Interfaces.MessageSender;
import Interfaces.Server;
import Client.ChatClient;
import Server.ChatServer;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EspressoChat {

    public static void main(String[] args) {
        if (args.length == 1) {

            ExecutorService threadPool = Executors.newFixedThreadPool(20);
//            Map listOfClients =
            DataStoreHandler dataStoreHandler = new HashMapDataStoreHandler(HashMapDataStore.getInstance().getClientsMap());
            MessageSender messageSender = new TextMessageSender();

            Server server = new ChatServer(Integer.parseInt(args[0]), threadPool, dataStoreHandler, messageSender);
            server.run();

        } else if (args.length == 3) {

            Client client = new ChatClient(args[0], Integer.parseInt(args[1]), args[2]);
            client.startClient();
        } else {
            System.out.println("Incorrect startup arguments");
            System.exit(1);
        }

    }
}
