import java.io.*;
import java.net.Socket;

public class ChatClient implements Client {

//    Server destinationServer;
    String destinationIP;
    int destinationPort;

    ChatClient(String ip, int port) {
        this.destinationIP = ip;
        this.destinationPort = port;
//        new ChatServer(destinationPort);
    }

    public void start() throws IOException {

        Socket socket = new Socket(destinationIP, destinationPort);

        BufferedReader readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writeToServer = new PrintWriter(outputStream, true);


        InputStream inputStream = socket.getInputStream();
        BufferedReader readFromServer = new BufferedReader(new InputStreamReader(inputStream));

        System.out.println("Start the chat, type and press Enter key");

        String messageReceivedFromServer, messageSentToServer;

        while (true) {
            messageSentToServer = readFromKeyboard.readLine();
            writeToServer.println(messageSentToServer);
            writeToServer.flush();

            if((messageReceivedFromServer = readFromServer.readLine()) != null)
            {
                System.out.println("Server says: " + messageReceivedFromServer);
            }
        }
    }

    public boolean connect(Server server) {
        return true;
    }


    public void sendMessage(Server server, Message message) {

    }


    public boolean receive(Message message) {
        return true;
    }


}
