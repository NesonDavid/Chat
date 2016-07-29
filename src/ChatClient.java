/**
 * Created by user on 2016/7/27.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient extends Frame{

    TextArea taContent = new TextArea();
    TextField tfTXT = new TextField();
    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;
    Thread tRecv = new Thread(new RecThread());

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400, 300);
        this.setSize(300, 300);
        add(tfTXT, BorderLayout.SOUTH);
        add(taContent, BorderLayout.NORTH);
        pack();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
                System.exit(0);
            }
        });
        tfTXT.addActionListener(new TFListener());
        setVisible(true);
        connect();

        tRecv.start();
    }

    public void connect() {
        try {
            s = new Socket("127.0.0.1",8848);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());
            bConnected = true;
System.out.println("connected");
        } catch(UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
                dos.close();
                dis.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

//        try {
//            bConnected = false;
//
//            tRecv.join();
//
//        }catch (InterruptedException e) {
//                e.printStackTrace();
//        }finally {
//            try {
//                dos.close();
//                dis.close();
//                s.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private class TFListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = tfTXT.getText().trim();
            //taContent.setText(str);
            tfTXT.setText("");
            try {
                dos.writeUTF(str);
                dos.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class RecThread implements Runnable {
        @Override
        public void run() {
            try {
                while(bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    taContent.setText(taContent.getText() + str + '\n' );
                }
            } catch(SocketException e) {
                System.out.println("exit,bye");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}