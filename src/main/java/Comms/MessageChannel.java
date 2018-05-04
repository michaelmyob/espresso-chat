package Comms;

import java.io.*;
import java.net.Socket;

public class MessageChannel {

    private Socket socket;
    public String clientNickName;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Socket getSocket() {
        return socket;
    }

    public MessageChannel(Socket socket) {
        this.socket = socket;
        intialiseStreams();
    }

    private void intialiseStreams() {
        try {
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeStreams() {
        try {
            this.objectOutputStream.close();
            this.objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNicknameToChannel(String clientNickName) {
        this.clientNickName = clientNickName;
    }

    public ObjectInputStream getInputStream() {
       return this.objectInputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return this.objectOutputStream;
    }
}
