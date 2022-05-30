package Models;

import resources.SavingData;

import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class Student extends User{
    public Teacher supervisor;
    public final int enteringYear;
    private static int idCounter=0;
    public HashMap<String,Score > scores=new HashMap<>(); //key:courseID,value:score
    public Status status;
    public Grade grade;

    private Student(String name, String password, String nationalCode, Department department , ArrayList<Course> courses, int enteringYear, Grade grade) throws NoSuchAlgorithmException {
        super(name,password,nationalCode,department,courses);
        idCounter++;
        this.enteringYear=enteringYear;
        this.grade=grade;
        this.id = idCounter + enteringYear*1000000;
        if(courses!=null) {
            for (Course c :
                    courses) {
                scores.put(c.id+"",new Score());
                c.students.add(this);
            }
        }
    }
    public Student(String name, String password, String nationalCode, Department department, ArrayList<Course> courses, int enteringYear, Grade grade, Teacher supervisor, Status status) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses, enteringYear, grade);
        this.supervisor=supervisor;
        this.status=status;
    }
    public Student(String name, String password, String nationalCode, Department department , ArrayList<Course> courses,int enteringYear,Grade grade,Teacher supervisor,Status status,String imagePath,String email,String phoneNumber) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses, enteringYear, grade, supervisor, status);
//        String str = "newStudent/\t{name:"+name+"\t{password:"+password+"\t{nationalCode:"+nationalCode+
//                "\t{department:"+department.name+"\t{enteringYear:"+enteringYear+
//                "\t{grade:"+grade+"\t{supervisor:"+supervisor.id+"\t{status:"+status.name+"\t{imagePath:"+imagePath+
//                "\t{email:"+email+"\t{phoneNumber:"+phoneNumber;
//        SavingData.addToFile(str);
        this.image=imagePath;
        this.email=email;
        this.phoneNumber=phoneNumber;
    }
    public static void addCourse(Student student,Course c){
        if(student.courses!=null&&! student.courses.contains(c)){
            student.courses.add(c);
            student.scores.put(c.id+"",new Score());
            c.students.add(student);
//            String str ="studentAddCourse/"+"\t}id:"+student.id+"\t}courseID:"+c.id;
//            SavingData.addToFile(str);
        }
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
    public int getPassedCreditNumber(){
        int number=0;
        for (Course c :
                courses) {
            if(c.status!= Course.ScoreStatus.NotGiven&&scores.get(c.id+"").passed()) number+=c.absCourse.credit;
        }
        return number;
    }
    public double getTotalAverage(){
        double sum=0d;
        int credits=0;
        for (Course c :
                courses) {
            if(c.status== Course.ScoreStatus.Final){
                sum+=(Double.parseDouble(scores.get(c.id+"").score)*c.absCourse.credit);
                credits+=c.absCourse.credit;
            }
        }
        return credits!=0?sum/credits:0;
    }



    public static class Score{
        private static final DecimalFormat df = new DecimalFormat("0.00");
        private static final DecimalFormat df2 = new DecimalFormat("0");
        public String score="";
        public String objectionText="",objectionAnswer="";
        public boolean isFinal;
        public Score(){

        }
        public void setScore(String str){//we know 0<=d<=20
            Double d = Double.parseDouble(str);
            d*=4;
            String string = df2.format(d);
            d = Double.parseDouble(string);
            d/=4d;
            score=df.format(d);
        }
        public boolean passed(){
            return Double.parseDouble(score)>=12;
        }
    }
    public static Student getStudentById(String id){
        for (User u :
                User.userList) {
            if(id.equals(u.id+"")&&u instanceof Student)
                return (Student) u;
        }
        return null;
    }
}
