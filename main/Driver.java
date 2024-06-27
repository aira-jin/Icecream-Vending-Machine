
import Controller.NewMainFrame;

import javax.swing.*;

public class Driver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NewMainFrame();
        });
    }
}