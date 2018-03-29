import java.io.*;
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
//        ServerSocket socket = new ServerSocket(getPort());
//        System.out.println("Server is running...");
//        String clientMessage;
//
//        Socket connectionSocket = socket.accept();
//
//        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//        clientMessage = inFromClient.readLine();
//        System.out.println("[SERVER] clientMessage from input stream: " + clientMessage);
//
//        inFromClient.close();
//
//        PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream());
//        outToClient.print("Msg from server - Hey I have received your message : " + clientMessage);
//
//        outToClient.close();
//
//        connectionSocket.close();

        ServerSocket socket = new ServerSocket(getPort());
        System.out.println("Server is running...");

        Socket connectionSocket = socket.accept();

        OutputStream os = connectionSocket.getOutputStream();
        DataInputStream is = new DataInputStream(connectionSocket.getInputStream());

        String c;
        String responseLine;

        while (true) {

            responseLine = is.readLine();
            System.out.println("echo: " + responseLine);


        }


//        os.close();
//        is.close();
//        connectionSocket.close();

    }


    public static void main(String[] args) throws IOException {
        RawServerSocket server = new RawServerSocket();
        server.listen();
    }
}
