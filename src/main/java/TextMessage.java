
public class TextMessage implements Message {

    public String messageContents;

    public TextMessage(String messageContents) {
        this.messageContents = messageContents;

    }

    @Override
    public String toString() {
        return messageContents;
    }
}
