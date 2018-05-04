package Comms;

import Interfaces.Message;
import Interfaces.MessageSender;
import Message.TextMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TextMessageSender implements MessageSender{

    public void send(Message message, MessageChannel messageChannel) {
        try {
            TextMessage msg = (TextMessage) message;
            messageChannel.getOutputStream().writeObject(msg);
            messageChannel.getOutputStream().flush();
        } catch (IOException e) {
            System.out.println("Error sending message!!!!!!!! Please try again");
        }
    }

}
