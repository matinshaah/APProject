package models;

import java.util.ArrayList;
import java.util.HashSet;

public class AbsCourse {
    private static int idCounter=0;
    public static HashSet<AbsCourse> list = new HashSet<>();
    public ArrayList<Course> courses = new ArrayList<>();
    public final int id;
    public String name;
    public int credit;
    public Grade grade;
    public Department department;
    public  HashSet<AbsCourse> preCourses;
    public AbsCourse(String name, int credit, Department department, HashSet<AbsCourse> preCourses, Grade grade){
        list.add(this);
        this.name=name;
        this.credit=credit;
        this.preCourses=preCourses;
        this.department=department;
        this.grade=grade;
        idCounter++;
        for (AbsCourse c :
                list) {
            if (c.id == idCounter) idCounter++;
        }
        this.id=idCounter+10000;
    }

    public static AbsCourse getAbsCourseByName(String name){
        for (AbsCourse absCourse:
             list) {
            if(absCourse.name.equals(name)) return absCourse;
        }
        return null;
    }
    public static AbsCourse getAbsCourseById(String id){
        for (AbsCourse absCourse:
                list) {
            if(id.equals(absCourse.id+"")) return absCourse;
        }
        return null;
    }

}
