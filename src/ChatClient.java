/**
 * Created by user on 2016/7/27.
 */
import java.awt.*;

public class ChatClient extends Frame{
    public static void main(String[] args) {
        new ChatClient().launchFrame();
    }

    public void launchFrame() {
        setLocation(400,300);
        this.setSize(300,300);
        setBackground(Color.red);
        setVisible(true);
    }
}