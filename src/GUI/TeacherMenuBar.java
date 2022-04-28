package GUI;

import Models.Teacher;
import Models.User;

import javax.swing.*;
import java.time.LocalDateTime;

public class TeacherMenuBar extends UserMenuBar{
    Teacher teacher;
    JMenuItem allReq;
    TeacherMenuBar(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);
    }

    @Override
    protected void initCom() {
        this.teacher = (Teacher) user;
        super.initCom();
        allReq = new MyMenuItem("all requests");
    }

    @Override
    protected void setMenuBar(){
        super.setMenuBar();
        requests.add(allReq);

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
        allReq.addActionListener(e->new TeacherRequestPanel(user,lastLogin));
        tempScores.addActionListener(e->{
            if(!teacher.isEVC)
                new TeacherTempScorePanel(user,lastLogin);
            else new EVCTempScorePanel(user,lastLogin);
        });
    }
}
