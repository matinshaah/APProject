package gui;

import controller.Controller;
import models.Course;
import models.User;
import resources.MasterLogger;

import javax.swing.*;
import java.time.LocalDateTime;

public class EVCCoursePanel extends CoursesListPanel{
    JLabel label;
    JTextField field;
    JButton editCourse,newCourse;
    public EVCCoursePanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        label= new JLabel("Enter the course id to edit:");
        field = new JTextField();
        editCourse = new JButton("search");
        newCourse = new JButton("New Course");
    }

    @Override
    protected void align() {
        super.align();
        this.add(label);
        this.add(field);
        this.add(editCourse);
        this.add(newCourse);
        label.setBounds(300,500,150,30);
        field.setBounds(460,500,150,30);
        editCourse.setBounds(615,505,80,20);
        newCourse.setBounds(800,500,120,30);
    }

    private void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        editCourse.addActionListener(e -> {
            Course course =Controller.findCourseByID(field.getText());
            if(course==null) {
                MasterLogger.getInstance().log("course not found",true,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Course not found");
            }else if(! course.absCourse.department.name.equals(user.department.name)){
                MasterLogger.getInstance().log("editing course of other departments",true,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"You can't edit this course");
            } else new CourseEditPanel(user,lastLogin,course);
        });
        newCourse.addActionListener(e -> new CourseEditPanel(user,lastLogin,null));
    }
}
