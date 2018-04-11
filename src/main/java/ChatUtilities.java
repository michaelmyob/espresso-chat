import java.io.*;
import java.net.Socket;

public class ChatUtilities {


    public static void sendAMessageThroughSocket(Socket socket, Message message) {

        try {

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            writer.println(message);
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
