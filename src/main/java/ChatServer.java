import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer implements Server {

    private String IPaddress;
    private int port;
    private ConcurrentHashMap clientsMap;

    public String getIPaddress() {
        return IPaddress;
    }

    public int getPort() {
        return port;
    }

    public Message respond(Client client, Message message) {
        return message;
    }

    public void listen(int port) throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();

        System.out.println("Server is up...");


        socket.close();

    }

    public boolean send(String clientName, Message message) {

        // lookup
        if (clientsMap.containsKey(clientName)) {
            String clientSocketAddress = (String)clientsMap.get(clientName);
        } else {
            System.out.println("Error :( Client not found");
            return false;
        }

        return true;
    }



}
