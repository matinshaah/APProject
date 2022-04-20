package GUI;

import Models.Student;
import Models.User;

import javax.swing.*;
import java.time.LocalDateTime;

public class StudentMainPanel extends UserMainPanel {
    Student student;
    MyMenuItem educationalStatus,withdraw,recommendation,minor,eduCertificate,dormitory,thesisDefense;
    JLabel supervisor,eduStatus,registrationLicense,registrationTime;

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
        supervisor = new JLabel("Supervisor: "+student.supervisor.name);
        eduStatus = new JLabel(String.valueOf(student.status));
        registrationLicense = new JLabel("Allowed to register");
        registrationTime = new JLabel("2022/5/30 9:00 AM");
        this.add(supervisor);
        this.add(eduStatus);
        this.add(registrationLicense);
        this.add(registrationTime);
        supervisor.setBounds(100,500,300,20);
        eduStatus.setBounds(100,530,500,20);
        registrationLicense.setBounds(100,560,500,20);
        registrationTime.setBounds(100,590,500,20);
    }

}
