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

    public boolean addClient(MessageChannel messageChannel) {
        if (messageChannel.clientNickName.isEmpty()) {
            sendResponse("You've not entered any text, please choose a valid nickname", messageChannel);
            return false;
        }
        else if (clientsMap.containsKey(messageChannel.clientNickName)) {
            sendResponse("Oops, that nickname already exists, please choose another nickname", messageChannel);
            return false;
        }
        clientsMap.put(messageChannel.clientNickName, messageChannel);
        sendResponse("Nickname '" + messageChannel.clientNickName + "' is now registered\n", messageChannel);
        return true;
    }

    public boolean removeClient(MessageChannel messageChannel) {
        if (clientsMap.containsKey(messageChannel.clientNickName)) {
            clientsMap.remove(messageChannel.clientNickName);
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
