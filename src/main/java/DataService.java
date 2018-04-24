import java.util.List;

public interface DataService {
    ClientSocket getClient(String nickName);
    boolean addClient(String nickName, ClientSocket clientSocket);
    boolean removeClient(String nickName);
    List<String> getAllClients();

}
