package GUI;

import Controller.Controller;
import Models.Student;
import Models.Teacher;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class ChangePasswordPanel extends UserMainPanel{
    JLabel prePassLabel,newPassLabel,confirmPassLabel;
    JPasswordField prePassField,newPassField,confirmPassField;
    JButton saveButton;
    JCheckBox showPass;
    public ChangePasswordPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setListeners();
     //   if(state==1)
            this.setBounds(0,0,MainFrame.mainFrame.getWidth(),MainFrame.mainFrame.getHeight());
    }

    @Override
    protected void initCom() {
        super.initCom();
        Font font = new Font("",Font.PLAIN,24);
        prePassLabel = new JLabel("Current password");
        newPassLabel = new JLabel("New password");
        confirmPassLabel = new JLabel("Confirm password");
        prePassField = new JPasswordField();
        newPassField = new JPasswordField();
        confirmPassField = new JPasswordField();
        confirmPassField.setFont(font);
        confirmPassLabel.setFont(font);
        newPassField.setFont(font);
        newPassLabel.setFont(font);
        prePassField.setFont(font);
        prePassLabel.setFont(font);

        saveButton = new JButton("Save");
        showPass = new JCheckBox();
    }

    @Override
    protected void align() {
        super.align();
        this.add(prePassLabel);
        this.add(newPassLabel);
        this.add(confirmPassLabel);
        this.add(prePassField);
        this.add(newPassField);
        this.add(confirmPassField);
        this.add(saveButton);
        this.add(showPass);

        prePassLabel.setBounds(400,260,200,40);
        newPassLabel.setBounds(400,320,200,40);
        confirmPassLabel.setBounds(400,380,200,40);

        prePassField.setBounds(650,260,200,40);
        newPassField.setBounds(650,320,200,40);
        confirmPassField.setBounds(650,380,200,40);
        showPass.setBounds(870,400,20,20);
        saveButton.setBounds(700,445,100,30);
        showPass.setOpaque(false);
    }

    private void setListeners(){
        showPass.addActionListener(e -> {
            if(showPass.isSelected()){
                prePassField.setEchoChar((char) 0);
                newPassField.setEchoChar((char) 0);
                confirmPassField.setEchoChar((char) 0);
            }else {
                prePassField.setEchoChar('•');
                newPassField.setEchoChar('•');
                confirmPassField.setEchoChar('•');
            }
        });
        saveButton.addActionListener(e->{
            int state = Controller.changePassword(user,prePassField.getText(),newPassField.getText(),confirmPassField.getText());
            if(state==1) JOptionPane.showMessageDialog(MainFrame.mainFrame,"New passwords do not match");
            else if(state==2) JOptionPane.showMessageDialog(MainFrame.mainFrame,"Invalid current password");
            else if(state==3) JOptionPane.showMessageDialog(MainFrame.mainFrame,"Please fill all fields");
            else if(state==4) JOptionPane.showMessageDialog(MainFrame.mainFrame,"The new password cannot be the same as current password");
            else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Your password is changed successfully");
                if(user instanceof Student) {
                    MainFrame.mainFrame.menuBar=new StudentMenuBar(user,LocalDateTime.now());
                    new StudentMainPanel(user,LocalDateTime.now());
                }
                else {
                    new UserMainPanel(user,LocalDateTime.now());
                    if(((Teacher) user).isEVC ) MainFrame.mainFrame.menuBar =(new EVCMenuBar(user,LocalDateTime.now()));
                    else MainFrame.mainFrame.menuBar =(new TeacherMenuBar(user,LocalDateTime.now()));
                }
                MainFrame.mainFrame.setJMenuBar(MainFrame.mainFrame.menuBar);
            }
        });
    }
}