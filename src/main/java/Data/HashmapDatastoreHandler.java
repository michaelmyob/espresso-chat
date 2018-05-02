package Data;

import Interfaces.DataStoreHandler;
import Channel.ClientSocket;

import java.util.*;

public class HashmapDatastoreHandler implements DataStoreHandler {

    HashmapDataStore hashmapDataStore;
    Map clientsMap;

    public HashmapDatastoreHandler() {
        hashmapDataStore = HashmapDataStore.getInstance();
        clientsMap = hashmapDataStore.getClientsMap();
    }


    public ClientSocket getClient(String clientNickName) {
        if (clientsMap.containsKey(clientNickName)) {
            return (ClientSocket) clientsMap.get(clientNickName);

        } else {
            return null;
        }
    }

    public boolean addClient(String clientNickName, ClientSocket clientSocket) {
        if (clientNickName.isEmpty()) {
            clientSocket.sendATextMessage("You've not entered any text, please choose a valid nickname");
            return false;
        }
        else if (clientsMap.containsKey(clientNickName)) {
            clientSocket.sendATextMessage("Oops, that nickname already exists, please choose another nickname");
            return false;
        }
        clientsMap.put(clientNickName, clientSocket);
        clientSocket.sendATextMessage("Nickname '" + clientNickName + "' is now registered\n");
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
