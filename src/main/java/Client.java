import java.io.IOException;
import java.net.Socket;

public interface Client {
    boolean connect(Server server);
    void sendMessage(Server server, Message message);
    boolean receive(Message message);
}
