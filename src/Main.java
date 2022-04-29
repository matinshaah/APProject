import Controller.Controller;
import GUI.LoginPanel;
import GUI.MainFrame;
import Models.Course;
import Models.Request;
import Models.Student;
import Models.User;
import resources.LoadingData;
import resources.MasterLogger;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args)  {
        MasterLogger.getInstance().log("Application started",false,Main.class);
       // new Test();
        new LoadingData();

//        for (User s :
//                User.userList) {
//            if(s instanceof Student) Student.addCourse((Student) s, Controller.findCourseByID("1400210001"));
//        }
        SwingUtilities.invokeLater(() -> {

            MainFrame.mainFrame = new MainFrame();
            new LoginPanel();

        });

    }
}
