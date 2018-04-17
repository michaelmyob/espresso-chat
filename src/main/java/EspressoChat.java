import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EspressoChat {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        if (args.length == 1) {

            Server server = new ChatServer(Integer.parseInt(args[0]));
            executorService.submit(server::run);


        } else if (args.length == 2){

            Client client = new ChatClient(args[0], Integer.parseInt(args[1]));
            executorService.submit(client::startClient);

        } else {
            System.out.println("Incorrect startup arguments");
            System.exit(1);
        }

    }
}
