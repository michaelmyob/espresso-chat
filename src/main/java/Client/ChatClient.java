package Client;

import Interfaces.Client;
import Message.TextMessage;

import java.io.*;
import java.net.Socket;

public class ChatClient implements Client {

    private String nickname;
    private String destinationIP;
    private int destinationPort;
    private BufferedReader keyboardReader;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isRunning;
    private final String SERVER_QUIT_RESPONSE = "QUIT";
    private Socket socket;

    public ChatClient(String ip, int port, String nickname) {
        this.destinationIP = ip;
        this.destinationPort = port;
        this.nickname = nickname;
        this.isRunning = true;
    }

    private void initialiseIO() {
        try {
            socket = new Socket(destinationIP, destinationPort);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            keyboardReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startClient() {

        initialiseIO();

        try {

            checkForIncomingMessages();
            attemptNicknameRegistration();

            while (isRunning) {

                String readFromKeyboardInput = keyboardReader.readLine();

                if (serverHasSentAQuitResponse(readFromKeyboardInput)) {
                    isRunning = false;
                } else {
                    TextMessage messageFromKeyboardInput = new TextMessage(nickname, readFromKeyboardInput);
                    serialiseToServer(messageFromKeyboardInput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Thank you for using Espresso Chat.\nQuitting now...");
            System.exit(0);
        }
    }

    private boolean serverHasSentAQuitResponse(String inputFromServer) {
        if (inputFromServer.toUpperCase().equals(SERVER_QUIT_RESPONSE)) {
            return true;
        }
        return false;
    }

    private void checkForIncomingMessages() {
        new Thread(
                () -> {

                    try {
                        while (isRunning) {

                            Object objectReadFromServer = objectInputStream.readObject();

                            if (objectReadFromServer instanceof TextMessage) {

                                TextMessage receivedMessage = (TextMessage) objectReadFromServer;
                                System.out.println(receivedMessage);
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }).start();
    }

    private void serialiseToServer(TextMessage messageFromKeyboardInput) throws IOException {
        objectOutputStream.writeObject(messageFromKeyboardInput);
        objectOutputStream.flush();
    }

    private void attemptNicknameRegistration() throws IOException {
        TextMessage nicknameToBeRegistered = new TextMessage(nickname, nickname);
        objectOutputStream.writeObject(nicknameToBeRegistered);
        objectOutputStream.flush();
    }


}