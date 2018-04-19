import java.io.*;
import java.net.Socket;

public class ChatClient implements Client {

    String destinationIP;
    int destinationPort;
    BufferedReader readFromServer;
    BufferedReader readFromKeyboard;
    OutputStream outputStream;
    PrintWriter writer;

    ChatClient(String ip, int port) {
        this.destinationIP = ip;
        this.destinationPort = port;
    }

    public void startClient() {

        try (Socket socket = new Socket(destinationIP, destinationPort)) {

            readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

            InputStream inputStream = socket.getInputStream();
            readFromServer = new BufferedReader(new InputStreamReader(inputStream));

            outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);


            System.out.println("Start the chat, type and press Enter key");

            checkForIncomingMessages();

            while (true) {

                if (!readFromServer.ready()) {
                    Message messageSentToServer = new TextMessage(readFromKeyboard.readLine());
                    writer.println(messageSentToServer);
                    writer.flush();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }

    private void checkForIncomingMessages() {
        new Thread(
                () -> {
                    String messageReceivedFromServer;

                    try {
                        while (true) {
                            if ((messageReceivedFromServer = readFromServer.readLine()) != null) {

//                                if (messageReceivedFromServer.equals("STATUS_QUIT_NOW")) {
//                                    System.exit(0);
//                                }
                                System.out.println(messageReceivedFromServer.toString());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }).start();
    }
}
