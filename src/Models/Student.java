package Models;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

public class Student extends User{
    public Teacher supervisor;
    public final int enteringYear;
    private static int idCounter=0;
    public Status status;
    public Grade grade;
    int totalAverage;

    public Student(String name, String password, String nationalCode, Department department , ArrayList<Course> courses, int enteringYear, Grade grade) throws NoSuchAlgorithmException {
        super(name,password,nationalCode,department,courses);
        idCounter++;
        this.enteringYear=enteringYear;
        this.grade=grade;
        this.id = idCounter + enteringYear*1000000;
        if(courses!=null) {
            for (Course c :
                    courses) {
                c.students.add(this);
            }
        }
        if(department!= null) this.department.students.add(this);
    }
    public Student(String name, String password, String nationalCode, Department department , ArrayList<Course> courses,int enteringYear,Grade grade,Teacher supervisor,Status status) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses, enteringYear, grade);
        this.supervisor=supervisor;
        this.status=status;
    }
    public Student(String name, String password, String nationalCode, Department department , ArrayList<Course> courses,int enteringYear,Grade grade,Teacher supervisor,Status status,String imagePath,String email,String phoneNumber) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses, enteringYear, grade, supervisor, status);
        this.image=imagePath;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }

    public enum Status{
        STUDYING("studying",Color.green),
        GRADUATED("graduated",Color.ORANGE),
        WITHDRAW_FROM_EDUCATION("withdraw from education",Color.red);

        public final String name;
        public final Color color;
        Status(String name, Color color){
            this.color=color;
            this.name=name;
        }
        public static Status getStatusByName(String name){
            for (Status s:
                 Status.values()) {
                if(s.name.equalsIgnoreCase(name)) return s;
            }
            return null;
        }
    }


}
