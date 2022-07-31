package models;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class Course {
    public static int CurrentTerm=14002;
    public static ArrayList<Course> courseList = new ArrayList<>();
    public AbsCourse absCourse;
    public int total,id;
    public ArrayList<Student> students;
    public HashSet<Teacher> teachers;
    public int term;
    public String classTime,examTime;
    public ScoreStatus status=ScoreStatus.NotGiven;
    public enum ScoreStatus{
        NotGiven,
        Temporary,
        Final
    }
    public Course(int term,int total,AbsCourse absCourse,HashSet<Teacher> teachers,ArrayList<Student> students,String classTime){
        courseList.add(this);
        this.classTime=classTime;
        this.id=Integer.parseInt(term+""+absCourse.id);
//        System.out.println(this.id);
        this.term= term;
        this.total= total;
        this.absCourse=absCourse;
        absCourse.courses.add(this);
        this.students=students;
        this.teachers= teachers;
    }
    public Course(int term,int total,AbsCourse absCourse,HashSet<Teacher> teachers,ArrayList<Student> students,String classTime,String examTime){
        this(term, total, absCourse, teachers, students, classTime);
        this.examTime=examTime;
    }

    public static ArrayList<Course> getCurrentTermCourses(ArrayList<Course> courses){
        ArrayList<Course> list = new ArrayList<>();
        for (Course c :
                courses) {
            if(c.term== CurrentTerm) list.add(c);
        }
        return list;
    }
    public double getCourseAverage(){
        if(this.status==ScoreStatus.NotGiven) return 0;
        ArrayList<Double> scores= new ArrayList<>();
        for (Student s :
                students) {
            scores.add(Double.parseDouble(s.scores.get(id + "").score));
        }
        return average(scores);
    }
    public double getCoursePassAverage(){
        if(this.status==ScoreStatus.NotGiven) return 0;
        ArrayList<Double> scores= new ArrayList<>();
        for (Student s :
                students) {
            double d =Double.parseDouble(s.scores.get(id + "").score);
            if(d>=12) scores.add(d);
        }
        return average(scores);
    }
    public int getPassedNumber(){
        if(this.status==ScoreStatus.NotGiven) return 0;
        int counter=0;
        for (Student s :
                students) {
            double d =Double.parseDouble(s.scores.get(id + "").score);
            if(d>=12) counter++;
        }
        return counter;
    }
    public int getFailedNumber(){
        if(this.status==ScoreStatus.NotGiven) return 0;
        return students.size()-getPassedNumber();
    }

    public static double average(ArrayList<Double> list){
        double average =0;
        for (Double d :
             list) {
            average+=d;
        }
        if(list.size()==0) average =0;
        else average/=list.size();
        return average;
    }

    public static void sortedList(ArrayList<Course> list){
        list.sort(new SortCompare());
    }



    static class SortCompare implements Comparator<Course> {
        // Method of this class
        @Override
        public int compare(Course a,Course b)
        {
            /* Returns sorted data in ascending order */
            return a.examTime.compareTo(b.examTime);
        }
    }


}
