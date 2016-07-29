import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by user on 2016/7/28.
 */

public class ChatServer {
    ServerSocket ss = null;
    boolean started = false;

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        try {
            ss = new ServerSocket(8848);
            started = true;
        } catch (BindException e) {
            System.out.println("port is in use");
            System.out.println("Please close corresponding application and restart");
            System.exit(0);
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            while(started) {
                Socket s = ss.accept();
                Client c = new Client(s);
                System.out.println("a client connected");
                new Thread(c).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Client implements Runnable {
        Socket s;
        DataInputStream dis;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                }
            } catch (EOFException e) {
                System.out.println("Client Disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(dis != null) dis.close();
                    if(s != null) s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
