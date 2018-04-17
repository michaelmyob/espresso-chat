import java.net.Socket;

public interface Server {

    Message respond(Client client, Message message);
    void addClientIntoMap(String clientNickName, Socket clientSocket);
    void run();
    String getIP();
    int getPort();
}
