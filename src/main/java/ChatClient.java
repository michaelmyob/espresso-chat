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

    public void startClient() throws IOException {

        Socket socket = new Socket(destinationIP, destinationPort);

        BufferedReader readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

        InputStream inputStream = socket.getInputStream();
        BufferedReader readFromServer = new BufferedReader(new InputStreamReader(inputStream));

        System.out.println("Start the chat, type and press Enter key");

        Message messageReceivedFromServer, messageSentToServer;

        while (true) {
            if((messageReceivedFromServer = new TextMessage(readFromServer.readLine())) != null)
            {
                System.out.println("Server says: " + messageReceivedFromServer);
            }

            messageSentToServer = new TextMessage(readFromKeyboard.readLine());
            ChatUtilities.sendAMessageThroughSocket(socket, messageSentToServer);

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
