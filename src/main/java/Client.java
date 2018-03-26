public interface Client {
    boolean connect(Server server);
    void talk(Server server, Message message);
    boolean receive(Message message);
}
