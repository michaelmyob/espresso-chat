import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RawClientSocket {

    public static void main(String[] args) throws IOException{

        Socket clientSocket = new Socket("localhost", 3000);
        System.out.println("[CLIENT] clientSocket has been created at localhost:3000");

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("[CLIENT] output stream made");

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("[CLIENT] input stream made");

        outToServer.writeBytes("Hello, coffee is really goooood!");
        outToServer.flush();
        outToServer.close();
//        System.out.println(inFromServer.readLine());

        clientSocket.close();
    }

}
