import Comms.MessageChannel;
import Data.HashMapDataStoreHandler;
import Interfaces.DataStoreHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class HashmapDataStoreHandlerTest {



    Runnable mockserver =() -> {


        try (ServerSocket socket = new ServerSocket(20000)) {
            socket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

    };
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future mockServerThread;
    @Before
    public void setup() {
        System.err.println("init server");
        mockServerThread = executorService.submit(mockserver);


    }

    @After
    public void tearDown()
    {
       // executorService.shutdownNow();
        //mockServerThread.interrupt();
        mockServerThread.cancel(true);
        System.err.println("close server");
    }


    @Test
    public void test(){

    }

    @Test
    public void clientRetrievalTest() throws IOException {
        //Given
        Map clientMap = new ConcurrentHashMap<String, MessageChannel>();
        String expectedClientName = "a";
        Socket clientSocket = new Socket("localhost", 20000);
        MessageChannel messageChannel = new MessageChannel(clientSocket);

        messageChannel.addNicknameToChannel("a");
        clientMap.put("a", messageChannel);
        DataStoreHandler handler = new HashMapDataStoreHandler(clientMap);


        //When
        MessageChannel expectedResponse = handler.getClient("a");

        //Then
        assertEquals(expectedResponse.clientNickName, expectedClientName);

    }
}
