import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChatServerTest {
//
//    @Test
//    public void canServerLookupAClient() {
//        ConcurrentHashMap client = new ConcurrentHashMap();
//        client.put("testClient", "blah blah");
//        Interfaces.Server server = new Server.ChatServer(0);
//        Interfaces.Message message = new Message.TextMessage("Hello World!");
//
//        assertTrue(server.send("testClient", message));
//
//    }
//
//    @Test
//    public void serverCanRegisterAClient() {
//
//        Interfaces.Server server = new Server.ChatServer(0);
//        InetSocketAddress clientAddress = new InetSocketAddress("localhost",51748);
//        assertTrue(server.register("testNickName", clientAddress));
//    }
//
//    @Test
//    public void serverDeclinesDuplicateClientRegistration() {
//
//        Interfaces.Server server = new Server.ChatServer(0);
//        InetSocketAddress clientAddress = new InetSocketAddress("localhost",51748);
//        server.register("testNickName", clientAddress);
//
//        assertFalse(server.register("testNickName", clientAddress));
//    }
//
//    @Test
//    public void serverCanLookupAClient() {
//
//        Interfaces.Server server = new Server.ChatServer(0);
//        server.register("testNickName");
//        assertTrue(server.lookupClient("testNickName"));
//
//    }

}
