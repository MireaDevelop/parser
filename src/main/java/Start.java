import jFrames.*;
import javax.swing.*;

public class Start {
    public static void main(String[] args) {

        Settings.getSettings();
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            new jFrame();
        });
    }
}