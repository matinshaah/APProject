package gui;

import controller.Controller;
import models.Course;
import models.Teacher;
import models.User;
import resources.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class DepTempScorePanel extends UserMainPanel{
    JLabel idLabel;
    JTextField idField;
    JButton searchCourse,searchTeacher,searchStudent;
    public DepTempScorePanel(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        idField = new JTextField();
        idLabel = new JLabel("enter the id:");
        idLabel.setFont(new Font("",Font.PLAIN,20));
        searchCourse = new JButton("search course");
        searchStudent = new JButton("search student");
        searchTeacher = new JButton("search teacher");
    }

    @Override
    protected void align() {
        super.align();
        this.add(idField);
        this.add(idLabel);
        this.add(searchCourse);
        this.add(searchStudent);
        this.add(searchTeacher);
        idLabel.setBounds(150,150,150,40);
        idField.setBounds(150,200,150,40);
        searchTeacher.setBounds(340,200,150,40);
        searchStudent.setBounds(500,200,150,40);
        searchCourse.setBounds(660,200,150,40);
    }

    private void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        searchTeacher.addActionListener(e->{
            Teacher teacher= Controller.findTeacherByID(idField.getText());
            if(teacher==null){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Teacher not found.");
                MasterLogger.getInstance().log("Teacher not found",true,this.getClass());
            }else new DepTeacherScorePanel(teacher,lastLogin);
        });
        searchStudent.addActionListener(e->{
            User user =Controller.findUserById(idField.getText());
            if(user==null||user instanceof Teacher){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Student not found.");
                MasterLogger.getInstance().log("Student not found",true,this.getClass());
            }else new DepStudentScorePanel(user,lastLogin);
        });
        searchCourse.addActionListener(e->{
            Course course =Controller.findCourseByID(idField.getText());
            if(course==null){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Course not found.");
                MasterLogger.getInstance().log("Course not found",true,this.getClass());
            }else new DepCourseScorePanel(user,lastLogin,course);
        });
    }
}
