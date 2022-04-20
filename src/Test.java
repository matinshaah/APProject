import Models.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

public class Test {
    public Test() throws NoSuchAlgorithmException {
        Department math = new Department();

        AbsCourse AP = new AbsCourse("AP",4,math,new HashSet<>());
        Course AP14002 = new Course(14002,100,AP,new HashSet<>(),new HashSet<>());

        EducationViceChar evc = new EducationViceChar("Khazaie","1","",math,new HashSet<>(),25);
        evc.courses.add(AP14002);
        //username:10001

        Student me =new Student("Matin","1","0410858961",math,new HashSet<>(),99, Student.StudentGrade.AS,evc, Student.Status.STUDYING);
        me.setEmail("shahnaziamirmatin@gmail.com"); //username:99000001
        me.courses.add(AP14002);

    }
}
