package Models;

import java.util.ArrayList;
import java.util.HashSet;

public class Department {
    public static HashSet<Department> list=new HashSet<>();
    public String name;
    public Teacher evc,dc;
    public HashSet<AbsCourse> absCourses = new HashSet<>();
    public ArrayList<Course> courses = new ArrayList<>();
    public ArrayList<Teacher> teachers = new ArrayList<>();
    HashSet<Student> students = new HashSet<>();
    public Department(String name){
        list.add(this);
        this.name=name;
    }

    public static Department getDepartmentByName(String name){
        for (Department d :
                list) {
            if(d.name.equals(name)) return d;
        }
        return null;
    }
}
