package Models;

import java.util.HashSet;

public class Course {
    AbsCourse absCourse;
    int total;
    HashSet<Student> students;
    HashSet<Teacher> teachers;
    int term;
    public Course(int term,int total,AbsCourse absCourse,HashSet<Teacher> teachers,HashSet<Student> students){
        this.term= term;
        this.total= total;
        this.absCourse=absCourse;
        this.students=students;
        this.teachers= teachers;
    }
}
