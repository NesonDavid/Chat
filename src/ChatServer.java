import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/28.
 */

public class ChatServer {
    ServerSocket ss = null;
    boolean started = false;

    List<Client> clients = new ArrayList<>();

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
                clients.add(c);
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
        private DataOutputStream dos = null;
        private boolean bConnected = false;

        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void send(String str) {
            try {
                dos.writeUTF(str);
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
                    for(int i=0; i<clients.size(); i++) {
                        Client c = clients.get(i);
                        c.send(str);
                    }

//                    for(Iterator<Client> it = clients.iterator(); it.hasNext();) {
//                        Client c = it.next();
//                        c.send(str);
//                    }
//
//                    Iterator<Client> it = clients.iterator();
//                    while(it.hasNext()) {
//                        Client c= it.next();
//                        c.send(str);
//                    }


                }
            } catch (EOFException e) {
                System.out.println("Client Disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(dis != null) dis.close();
                    if(dos != null) dos.close();
                    if(s != null) s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}
