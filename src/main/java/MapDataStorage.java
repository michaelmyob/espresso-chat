import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MapDataStorage implements DataService {

    private static Map clientsMap;
    private static MapDataStorage mapDataStorage = new MapDataStorage();

    private MapDataStorage() {
       clientsMap = new ConcurrentHashMap<String, ClientSocket>();
    }

    public static MapDataStorage getInstance() {
        return mapDataStorage;
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
            System.out.println("You've not entered any text, please choose a valid nickname");
            return false;
        }
        else if (clientsMap.containsKey(clientNickName)) {
            System.out.println("Oops, that nickname already exists, please choose another nickname");
            return false;
        }
        clientsMap.put(clientNickName, clientSocket);
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
