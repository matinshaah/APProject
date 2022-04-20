package GUI;

import Models.Teacher;
import Models.User;

import java.time.LocalDateTime;

public class TeacherMainPanel extends UserMainPanel{
    Teacher teacher;
    public TeacherMainPanel(User teacher, LocalDateTime loginTime){
        super(teacher,loginTime);

    }
    @Override
    protected void initCom(){
        this.teacher=(Teacher) user;
        super.initCom();
    }
    @Override
    protected void align(){
        super.align();

    }
    @Override
    protected void setMenuBar(){
        super.setMenuBar();

    }
    @Override
    protected void setListeners(){
        super.setListeners();

    }
}
