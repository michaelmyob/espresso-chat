package Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Channel.ClientSocket;

public class HashmapDataStore {

    private static Map clientsMap;

    private HashmapDataStore() {
        clientsMap = new ConcurrentHashMap<String, ClientSocket>();
    }

    public static HashmapDataStore getInstance() {
        return hashmapDataStore;
    }

    private static HashmapDataStore hashmapDataStore = new HashmapDataStore();

    public Map getClientsMap() {
        return clientsMap;
    }
}
