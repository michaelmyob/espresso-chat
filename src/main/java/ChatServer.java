import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements Server {



    public Message respond(Client client, Message message) {
        return message;
    }

    public void listen(int port) throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        System.out.println("Server is up...");


        socket.close();

    }


}
