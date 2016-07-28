import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by user on 2016/7/28.
 */

public class ChatServer {


    public static void main(String[] args) {
        DataInputStream dis = null;
        ServerSocket ss = null;
        Socket s = null;

        try {
            ss = new ServerSocket(8848);
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            while(true) {
                boolean bConnected = false;
                s = ss.accept();
                System.out.println("a client connected");
                bConnected = true;
                dis = new DataInputStream(s.getInputStream());
                while(bConnected) {
                        String str = dis.readUTF();
                        System.out.println(str);
                }
                dis.close();
            }
        } catch (IOException e) {
            try {
                dis.close();
                s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("Client Disconnected");
        }
    }
}
