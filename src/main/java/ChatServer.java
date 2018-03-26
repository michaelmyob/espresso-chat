import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer implements Server {

    public Message respond(Client client, Message message) {
        return message;
    }

    public boolean ready() {
        return true;
    }

    public void listen(int port) throws IOException {

        ServerSocket socket = null;

        try {
            socket = new ServerSocket(port);
            socket.accept();
        } catch (IOException e) {
            System.out.println("Cannot start the server...");

        } finally {
            socket.close();
        }
    }

}
