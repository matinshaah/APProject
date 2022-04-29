import GUI.LoginPanel;
import GUI.MainFrame;
import resources.MasterLogger;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MasterLogger.getInstance().log("Application started",false,Main.class);
        new Test();

        SwingUtilities.invokeLater(() -> {

            MainFrame.mainFrame = new MainFrame();
            new LoginPanel();

        });
        MasterLogger.getInstance().log("Application finished",false,Main.class);

    }
}
