import java.io.Serializable;

public class TextMessage implements Message, Serializable {

    public String messageContents;

    public TextMessage(String messageContents) {
        this.messageContents = messageContents;

    }

    @Override
    public String toString() {
        return messageContents;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return this.equals(obj.toString());
//    }
}
