import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatClientTest {

    @Test
    public void canClientConnectToServer() {
        Server server = new ChatServer();
        Client client = new ChatClient();

        assertTrue(client.connect(server));
    }



    @Test
    public void canNotTalkToUnavailableServer() {
        Server server = new UnavailableServer();
        Client client = new ChatClient();

        assertFalse(client.connect(server));

    }
}
