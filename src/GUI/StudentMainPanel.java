package GUI;

import Models.Student;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class StudentMainPanel extends UserMainPanel {
    Student student;
    MyMenuItem educationalStatus,withdraw,recommendation,minor,eduCertificate,dormitory,thesisDefense;
    MyJLabel supervisor,eduStatus,registrationLicense,registrationTime;

    public StudentMainPanel(User student, LocalDateTime loginTime) {
        super(student,loginTime);


    }
    @Override
    protected void initCom(){
        this.student=(Student) user;
        super.initCom();
        educationalStatus = new MyMenuItem("Educational Status");
        withdraw = new MyMenuItem("WithDraw from Education");
        eduCertificate = new MyMenuItem("Educational Certificate");
        recommendation = new MyMenuItem("Recommendation");
        minor = new MyMenuItem("Minor");
        dormitory = new MyMenuItem("Dormitory");
        thesisDefense = new MyMenuItem("Thesis Defense");
        setMainChart();

    }
    @Override
    protected void align(){
        super.align();
    }
    @Override
    protected void setMenuBar(){
        super.setMenuBar();
        reportCard.add(educationalStatus);
        setApplications();
    }
    @Override
    protected void setListeners(){
        super.setListeners();

    }
    private void setApplications(){
        applications.add(eduCertificate);
        if(student==null) System.out.println("khar");
        if(student.grade== Student.StudentGrade.AS){ //karshenasi
            applications.add(recommendation);
            applications.add(minor);
        }else if(student.grade == Student.StudentGrade.MS ){  //asrshad
            applications.add(recommendation);
            applications.add(dormitory);
        }else { //doctora
            applications.add(thesisDefense);
        }
        applications.add(withdraw);
    }

    protected void setMainChart(){
        supervisor = new MyJLabel("Supervisor:\t\t"+student.supervisor.name);
        eduStatus = new MyJLabel("Educational Status:\t \t"+String.valueOf(student.status));
        registrationLicense = new MyJLabel("Registration Permission:\t\t"+"Allowed to register");
        registrationTime = new MyJLabel("Registration Time:\t\t"+"2022/5/30 9:00 AM");
        this.add(supervisor);
        this.add(eduStatus);
        this.add(registrationLicense);
        this.add(registrationTime);
        supervisor.setBounds(200,400,900,50);
        eduStatus.setBounds(200,450,900,50);
        registrationLicense.setBounds(200,500,500,50);
        registrationTime.setBounds(200,550,900,50);
    }

    static class MyJLabel extends JLabel{
        static Font font = new Font("",Font.PLAIN,30);
        MyJLabel(String text){
            super(text);
            this.setOpaque(true);
            this.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
            this.setBackground(Color.green);
            this.setFont(font);

        }
    }

}
