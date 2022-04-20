package Models;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class DepartmentChair extends Teacher{
    public DepartmentChair(String name, String password, String nationalCode, Department department, HashSet<Course> courses, int roomNumber) throws NoSuchAlgorithmException {
        super(name, password, nationalCode, department, courses, roomNumber);
        if(department!=null) department.departmentChair=this;
    }
}
