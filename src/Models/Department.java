package Models;

import java.util.HashSet;

public class Department {
    HashSet<Teacher> teachers = new HashSet<>();
    HashSet<Student> students = new HashSet<>();
    DepartmentChair departmentChair;
    EducationViceChar educationViceChar;


}
