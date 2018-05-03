package Interfaces;
import Comms.MessageChannel;

import java.util.List;

public interface DataStoreHandler {
    MessageChannel getClient(String nickName);
    boolean addClient(MessageChannel messageChannel);
    boolean removeClient(MessageChannel messageChannel);
    List<String> getAllClients();

}
