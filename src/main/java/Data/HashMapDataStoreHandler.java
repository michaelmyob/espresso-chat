package Data;

import Interfaces.DataStoreHandler;
import Channel.MessageChannel;

import java.util.*;

public class HashMapDataStoreHandler implements DataStoreHandler {

    Map clientsMap;

    public HashMapDataStoreHandler(Map clientsMap) {
        this.clientsMap = clientsMap;
    }


    public MessageChannel getClient(String clientNickName) {
        if (clientsMap.containsKey(clientNickName)) {
            return (MessageChannel) clientsMap.get(clientNickName);

        } else {
            return null;
        }
    }

    public boolean addClient(String clientNickName, MessageChannel messageChannel) {
        if (clientNickName.isEmpty()) {
            messageChannel.sendATextMessage("You've not entered any text, please choose a valid nickname");
            return false;
        }
        else if (clientsMap.containsKey(clientNickName)) {
            messageChannel.sendATextMessage("Oops, that nickname already exists, please choose another nickname");
            return false;
        }
        clientsMap.put(clientNickName, messageChannel);
        messageChannel.sendATextMessage("Nickname '" + clientNickName + "' is now registered\n");
        return true;
    }

    public boolean removeClient(String clientNickName) {
        if (clientsMap.containsKey(clientNickName)) {
            clientsMap.remove(clientNickName);
            return true;
        }
        return false;
    }

    public List<String> getAllClients() {
        List<String> clientsList = new ArrayList<>();
        clientsList.addAll(clientsMap.keySet());
        return clientsList;
    }
}
