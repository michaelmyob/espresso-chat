package Comms;

import Interfaces.Message;
import Message.TextMessage;

import java.io.*;
import java.net.Socket;

public class MessageChannel {

    private Socket socket;
    public String clientNickName;


    public Socket getSocket() {
        return socket;
    }

    public MessageChannel(String nickName, Socket socket) {
        this.clientNickName = nickName;
        this.socket = socket;
    }

    public ObjectInputStream getInputStream() {
        try {
            return new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    private void sendAMessageThroughSocket(Message message) {
//        try {
//            OutputStream outputStream = socket.getOutputStream();
//            PrintWriter writer = new PrintWriter(outputStream, true);
//
//            writer.println(message);
//            writer.flush();
//        } catch (IOException e) {
//            System.out.println("Error sending message!!!!!!!! Please try again");
//        }
//    }
//
//
//    public void sendATextMessage(String message) {
//        Message msg = new TextMessage(message);
//        sendAMessageThroughSocket(msg);
//    }


}
