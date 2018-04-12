import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public interface Server {

    Message respond(Client client, Message message);
    boolean register(String clientNickName, InetSocketAddress clientAddress);
    void run();
    String getIP();
    int getPort();
}
