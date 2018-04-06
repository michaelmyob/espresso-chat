import java.io.IOException;

public interface Server {

    Message respond(Client client, Message message);
    boolean send(String clientName, Message message);

}
