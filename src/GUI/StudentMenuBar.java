package GUI;

import Models.Grade;
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
        teacherList.addActionListener(e -> new TeacherListPanel(user,lastLogin));
        minor.addActionListener(e -> new MinorPanel(user,lastLogin));
        recommendation.addActionListener(e-> new RecommendPanel(user,lastLogin));
        eduCertificate.addActionListener(e-> new EduCertificatePanel(user,lastLogin));
        thesisDefense.addActionListener(e-> new ThesisDefensePanel(user,lastLogin));
        dormitory.addActionListener(e->new DormitoryPanel(student,lastLogin));
        withdraw.addActionListener(e-> new WithdrawPanel(student,lastLogin));
        tempScores.addActionListener(e-> new StudentTempScoresPanel(user,lastLogin));
        educationalStatus.addActionListener(e->new StudentEduStatusPanel(user,lastLogin));

    }

    protected void setMenuBar(){
        super.setMenuBar();
        reportCard.add(educationalStatus);
        setRequests();
    }

    private void setRequests(){
        requests.add(eduCertificate);
        if(student.grade== Grade.BS){ //karshenasi
            requests.add(recommendation);
            requests.add(minor);
        }else if(student.grade == Grade.MS ){  //arshad
            requests.add(recommendation);
            requests.add(dormitory);
        }else { //doctora
            requests.add(thesisDefense);
        }
        requests.add(withdraw);
    }

}
