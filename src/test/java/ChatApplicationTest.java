import org.junit.Ignore;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatApplicationTest {

    public InetSocketAddress getSampleSocketAddress(){
        return new InetSocketAddress("localhost", 50000);
    }

//    @Test
//    public void canClientSendAMessageToAnotherClient() {
//        Interfaces.Server server = new Server.ChatServer(0);
//        server.register("sampleClient", getSampleSocketAddress());
//        Interfaces.Message msg = new Message.TextMessage("Hi there!");
////        assertTrue(server.send("sampleClient", msg));
//    }
}
