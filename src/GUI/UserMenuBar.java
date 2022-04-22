package GUI;

import Models.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class UserMenuBar extends JMenuBar {
    User user;
    LocalDateTime lastLogin;
    protected MyJMenu registration,educationalService,reportCard,applications;
    protected MyMenuItem profile;
    protected MyMenuItem courseList,teacherList,weeklySchedule,examList,tempScores;
    final private MyMenuItem mainPage = new MyMenuItem("Main Page"),exit = new MyMenuItem("Log out");
    UserMenuBar(User user,LocalDateTime lastLogin){
        super();
        this.user = user;
        this.lastLogin = lastLogin;
        initCom();
        setMenuBar();
        setListeners();
    }
    protected void initCom(){
        reportCard = new MyJMenu("Report Card");
        registration = new MyJMenu("Registration and Restoration");
        educationalService = new MyJMenu("Educational Service");
        courseList = new MyMenuItem("Courses List");
        teacherList = new MyMenuItem("Teachers List");
        weeklySchedule = new MyMenuItem("Weekly Schedule");
        examList = new MyMenuItem("Exam List");
        applications = new MyJMenu("Applications");
        tempScores = new MyMenuItem("Temporary Scores");
        profile = new MyMenuItem("User Profile");
    }

    protected void setMenuBar(){
        this.add(mainPage);
        mainPage.setPreferredSize(new Dimension(mainPage.getPreferredSize().width,40));
//        this.setBackground(Color.green);
        this.add(registration);
        registration.add(courseList);
        registration.add(teacherList);
        this.add(educationalService);
        educationalService.add(weeklySchedule);
        educationalService.add(examList);
        educationalService.add(applications);
        this.add(reportCard);
        reportCard.add(tempScores);
        this.add(profile);
        setNewRegistration();
        this.add(exit);
        MainFrame.mainFrame.update();
    }
    protected void setNewRegistration(){ //for adding EVC registerNewUser myMenuItem to menuBar

    }

    protected void setListeners(){
        exit.addActionListener(e -> {
            this.removeAll();
            MainFrame.mainFrame.update();
            new LoginPanel();

        });
        mainPage.addActionListener(e -> {
            if(user instanceof Student) new StudentMainPanel(user,lastLogin);
            else new UserMainPanel(user,lastLogin);
        });
        profile.addActionListener(e -> {
            if(UserMainPanel.checkLastLogin(LocalDateTime.now(),lastLogin)) return;
            MainFrame.mainFrame.getContentPane().removeAll();
            if(user instanceof Student) new StudentProfilePanel(user,lastLogin);
            else if(user instanceof EducationViceChar) new TeacherProfilePanel(user,lastLogin);
            else if(user instanceof DepartmentChair) new TeacherProfilePanel(user,lastLogin);
            else  new TeacherProfilePanel( user,lastLogin);
            MainFrame.mainFrame.update();
        });
    }

}
