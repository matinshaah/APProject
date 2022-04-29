package GUI;

import Models.Teacher;
import Models.User;
import resources.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class TeacherProfilePanel extends TeacherEditPanel{
    JButton changePass;
    JMenu color;
    JMenuItem blue,green,red,pink;
    public TeacherProfilePanel(User user, LocalDateTime loginTime, Teacher teacher) {
        super(user,loginTime,teacher);

    }
    @Override
    protected void initCom() {
        changePass = new JButton("change password");
        color = new JMenu("color");
        color.setBorder(BorderFactory.createLineBorder(Color.black));
        color.setOpaque(true);
        blue = new JMenuItem("blue");
        blue.setForeground(Color.blue);
        green = new JMenuItem("green");
        green.setForeground(Color.green);
        red = new JMenuItem("red");
        red.setForeground(Color.red);
        pink = new JMenuItem("pink");
        pink.setForeground(Color.pink);
        super.initCom();
    }

    @Override
    protected void align() {
        super.align();
        this.add(changePass);
        changePass.setBounds(800,100,150,30);
        this.add(color);
        color.setBounds(1100,100,80,30);
        color.add(blue);
        color.add(pink);
        color.add(green);
        color.add(red);
    }
    @Override
    protected void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        super.setListeners();
        changePass.addActionListener(e-> new ChangePasswordPanel(user,LocalDateTime.now()));
        blue.addActionListener(e->{
            user.color=Color.blue;
            new TeacherProfilePanel(user,lastLogin,(Teacher) user);
        });
        red.addActionListener(e->{
            user.color=Color.red;
            new TeacherProfilePanel(user,lastLogin,(Teacher) user);
        });
        green.addActionListener(e->{
            user.color=Color.green;
            new TeacherProfilePanel(user,lastLogin,(Teacher) user);
        });
        pink.addActionListener(e->{
            user.color=Color.pink;
            new TeacherProfilePanel(user,lastLogin,(Teacher) user);
        });
    }
}