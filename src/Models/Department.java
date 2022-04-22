package Models;

import java.util.HashSet;

public class Department {
    public static HashSet<Department> list=new HashSet<>();
    public String name;
    public HashSet<AbsCourse> absCourses = new HashSet<>();
    public HashSet<Teacher> teachers = new HashSet<>();
    HashSet<Student> students = new HashSet<>();
    DepartmentChair departmentChair;
    EducationViceChar educationViceChar;
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
