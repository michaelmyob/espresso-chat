import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatApplicationTest {

    @Test
    public void testSendAndReceiveMessage() {
        Server server = new ChatServer();
        Client client = new ChatClient();
        Message message = new TextMessage("Hello World!");

        client.connect(server);

        client.sendMessage(server, message);

       //  server.respond(client, message);

        assertTrue(client.receive(message));
    }

    @Test
    public void canAClientSendTheMessageToTheServer()
    {
        Server server = new ChatServer();
        Client client = new ChatClient();
        Message messageSentFromClient = new TextMessage("Hello World!");

//        client.connect(server);

        client.sendMessage(server, messageSentFromClient);

        String response = server.respond(client, messageSentFromClient).forDisplay();

        assertEquals(response, messageSentFromClient.forDisplay());
    }
}
