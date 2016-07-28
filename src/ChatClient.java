/**
 * Created by user on 2016/7/27.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Frame{

    TextArea taContent = new TextArea();
    TextField tfTXT = new TextField();

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
                System.exit(0);
            }
        });
        tfTXT.addActionListener(new TFListener());
        setVisible(true);
        connect();
    }

    public void connect() {
        try {
            Socket s = new Socket("127.0.0.1",8888);
System.out.println("connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TFListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = tfTXT.getText().trim();
            taContent.setText(s);
            tfTXT.setText("");
        }
    }
}