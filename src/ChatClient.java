/**
 * Created by user on 2016/7/27.
 */
import java.awt.*;

public class ChatClient extends Frame{

    TextArea taContent = new TextArea();
    TextField tf = new TextField();

    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400, 300);
        this.setSize(300, 300);
        add(tf, BorderLayout.SOUTH);
        add(taContent, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }
}