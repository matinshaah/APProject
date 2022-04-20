package Models;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class Student extends User{
    public Teacher supervisor;
    final int enteringYear;
    private static int idCounter=0;

    public Student(String name, String password, String nationalCode, Department department , HashSet<Course> courses,int enteringYear,StudentGrade grade) throws NoSuchAlgorithmException {
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
    public Student(String name, String password, String nationalCode, Department department , HashSet<Course> courses,int enteringYear,StudentGrade grade,Teacher supervisor,Status status) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses, enteringYear, grade);
        this.supervisor=supervisor;
        this.status=status;
    }

    public enum StudentGrade{
        AS,
        MS,
        PHD
    }

    public StudentGrade grade;
    public enum Status{
        STUDYING,
        GRADUATED,
        WITHDRAW_FROM_EDUCATION

    }
    public Status status;
    int totalAverage;


}
