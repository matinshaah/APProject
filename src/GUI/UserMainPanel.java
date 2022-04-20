package GUI;

import Models.Student;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserMainPanel extends JPanel {
    User user;
    LocalDateTime lastLogin;
    final protected JMenuBar menuBar = new JMenuBar();
    protected MyJMenu registration,educationalService,reportCard,applications;
    private MyMenuItem profile;
    private MyMenuItem courseList,teacherList,weeklySchedule,examList,tempScores;
    final private MyMenuItem mainPage = new MyMenuItem("Main Page"),exit = new MyMenuItem("Log out");
    final private JLabel lastLoginLabel = new JLabel(),currentTimeLabel=new JLabel();
    final private JLabel nameLabel = new JLabel(),emailLabel=new JLabel();
    final private JLabel imgLabel = new JLabel();
    public UserMainPanel(User user,LocalDateTime loginTime){
        this.user=user;
        lastLogin=loginTime;
        if(initPanel()) return;
        initCom();
        align();
        MainFrame.mainFrame.update();
    }
    private boolean initPanel(){
        MainFrame.mainFrame.getContentPane().removeAll();
        MainFrame.mainFrame.setJMenuBar(menuBar);
        if(checkLastLogin(LocalDateTime.now())){
            MainFrame.mainFrame.update();
            return true;
        }
        MainFrame.mainFrame.add(this);
        this.setBounds(0,0, MainFrame.WIDTH -15, MainFrame.HEIGHT -85);
        this.setBackground(Color.blue);
        this.setLayout(null);
        MainFrame.mainFrame.update();
        return false;
    }

    protected void initCom(){
        setListeners();
        setTime();
        Font labelFont = new Font("",Font.PLAIN,15);
        lastLoginLabel.setFont(labelFont);
        currentTimeLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        nameLabel.setText("Name: "+user.name);
        emailLabel.setText("Email: "+user.email);
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
        imgLabel.setIcon(new ImageIcon(user.image));
    }

    protected void align(){
        setMenuBar();
        this.add(lastLoginLabel);
        lastLoginLabel.setBounds(1100,650,200,20);
        this.add(currentTimeLabel);
        currentTimeLabel.setBounds(50,650,250,20);
        this.add(nameLabel);
        nameLabel.setBounds(20,40,200,20);
        this.add(emailLabel);
        emailLabel.setBounds(20,70,300,20);
        this.add(imgLabel);
        imgLabel.setBounds(600,20,96,96);

    }
    protected void setMenuBar(){
        menuBar.add(mainPage);
        mainPage.setPreferredSize(new Dimension(mainPage.getPreferredSize().width,40));
//        menuBar.setBackground(Color.green);
        menuBar.add(registration);
        registration.add(courseList);
        registration.add(teacherList);
        menuBar.add(educationalService);
        educationalService.add(weeklySchedule);
        educationalService.add(examList);
        educationalService.add(applications);
        menuBar.add(reportCard);
        reportCard.add(tempScores);
        menuBar.add(profile);
        setNewRegistration();
        menuBar.add(exit);
        MainFrame.mainFrame.update();
    }
    protected void setNewRegistration(){ //for adding EVC registerNewUser myMenuItem to menuBar

    }

    protected void setListeners(){
        exit.addActionListener(e -> {
            menuBar.removeAll();
            MainFrame.mainFrame.update();
            new LoginPanel();

        });
        mainPage.addActionListener(e -> {
            if(user instanceof Student) new StudentMainPanel(user,lastLogin);
            else new TeacherMainPanel(user,lastLogin);
        });
    }


    private void setTime() {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Timer t = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            currentTimeLabel.setText("Current Time: "+dtf.format(now));
        });
        t.start();
        lastLoginLabel.setText("Last login: "+DateTimeFormatter.ofPattern("HH:mm:ss").format(lastLogin));
    }
    protected boolean checkLastLogin(LocalDateTime now){
        Duration timeElapsed =Duration.between(lastLogin,now);
        long difference = timeElapsed.toMillis();
        long hourToMillis =3600000;
        if(difference >3*hourToMillis){
            JOptionPane.showMessageDialog(MainFrame.mainFrame,"Please login again");
            MainFrame.mainFrame.getContentPane().removeAll();
            new LoginPanel();
            return true;
        }
        return false;
    }
}
