import java.io.Serializable;

public class TextMessage implements Message, Serializable {

    public String messageContents;

    public TextMessage(String messageContents) {
        this.messageContents = messageContents;

    }

    public String toString() {
        return messageContents;
    }

    public boolean equals(Object obj) {
        return (obj instanceof String) &&
                this.messageContents.equals(obj.toString());
    }
}
