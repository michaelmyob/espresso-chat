import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RawServerSocket {


    private int port;
    private String host;

    public RawServerSocket() {
        this.port = 30000;
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

    }



    public static void main(String[] args) throws IOException {
        RawServerSocket server = new RawServerSocket();
//        server.listen();

        ServerSocket socket = new ServerSocket(server.getPort());
        System.out.println("Server is running and ready for chatting...");
        Socket connectionSocket = socket.accept();

        BufferedReader readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

        OutputStream outputStream = connectionSocket.getOutputStream();
        PrintWriter writeToClient = new PrintWriter(outputStream, true);

        InputStream inputStream = connectionSocket.getInputStream();
        BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

        String messageReceivedFromClient, messageSentToClient;


        while (true) {
            if ((messageReceivedFromClient = readFromClient.readLine()) != null) {
                System.out.println("Client says: " + messageReceivedFromClient);
            }

            messageSentToClient = readFromKeyboard.readLine();
            writeToClient.println(messageSentToClient);
            writeToClient.flush();
        }

    }
}
