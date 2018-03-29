import java.io.*;
import java.net.Socket;

public class RawClientSocket {

    public static void main(String[] args) throws IOException{

//        Socket clientSocket = new Socket("localhost", 3000);
//        System.out.println("[CLIENT] clientSocket has been created at localhost:3000");
//
//        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
//        outToServer.print("Hello, coffee is really goooood!");
//
//        outToServer.close();
//
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        System.out.println("[CLIENT] Server Says: " + inFromServer.readLine());
//
//        clientSocket.close();

        Socket echoSocket = new Socket("localhost", 3000);
        OutputStream os = echoSocket.getOutputStream();
        DataInputStream is = new DataInputStream(echoSocket.getInputStream());

        int c;

        while ((c = System.in.read()) != -1) {
            os.write((byte)c);
            if (c == '\n') {
                os.flush();
            }
        }

        os.close();
        is.close();
        echoSocket.close();
    }

}
