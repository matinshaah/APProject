package GUI;

import Models.Teacher;
import Models.User;

import java.time.LocalDateTime;

public class TeacherMenuBar extends UserMenuBar{
    Teacher teacher;
    TeacherMenuBar(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);
    }

    @Override
    protected void initCom() {
        this.teacher = (Teacher) user;
        super.initCom();
    }

    @Override
    protected void setMenuBar(){
        super.setMenuBar();

    }
    @Override
    protected void setListeners(){
        super.setListeners();
        courseList.addActionListener(e -> {
            if(! teacher.isEVC )
                new CoursesListPanel(user,lastLogin);
            else  new EVCCoursePanel(user,lastLogin);
        });
        teacherList.addActionListener(e -> {
            if(! teacher.isDC ) new TeacherListPanel(user,lastLogin);
            else new DCTeacherPanel(user,lastLogin);
        });

    }
}