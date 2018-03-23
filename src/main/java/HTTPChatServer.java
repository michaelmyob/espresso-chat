public class HTTPChatServer implements Server {

    public Message respond(Client client, Message message) {
        return new TextMessage(message.toString());
    }

}
