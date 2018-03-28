import java.io.IOException;
import java.net.Socket;

public class ChatClient implements Client {



    public boolean connect(Server server) {
        return true;
    }


    public void sendMessage(Server server, Message message) {

    }


    public boolean receive(Message message) {
        return true;
    }


}
