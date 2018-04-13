import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatServerTest {

//    @Test
//    public void canServerLookupAClient() {
//        ConcurrentHashMap client = new ConcurrentHashMap();
//        client.put("testClient", "blah blah");
//        Server server = new ChatServer(0);
//        Message message = new TextMessage("Hello World!");
//
//        assertTrue(server.send("testClient", message));
//
//    }

    @Test
    public void serverCanRegisterAClient() {

        Server server = new ChatServer(0);
        InetSocketAddress clientAddress = new InetSocketAddress("localhost",51748);
        assertTrue(server.register("testNickName", clientAddress));
    }

    @Test
    public void serverDeclinesDuplicateClientRegistration() {

        Server server = new ChatServer(0);
        InetSocketAddress clientAddress = new InetSocketAddress("localhost",51748);
        server.register("testNickName", clientAddress);

        assertFalse(server.register("testNickName", clientAddress));
    }
//
//    @Test
//    public void serverCanLookupAClient() {
//
//        Server server = new ChatServer(0);
//        server.register("testNickName");
//        assertTrue(server.lookupClient("testNickName"));
//
//    }

}
