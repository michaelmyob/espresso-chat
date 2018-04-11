import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable {

    //        ServerSocket socket = new ServerSocket(this.getPort());
//        Socket connectionSocket = socket.accept();
    Socket connectionSocket;

    public ServerWorker(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
    }


    public void run() {

        try {
            InputStream inputStream = connectionSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            String messageReceivedFromClient, messageSentToClient, clientNickName;


            while (true) {
                if (!connectionSocket.isClosed() && connectionSocket != null) {

                    Message msg = new TextMessage("Hooray, client is now connected and registered! Please choose from options below:");
                    ChatUtilities.sendAMessageThroughSocket(connectionSocket, msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

