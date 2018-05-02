package Interfaces;
import Channel.ClientSocket;

import java.util.List;

public interface DataStoreHandler {
    ClientSocket getClient(String nickName);
    boolean addClient(String nickName, ClientSocket clientSocket);
    boolean removeClient(String nickName);
    List<String> getAllClients();

}
