package Interfaces;
import Channel.MessageChannel;

import java.util.List;

public interface DataStoreHandler {
    MessageChannel getClient(String nickName);
    boolean addClient(String nickName, MessageChannel messageChannel);
    boolean removeClient(String nickName);
    List<String> getAllClients();

}
