package Models;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class Teacher extends User{
    private int idCounter;
    public Teacher(String name, String password, String nationalCode, Department department, HashSet<Course> courses,int roomNumber) throws NoSuchAlgorithmException {
        super(name, password, nationalCode, department,courses);
        this.roomNumber=roomNumber;
        idCounter++;
        this.id=idCounter+10000;

        if(courses!= null) {
            for (Course c :
                    courses) {
                c.teachers.add(this);
            }
        }
        if(department!=null) this.department.teachers.add(this);
    }

    public Teacher(String name, String password, String nationalCode, Department department, HashSet<Course> courses,int roomNumber,Type type) throws NoSuchAlgorithmException {
        this(name, password, nationalCode, department, courses, roomNumber);
        this.type=type;
    }

    enum Type{
        ASSISTANT_PROFESSOR,
        PROFESSOR,
        ASSOCIATE_PROFESSOR
    }
    Type type;
    int roomNumber;


}
