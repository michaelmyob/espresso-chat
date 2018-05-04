package Client;

import Interfaces.Client;
import Message.TextMessage;

import java.io.*;
import java.net.Socket;

public class ChatClient implements Client {

    private String nickname;
    private String destinationIP;
    private int destinationPort;
    private BufferedReader readFromKeyboard;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isRunning;
    private final String SERVER_QUIT_RESPONSE = "QUIT";

    public ChatClient(String ip, int port, String nickname) {
        this.destinationIP = ip;
        this.destinationPort = port;
        this.nickname = nickname;
        this.isRunning = true;
    }

    public void startClient() {

        try (Socket socket = new Socket(destinationIP, destinationPort)) {

            readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            checkForIncomingMessages();

            TextMessage nicknameToBeRegistered = new TextMessage(nickname, nickname);
            objectOutputStream.writeObject(nicknameToBeRegistered);
            objectOutputStream.flush();
            System.out.println("[DEBUG] nickname sent to server!");

            while (isRunning) {

                String input = readFromKeyboard.readLine();

                TextMessage messageSentToServer = new TextMessage(nickname, input);

                if (input.toUpperCase().equals(SERVER_QUIT_RESPONSE)) {
                    isRunning = false;
                }

                objectOutputStream.writeObject(messageSentToServer);
                objectOutputStream.flush();
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
                    Object messageReceivedFromServer;

                    try {
                        while (isRunning) {
                            if ((messageReceivedFromServer = objectInputStream.readObject()) != null) {

                                if (messageReceivedFromServer instanceof TextMessage) {
                                    TextMessage receivedMessage = (TextMessage) messageReceivedFromServer;

                                    if (receivedMessage.messageContents.equals(SERVER_QUIT_RESPONSE)) {
                                        isRunning = false;
                                    }

                                    System.out.println(receivedMessage);
                                }

                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                }).start();
    }
}