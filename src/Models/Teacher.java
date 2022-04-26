package Models;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Teacher extends User{
    public static ArrayList<Teacher> list= new ArrayList<>();
    private static int idCounter=0;
    public Degree degree;
    public int roomNumber;
    public boolean isEVC,isDC;
    public Teacher(String name, String password, String nationalCode, Department department, ArrayList<Course> courses) throws NoSuchAlgorithmException {
        super(name, password, nationalCode, department,courses);
        list.add(this);
        this.roomNumber=new Random().nextInt(100);
        idCounter++;
        this.id=idCounter+10000;
        this.degree=Degree.ASSISTANT_PROFESSOR;

        if(courses!= null) {
            for (Course c :
                    courses) {
                c.teachers.add(this);
            }
        }else this.courses = new ArrayList<>();
        if(department!=null) {
            this.department.teachers.add(this);
            if(isDC) department.dc=this;
            if(isEVC) department.evc=this;
        }
    }

    public Teacher(String name, String password, String nationalCode, Department department, ArrayList<Course> courses,Degree degree) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses);
        this.degree=degree;
    }
    public Teacher(String name, String password, String nationalCode, Department department, ArrayList<Course> courses,Degree degree,boolean isEVC,boolean isDC) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses, degree);
        this.isDC=isDC;
        this.isEVC=isEVC;
        if(isDC) department.dc=this;
        if(isEVC) department.evc=this;
    }

    public Teacher(String name, String password, String nationalCode, Department department, Degree degree, String email, String phoneNumber, String imagePath) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, null,degree);
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.image=imagePath;
    }

    public enum Degree{
        ASSISTANT_PROFESSOR("assistant professor"),
        PROFESSOR("professor"),
        ASSOCIATE_PROFESSOR("associate professor");

        final public String name;
        Degree (String name){
            this.name=name;
        }
        public static Degree getDegreeByName(String name){
            for (Degree degree :
                    Degree.values()) {
                if (degree.name.equals(name))
                    return degree;
            }
            return null;
        }
    }
    public static Teacher getTeacherByName(String name){
        for (Teacher t :
                list) {
            if(t.name.equals(name)) return t;
        }
        return null;
    }

}
