package Message;

import Interfaces.Message;

import java.io.Serializable;

public class TextMessage implements Message, Serializable {

    public String messageContents;
    public String sender;

    public TextMessage(String sender, String messageContents) {
        this.sender = sender;
        this.messageContents = messageContents;

    }

//    public String toString() {
//        return sender + " says: " + messageContents;
//    }

//    public boolean equals(Object obj) {
//        return (obj instanceof String) &&
//                this.messageContents.equals(obj.toString());
//    }
}
