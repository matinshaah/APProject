import GUI.LoginPanel;
import GUI.MainFrame;
import resources.LoadingData;
import resources.MasterLogger;
import javax.swing.*;

public class Main {
    public static void main(String[] args)  {
        MasterLogger.getInstance().log("Application started",false,Main.class);
        new LoadingData();

        SwingUtilities.invokeLater(() -> {


            MainFrame.mainFrame = new MainFrame();
            new LoginPanel();

        });

    }
}
