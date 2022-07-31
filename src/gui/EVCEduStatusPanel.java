package gui;

import controller.Controller;
import models.Teacher;
import models.User;
import resources.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EVCEduStatusPanel extends UserMainPanel{
    JLabel idLabel;
    JTextField idField;
    JButton searchById,searchByName;
    public EVCEduStatusPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setListeners();
    }
    @Override
    protected void initCom() {
        super.initCom();
        idField = new JTextField();
        idLabel = new JLabel("enter the student id or name:");
        idLabel.setFont(new Font("",Font.PLAIN,20));
        searchById = new JButton("search by id");
        searchByName = new JButton("search by name");

    }

    @Override
    protected void align() {
        super.align();
        this.add(idField);
        this.add(idLabel);
        this.add(searchByName);
        this.add(searchById);
        idLabel.setBounds(150,150,300,40);
        idField.setBounds(150,200,150,40);
        searchByName.setBounds(380,200,150,40);
        searchById.setBounds(550,200,150,40);
    }
    private void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        searchById.addActionListener(e->{
            User user = Controller.findUserById(idField.getText());
            if(user==null||user instanceof Teacher){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Student not found");
                MasterLogger.getInstance().log("student not found",true,this.getClass());
            }else new StudentEduStatusPanel(user,lastLogin);
        });
        searchByName.addActionListener(e->{
            User user = User.getUserByName(idField.getText());
            if(user==null||user instanceof Teacher){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Student not found");
                MasterLogger.getInstance().log("student not found",true,this.getClass());
            }else new StudentEduStatusPanel(user,lastLogin);
        });
    }
}
