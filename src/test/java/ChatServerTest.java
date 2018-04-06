import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ChatServerTest {

    @Test
    public void canServerLookupAClient() {
        Server server = new ChatServer();
        Message message = new TextMessage("Hello World!");

        assertTrue(server.send("testClient", message));

    }

}
