package Comms;

import java.io.*;
import java.net.Socket;

public class MessageChannel {

    private Socket socket;
    public String clientNickName;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    public MessageChannel(Socket socket) {
        this.socket = socket;
        initialiseStreams();
    }

    private void initialiseStreams() {
//        try {
//            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
//           // this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void closeConnection() {
        try {
            this.objectOutputStream.close();
            this.objectInputStream.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNicknameToChannel(String clientNickName) {
        this.clientNickName = clientNickName;
    }

    public ObjectInputStream getInputStream() throws IOException {
       return new ObjectInputStream(socket.getInputStream());
    }

    public ObjectOutputStream getOutputStream() throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }

}
