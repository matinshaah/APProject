package GUI;

import Models.Student;
import Models.User;

import java.time.LocalDateTime;

public class StudentMenuBar extends UserMenuBar{
    Student student;
    MyMenuItem educationalStatus,withdraw,recommendation,minor,eduCertificate,dormitory,thesisDefense;
    StudentMenuBar(User user, LocalDateTime lastLogin) {
        super(user,lastLogin);


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
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        courseList.addActionListener(e -> new CoursesListPanel(user,lastLogin));
    }

    protected void setMenuBar(){
        super.setMenuBar();
        reportCard.add(educationalStatus);
        setApplications();
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

}
