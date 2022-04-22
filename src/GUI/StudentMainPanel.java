package GUI;

import Models.Student;
import Models.User;

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
    @Override
    protected void align(){
        super.align();
    }

    protected void setMainChart(){
        supervisor = new MyJLabel(     "Supervisor:                                                          "+student.supervisor.name);
        eduStatus = new MyJLabel(        "Educational Status:                                            "+student.status);
        registrationLicense = new MyJLabel("Registration Permission:                                   "+"Allowed to register");
        registrationTime = new MyJLabel( "Registration Time:                                            "+"2022/5/30 9:00 AM");
        this.add(supervisor);
        this.add(eduStatus);
        this.add(registrationLicense);
        this.add(registrationTime);
        supervisor.setBounds(200,300,900,50);
        eduStatus.setBounds(200,350,900,50);
        registrationLicense.setBounds(200,400,900,50);
        registrationTime.setBounds(200,450,900,50);
    }

    static class MyJLabel extends JLabel{
        static Font font = new Font("",Font.PLAIN,30);
        MyJLabel(String text){
            super(text);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createDashedBorder(Color.red));
            this.setBackground(Color.green);
            this.setFont(font);

        }
    }

}
