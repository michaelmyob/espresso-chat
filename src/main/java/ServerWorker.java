import java.io.*;
import java.net.Socket;

public class ServerWorker implements Runnable{

    //        ServerSocket socket = new ServerSocket(this.getPort());
//        Socket connectionSocket = socket.accept();
    Socket connectionSocket;

    public ServerWorker(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
    }



    public void run() {

        try {
            BufferedReader readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

            OutputStream outputStream = connectionSocket.getOutputStream();
            PrintWriter writeToClient = new PrintWriter(outputStream, true);

            InputStream inputStream = connectionSocket.getInputStream();
            BufferedReader readFromClient = new BufferedReader(new InputStreamReader(inputStream));

            String messageReceivedFromClient, messageSentToClient, clientNickName;


            while (true) {
                if (!connectionSocket.isClosed() && connectionSocket != null) {

                    System.out.println("Hooray, client is now connected and registered!");
                    writeToClient.println("Great, you are now registered! Please choose from options below: ");
                    writeToClient.flush();
////                    String nickname = readFromClient.readLine();
//                    if ((clientNickName = readFromClient.readLine()) != null) {
//
//                        writeToClient.println("nickname set as: " + clientNickName);
//
//                        writeToClient.flush();
//                    }


//                    System.out.println("Client from address " + connectionSocket.getRemoteSocketAddress() + " connected");
                }


                messageSentToClient = readFromKeyboard.readLine();
                writeToClient.println(messageSentToClient);
                writeToClient.flush();
            }
        } catch (IOException e) {

        }
    }
}

