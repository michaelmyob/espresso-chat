import java.net.Socket;

public interface Server {

    Message respond(Client client, Message message);
    boolean register(String clientNickName, Socket clientSocket);
    void run();
    String getIP();
    int getPort();
}
