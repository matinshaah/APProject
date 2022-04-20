package GUI;

import Models.EducationViceChar;
import Models.Teacher;


import java.time.LocalDateTime;

public class EVC_MainPanel extends TeacherMainPanel{
    EducationViceChar evc;
    private MyMenuItem educationalStatus,newTeacher,newStudent;
    private MyJMenu registerNewUser;
    public EVC_MainPanel(Teacher teacher, LocalDateTime loginTime) {
        super(teacher,loginTime);

    }
    @Override
    protected void initCom(){
        evc = (EducationViceChar) teacher;
        super.initCom();
        educationalStatus = new MyMenuItem("Educational Status");
        registerNewUser = new MyJMenu("Register New User");
        newTeacher = new MyMenuItem("New Teacher");
        newStudent = new MyMenuItem("New Student");
    }
    @Override
    protected void align(){
        super.align();
    }
    @Override
    protected void setMenuBar(){
        super.setMenuBar();
        reportCard.add(educationalStatus);
        educationalStatus.add(newTeacher);
        educationalStatus.add(newStudent);

    }
    @Override
    protected void setNewRegistration(){
        menuBar.add(registerNewUser);
    }
    @Override
    protected void setListeners(){
        super.setListeners();
    }
}
