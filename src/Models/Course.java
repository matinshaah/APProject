package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class Course {
    public static ArrayList<Course> courseList = new ArrayList<>();
    public AbsCourse absCourse;
    public int total,id;
    public HashSet<Student> students;
    public HashSet<Teacher> teachers;
    public int term;
    public String classTime,examTime;
    public Course(int term,int total,AbsCourse absCourse,HashSet<Teacher> teachers,HashSet<Student> students,String classTime){
        courseList.add(this);
        this.classTime=classTime;
        this.id=Integer.parseInt(term+""+absCourse.id);
        this.term= term;
        this.total= total;
        this.absCourse=absCourse;
        absCourse.courses.add(this);
        this.students=students;
        this.teachers= teachers;
        if(absCourse.department!=null) absCourse.department.courses.add(this);
    }
    public Course(int term,int total,AbsCourse absCourse,HashSet<Teacher> teachers,HashSet<Student> students,String classTime,String examTime){
        this(term, total, absCourse, teachers, students, classTime);
        this.examTime=examTime;
    }

    public static ArrayList<Course> sortedList(ArrayList<Course> list){
        list.sort(new SortCompare());
        return list;
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
