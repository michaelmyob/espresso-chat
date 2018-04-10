import java.io.*;
import java.net.Socket;

public class Worker {

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

