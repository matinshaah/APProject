package GUI;

import Controller.Controller;
import Models.Teacher;
import Models.User;
import resources.MasterLogger;

import javax.swing.*;
import java.time.LocalDateTime;

public class DCTeacherPanel extends TeacherListPanel{
    JLabel label;
    JTextField field;
    JButton editTeacher,newTeacher;
    public DCTeacherPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        label= new JLabel("Enter the Teacher id to edit:");
        field = new JTextField();
        editTeacher = new JButton("search");
        newTeacher = new JButton("New Teacher");
    }

    @Override
    protected void align() {
        super.align();
        this.add(label);
        this.add(field);
        this.add(editTeacher);
        this.add(newTeacher);
        label.setBounds(270,500,180,30);
        field.setBounds(460,500,150,30);
        editTeacher.setBounds(615,505,80,20);
        newTeacher.setBounds(800,500,120,30);
    }
    private void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        editTeacher.addActionListener(e -> {
            Teacher teacher=Controller.findTeacherByID(field.getText().trim());
            if(teacher==null) {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Teacher not found");
                MasterLogger.getInstance().log("Teacher not found",true,this.getClass());
            }else if(! teacher.department.name.equals(user.department.name)){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"You can't edit this teacher");
                MasterLogger.getInstance().log("Teacher from different department",true,this.getClass());
            } else new TeacherEditPanel(user,lastLogin,teacher);
        });
        newTeacher.addActionListener(e -> new TeacherEditPanel(user,lastLogin,null));
    }
}
