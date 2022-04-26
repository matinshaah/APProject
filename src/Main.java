import GUI.LoginPanel;
import GUI.MainFrame;
import Models.Student;
import Models.User;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        new Test();

        SwingUtilities.invokeLater(() -> {

            MainFrame.mainFrame = new MainFrame();
            new LoginPanel();

        });
    }
}
