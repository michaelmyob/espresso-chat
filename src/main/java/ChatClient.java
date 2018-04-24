import java.io.*;
import java.net.Socket;

public class ChatClient implements Client {

    String nickname;
    String destinationIP;
    int destinationPort;
    BufferedReader readFromServer;
    BufferedReader readFromKeyboard;
    OutputStream outputStream;
    PrintWriter writer;
    private final String SERVER_QUIT_RESPONSE = "QUIT";

    ChatClient(String ip, int port, String nickname) {
        this.destinationIP = ip;
        this.destinationPort = port;
        this.nickname = nickname;
    }

    public void startClient() {

        try (Socket socket = new Socket(destinationIP, destinationPort)) {


            readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

            InputStream inputStream = socket.getInputStream();
            readFromServer = new BufferedReader(new InputStreamReader(inputStream));

            outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);

            checkForIncomingMessages();

            boolean isRunning = true;

            writer.println(nickname);
            writer.flush();

            String response = readFromServer.readLine();
            if (response.equals(SERVER_QUIT_RESPONSE)) {
                isRunning = false;
            }



            while (isRunning) {

                if (!readFromServer.ready() ) {

                    String input = readFromKeyboard.readLine();

                    Message messageSentToServer = new TextMessage(input);

                    if (input.toUpperCase().equals(SERVER_QUIT_RESPONSE)) {
                        isRunning = false;
                    }

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
                                System.out.println(messageReceivedFromServer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }).start();
    }
}
