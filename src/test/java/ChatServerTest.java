import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatServerTest {

    @Test
    public void canServerLookupAClient() {
        ConcurrentHashMap client = new ConcurrentHashMap();
        client.put("testClient", "blah blah");
        Server server = new ChatServer(client);
        Message message = new TextMessage("Hello World!");

        assertTrue(server.send("testClient", message));

    }

}
