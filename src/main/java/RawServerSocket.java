import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RawServerSocket {


    private int port;
    private String host;

    public RawServerSocket() {
        this.port = 3000;
        this.host = "localhost";
    }

    public RawServerSocket(int port) {
        this.port = port;
        this.host = "localhost";
    }

    public RawServerSocket(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public void listen() throws IOException {
        ServerSocket socket = new ServerSocket(getPort());
        System.out.println("Server is running...");
        String clientMessage;

        while (true) {
            Socket connectionSocket = socket.accept();

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientMessage = inFromClient.readLine();
            System.out.println("[SERVER] clientMessage: " + clientMessage);

            outToClient.writeBytes("I have received your message : " + clientMessage);
            outToClient.flush();
            outToClient.close();

        }

    }


    public static void main(String[] args) throws IOException {
        RawServerSocket server = new RawServerSocket();
        server.listen();
    }
}
