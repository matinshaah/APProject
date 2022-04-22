package Models;

import java.util.ArrayList;
import java.util.HashSet;

public class Course {
    public static ArrayList<Course> courseList = new ArrayList<>();
    public AbsCourse absCourse;
    public int total;
    HashSet<Student> students;
    public HashSet<Teacher> teachers;
    public int term;
    public Course(int term,int total,AbsCourse absCourse,HashSet<Teacher> teachers,HashSet<Student> students){
        courseList.add(this);
        this.term= term;
        this.total= total;
        this.absCourse=absCourse;
        this.students=students;
        this.teachers= teachers;
    }
}
