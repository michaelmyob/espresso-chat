import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ChatServerTest {

    @Test
    public void isServerReadyForConnections() {
        Server server = new ChatServer();
        assertTrue(server.ready());
    }

}
