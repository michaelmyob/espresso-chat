public class EspressoChat {

    public static void main(String[] args) {

        System.out.println("args: " + args[0]);
        System.out.println("args: " + args[1]);

        if (args.length == 1) {
            Server server = new ChatServer();
            server.listen(2000);
            //run as server
        } else {
            //run as client
        }

    }
}
