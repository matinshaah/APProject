package Models;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class EducationViceChar extends Teacher{
    public EducationViceChar(String name, String password, String nationalCode, Department department, HashSet<Course> courses, int roomNumber) throws NoSuchAlgorithmException {
        super(name, password, nationalCode, department,courses, roomNumber);
        if(department!=null) department.educationViceChar=this;
    }
}
