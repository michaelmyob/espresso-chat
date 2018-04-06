import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RawServerSocket implements Runnable {


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

    public class ServerListener implements Runnable {
        //        ServerSocket socket = new ServerSocket(this.getPort());
//        Socket connectionSocket = socket.accept();
        Socket connectionSocket;

        public ServerListener(Socket connectionSocket) throws IOException {
            this.connectionSocket = connectionSocket;
        }

        public void run() {

            try {
                BufferedReader readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

                OutputStream outputStream = connectionSocket.getOutputStream();
                PrintWriter writeToClient = new PrintWriter(outputStream, true);

                InputStream inputStream = connectionSocket.getInputStream();
                BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

                String messageReceivedFromClient, messageSentToClient;


                while (true) {
                    if (!connectionSocket.isClosed() && connectionSocket != null) {
                        System.out.println("Client from address " + connectionSocket.getRemoteSocketAddress() + " connected");
                    }
                    if ((messageReceivedFromClient = readFromClient.readLine()) != null) {

                        System.out.println("Client says: " + messageReceivedFromClient);
                    }

                    messageSentToClient = readFromKeyboard.readLine();
                    writeToClient.println(messageSentToClient);
                    writeToClient.flush();
                }
            } catch (IOException e) {

            }
        }
    }


    public static void main(String[] args) throws IOException {
        RawServerSocket server = new RawServerSocket();
        server.run();


    }


    public void run() {

        System.out.println("Server is running and ready for chatting...");

        ServerSocket socket = null;
        try {
            socket = new ServerSocket(this.getPort());
        } catch (IOException e) {

        }
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = socket.accept();
                System.out.println("New socket connection accepted");
            } catch (IOException e) {

                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

            try {
                new Thread(new ServerListener(clientSocket)).start();
            } catch (IOException e) {

            }
        }
    }


}