package GUI;

import Models.EducationViceChar;
import Models.User;

import java.time.LocalDateTime;

public class EVCMenuBar extends TeacherMenuBar{
    EducationViceChar evc;
    private MyMenuItem educationalStatus,newTeacher,newStudent;
    private MyJMenu registerNewUser;
    EVCMenuBar(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);
    }

    @Override
    protected void initCom() {
        this.evc = (EducationViceChar) teacher;
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
        registerNewUser.add(newTeacher);
        registerNewUser.add(newStudent);

    }
    @Override
    protected void setNewRegistration(){
        this.add(registerNewUser);
    }
    protected void setListeners(){
        super.setListeners();

    }
}
