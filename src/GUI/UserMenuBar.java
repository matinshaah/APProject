package GUI;

import Models.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class UserMenuBar extends JMenuBar {
    User user;
    LocalDateTime lastLogin;
    protected MyJMenu registration,educationalService,reportCard,requests;
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
        requests = new MyJMenu("Requests");
        tempScores = new MyMenuItem("Temporary Scores");
        profile = new MyMenuItem("User Profile");
    }

    protected void setMenuBar(){
        this.add(mainPage);
//        this.setBackground(Color.cyan);
        mainPage.setOpaque(false);
        mainPage.setPreferredSize(new Dimension(mainPage.getPreferredSize().width,40));
        this.add(registration);
        registration.setBackground(Color.green);
        registration.add(courseList);
        courseList.setOpaque(false);
        registration.add(teacherList);
        this.add(educationalService);
        educationalService.add(weeklySchedule);
        educationalService.add(examList);
        educationalService.add(requests);
        this.add(reportCard);
        reportCard.add(tempScores);
        this.add(profile);
        profile.setOpaque(false);
        setNewRegistration();
        this.add(exit);
        exit.setOpaque(false);
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
            if(user instanceof Student) new StudentProfilePanel(user,lastLogin,(Student) user);
            else  new TeacherProfilePanel( user,lastLogin,(Teacher) user);
            MainFrame.mainFrame.update();
        });
        weeklySchedule.addActionListener(e -> new WeeklySchedulePanel(user,lastLogin));
        examList.addActionListener(e -> new WeeklySchedulePanel(user,lastLogin));

    }
}
