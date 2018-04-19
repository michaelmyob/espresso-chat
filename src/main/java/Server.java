public interface Server {
    void addClientIntoMap(String clientNickName, ClientSocket clientSocket);
    void run();
}
