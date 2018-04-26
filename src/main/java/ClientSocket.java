import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {

    private Socket socket;
    public String clientNickName;


    public Socket getSocket() {
        return socket;
    }

    public ClientSocket(String nickName, Socket socket) {
        this.clientNickName = nickName;
        this.socket = socket;
    }

    public InputStream getInputStream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void sendAMessageThroughSocket(Message message) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            writer.println(message);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error sending message!!!!!!!! Please try again");
        }
    }


    public void sendATextMessage(String message) {
        Message msg = new TextMessage(message);
        sendAMessageThroughSocket(msg);
    }


}
