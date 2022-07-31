package gui;

import models.User;

import java.time.LocalDateTime;

public class EVCMenuBar extends TeacherMenuBar{
    private MyMenuItem educationalStatus,newTeacher,newStudent;
    private MyJMenu registerNewUser;
    EVCMenuBar(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);
    }

    @Override
    protected void initCom() {
        super.initCom();
        educationalStatus = new MyMenuItem("Educational Status");
        registerNewUser = new MyJMenu("Register New User");
        newTeacher = new MyMenuItem("New Teacher");
        newStudent = new MyMenuItem("New Student");
    }
    @Override
    protected void setMenuBar(){
        super.setMenuBar();
        reportCard.add(educationalStatus);
        registerNewUser.setOpaque(false);
        registerNewUser.add(newTeacher);
        registerNewUser.add(newStudent);
    }
    @Override
    protected void setNewRegistration(){
        this.add(registerNewUser);
    }
    protected void setListeners(){
        super.setListeners();
        newTeacher.addActionListener(e ->new TeacherEditPanel(user,lastLogin,null));
        newStudent.addActionListener(e ->new StudentEditPanel(user,lastLogin,null));
        educationalStatus.addActionListener(e-> new EVCEduStatusPanel(user,lastLogin));
    }
}
