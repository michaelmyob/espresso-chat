import org.junit.Test;

import javax.xml.soap.Text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatApplicationTest {

    @Test
    public void testSendAndReceiveMessage() {
        Server server = new HTTPChatServer();
        Client client = new HTTPChatClient();
        Message message = new TextMessage("Hello World!");

        client.connect(server);

        client.talk(server, message);

       //  server.respond(client, message);

        assertTrue(client.receive(message));
    }

    @Test
    public void canAClientSendTheMessageToTheServer()
    {
        Server server = new HTTPChatServer();
        Client client = new HTTPChatClient();
        Message messageSentFromClient = new TextMessage("Hello World!");

        client.connect(server);

        client.talk(server, messageSentFromClient);

        assertEquals(server.respond(client, messageSentFromClient).forDisplay(), messageSentFromClient.forDisplay());


    }
}
