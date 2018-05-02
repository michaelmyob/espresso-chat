package Interfaces;

import Comms.MessageChannel;

public interface MessageSender {
    void send(Message message, MessageChannel messageChannel);
}
