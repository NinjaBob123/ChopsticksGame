package green.apps;

import javax.swing.*;

public class WindowHandler {

    private JFrame frame;

    public WindowHandler() {
        frame = new JFrame("Chopsticks Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

}
