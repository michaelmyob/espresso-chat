import java.io.*;
import java.net.Socket;

public class ChatClient implements Client {

    //    Server destinationServer;
    String destinationIP;
    int destinationPort;
    BufferedReader readFromServer;
    BufferedReader readFromKeyboard;

    ChatClient(String ip, int port) {
        this.destinationIP = ip;
        this.destinationPort = port;
    }

    public void startClient() {

        try {
            Socket socket = new Socket(destinationIP, destinationPort);

            readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

            InputStream inputStream = socket.getInputStream();
            readFromServer = new BufferedReader(new InputStreamReader(inputStream));

            System.out.println("Start the chat, type and press Enter key");

            checkForIncomingMessages();

            while (true) {

                if (!readFromServer.ready()) {
                    Message messageSentToServer = new TextMessage(readFromKeyboard.readLine());

                    ChatUtilities.sendAMessageThroughSocket(socket, messageSentToServer);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkForIncomingMessages() {
        new Thread(
                () -> {
                    String messageReceivedFromServer;

                    try {
                        while (true) {
                            if ((messageReceivedFromServer = readFromServer.readLine()) != null) {
                                System.out.println("Incoming Message: " + messageReceivedFromServer.toString());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }).start();
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
