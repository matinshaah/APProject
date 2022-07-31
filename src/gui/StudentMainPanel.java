package gui;

import models.Student;
import models.User;
import resources.MasterLogger;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class StudentMainPanel extends UserMainPanel {
    Student student;
    MyJLabel supervisor,eduStatus,registrationLicense,registrationTime;

    public StudentMainPanel(User student, LocalDateTime loginTime) {
        super(student,loginTime);
    }
    @Override
    protected void initCom(){
        this.student=(Student) user;
        super.initCom();
        setMainChart();

    }
    protected void setMainChart(){
        MasterLogger.getInstance().log("main chart is set",false,this.getClass());
        String license=student.status== Student.Status.STUDYING?"Allowed to register":"        -";
        String time=student.status== Student.Status.STUDYING?"2022/5/30 9:00 AM":"          -";
        String supervisorName=student.supervisor!=null?student.supervisor.name:"   -";
        supervisor = new MyJLabel(     "Supervisor:                                                          "+supervisorName);
        eduStatus = new MyJLabel(        "Educational Status:                                            "+student.status.name);
        registrationLicense = new MyJLabel("Registration Permission:                                   "+license);
        registrationTime = new MyJLabel( "Registration Time:                                            "+time);

        supervisor.setBackground(student.supervisor==null?Color.red:Color.green);
        eduStatus.setBackground(student.status.color);
        registrationLicense.setBackground(student.status.color);
        registrationTime.setBackground(student.status.color);
        this.add(supervisor);
        this.add(eduStatus);
        this.add(registrationLicense);
        this.add(registrationTime);
        supervisor.setBounds(200,300,1000,50);
        eduStatus.setBounds(200,350,1000,50);
        registrationLicense.setBounds(200,400,1000,50);
        registrationTime.setBounds(200,450,1000,50);
    }

    static class MyJLabel extends JLabel{
        static Font font = new Font("",Font.PLAIN,30);
        MyJLabel(String text){
            super(text);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createDashedBorder(Color.black));
            this.setFont(font);
        }
    }

}
