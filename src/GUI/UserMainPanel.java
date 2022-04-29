package GUI;

import Models.*;
import resources.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserMainPanel extends JPanel {
    User user;
    LocalDateTime lastLogin;
    final private JLabel lastLoginLabel = new JLabel(),currentTimeLabel=new JLabel();
    final private JLabel nameLabel = new JLabel(),emailLabel=new JLabel();
    final private JLabel imgLabel = new JLabel();
    public UserMainPanel(User user,LocalDateTime loginTime){
        this.user=user;
        lastLogin=loginTime;
        if(initPanel()) return;
        initCom();
        align();
        if(checkLastLogin(LocalDateTime.now(),lastLogin,user)){
            MainFrame.mainFrame.update();
        }
        MainFrame.mainFrame.update();
    }
    private boolean initPanel(){
        MasterLogger.getInstance().log("panel initialized",false,this.getClass());
        MainFrame.mainFrame.getContentPane().removeAll();
        MainFrame.mainFrame.add(this);
        this.setBounds(0,0, MainFrame.WIDTH -15, MainFrame.HEIGHT -85);
        this.setBackground(user.color);
        this.setLayout(null);
        MainFrame.mainFrame.update();
        return false;
    }

    protected void initCom(){
        MasterLogger.getInstance().log("panel components initialized",false,this.getClass());
        setTime();
        Font labelFont = new Font("",Font.PLAIN,20);
        lastLoginLabel.setFont(labelFont);
        currentTimeLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        nameLabel.setText("Name: "+user.name);
        emailLabel.setText("Email: "+user.email);
        imgLabel.setIcon(new ImageIcon(user.image));
    }

    protected void align(){
        MasterLogger.getInstance().log("panel components aligned",false,this.getClass());
        this.add(lastLoginLabel);
        lastLoginLabel.setBounds(1100,650,200,30);
        this.add(currentTimeLabel);
        currentTimeLabel.setBounds(50,650,350,30);
        this.add(nameLabel);
        nameLabel.setBounds(20,40,200,30);
        this.add(emailLabel);
        emailLabel.setBounds(20,70,350,30);
        this.add(imgLabel);
        imgLabel.setBounds(600,20,96,96);
    }
    private void setTime() {
        MasterLogger.getInstance().log("set time",false,this.getClass());
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        Timer t = new Timer(1000, e -> {
            LocalDateTime now = LocalDateTime.now();
            currentTimeLabel.setText("Current Time: "+dtf.format(now));
        });
        t.start();
        lastLoginLabel.setText("Last login: "+DateTimeFormatter.ofPattern("HH:mm:ss").format(lastLogin));
    }
    public static boolean checkLastLogin(LocalDateTime now,LocalDateTime lastLogin,User user){
        MasterLogger.getInstance().log("check last login",false,UserMainPanel.class);
        Duration timeElapsed =Duration.between(lastLogin,now);
        long difference = timeElapsed.toMillis();
        long hourToMillis =3600000;
        if(difference >3*hourToMillis){
            MasterLogger.getInstance().log("force to change password",false,UserMainPanel.class);
            JOptionPane.showMessageDialog(MainFrame.mainFrame,"You must change your password");
            MainFrame.mainFrame.getContentPane().removeAll();
            MainFrame.mainFrame.menuBar.removeAll();
            new ChangePasswordPanel(user,LocalDateTime.now());
            return true;
        }
        return false;
    }
}
