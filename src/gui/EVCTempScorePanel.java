package gui;

import models.User;
import resources.MasterLogger;

import javax.swing.*;
import java.time.LocalDateTime;

public class EVCTempScorePanel extends UserMainPanel{
    JButton myCourse,allCourse;
    public EVCTempScorePanel(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        myCourse = new JButton("My courses");
        allCourse = new JButton("all courses");
    }

    @Override
    protected void align() {
        super.align();
        this.add(allCourse);
        this.add(myCourse);
        allCourse.setBounds(150,150,100,40);
        myCourse.setBounds(150,200,100,40);
    }
    private void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        myCourse.addActionListener(e->new TeacherTempScorePanel(user,lastLogin));
        allCourse.addActionListener(e-> new DepTempScorePanel(user,lastLogin));
    }
}
