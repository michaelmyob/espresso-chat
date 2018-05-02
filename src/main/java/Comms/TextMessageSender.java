package Comms;

import Interfaces.Message;
import Interfaces.MessageSender;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TextMessageSender implements MessageSender{

    public void send(Message message, MessageChannel messageChannel) {
        try {
            OutputStream outputStream = messageChannel.getSocket().getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(message);
        } catch (IOException e) {
            System.out.println("Error sending message!!!!!!!! Please try again");
        }
    }

}
