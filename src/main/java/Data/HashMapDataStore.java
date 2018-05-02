package Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Channel.MessageChannel;

public class HashMapDataStore {

    private static Map clientsMap;

    private HashMapDataStore() {
        clientsMap = new ConcurrentHashMap<String, MessageChannel>();
    }

    public static HashMapDataStore getInstance() {
        return hashMapDataStore;
    }

    private static HashMapDataStore hashMapDataStore = new HashMapDataStore();

    public Map getClientsMap() {
        return clientsMap;
    }
}
