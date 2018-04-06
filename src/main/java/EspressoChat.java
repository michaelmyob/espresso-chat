import java.io.IOException;

public class EspressoChat {

    public static void main(String[] args) throws IOException {

        System.out.println("args: " + args[0]);
        System.out.println("args: " + args[1]);

        if (args.length == 1) {
            Server server = new ChatServer();
        } else {
            Client client = new ChatClient();
        }

    }
}
