package Data;

import Comms.TextMessageSender;
import Interfaces.DataStoreHandler;
import Comms.MessageChannel;
import Interfaces.Message;
import Interfaces.MessageSender;
import Message.TextMessage;

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
            sendResponse("You've not entered any text, please choose a valid nickname", messageChannel);
            return false;
        }
        else if (clientsMap.containsKey(clientNickName)) {
            sendResponse("Oops, that nickname already exists, please choose another nickname", messageChannel);
            return false;
        }
        clientsMap.put(clientNickName, messageChannel);
        sendResponse("Nickname '" + clientNickName + "' is now registered\n", messageChannel);
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

    private void sendResponse(String message, MessageChannel messageChannel) {
        Message msg = new TextMessage("server", message);
        MessageSender sender = new TextMessageSender();
        sender.send(msg, messageChannel);
    }
}
