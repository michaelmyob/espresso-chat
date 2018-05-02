import Interfaces.Client;
import Interfaces.Server;

public class EspressoChat {

    public static void main(String[] args) {
        if (args.length == 1) {

            Server server = new Server.ChatServer(Integer.parseInt(args[0]));
            server.run();

        } else if (args.length == 3) {

            Client client = new Client.ChatClient(args[0], Integer.parseInt(args[1]), args[2]);
            client.startClient();
        } else {
            System.out.println("Incorrect startup arguments");
            System.exit(1);
        }

    }
}
