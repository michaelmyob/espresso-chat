public class EspressoChat {

    public static void main(String[] args) {
        if (args.length == 1) {

            Server server = new ChatServer(Integer.parseInt(args[0]));
            server.run();

        } else if (args.length == 2) {

            Client client = new ChatClient(args[0], Integer.parseInt(args[1]));
            client.startClient();
        } else {
            System.out.println("Incorrect startup arguments");
            System.exit(1);
        }

    }
}
