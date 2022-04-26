import Models.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

public class Test {
    public Test() throws NoSuchAlgorithmException {
        Department math = new Department("Math");

        AbsCourse AP = new AbsCourse("AP",4,math,new HashSet<>(), Grade.BS);
        Course AP14002 = new Course(14002,100,AP,new HashSet<>(),new HashSet<>(),"Tuesday,Sunday 15:00-17:00","2022/5/20 09:00");
        Course AP992 = new Course(992,100,AP,new HashSet<>(),new HashSet<>(),"Tuesday,Sunday 15:00-17:00","2022/5/20 09:01");
        Course AP982 = new Course(982,100,AP,new HashSet<>(),new HashSet<>(),"Tuesday,Sunday 15:00-17:00","2022/5/20 08:59");


        Teacher evc = new Teacher("Khazaie","1","0410978",math,new ArrayList<>(), Teacher.Degree.ASSISTANT_PROFESSOR,true,false);
        Teacher boss = new Teacher("Jalili","1","0414336",math,new ArrayList<>(), Teacher.Degree.ASSOCIATE_PROFESSOR,false,true);
        evc.email="Khazaie@sharif.edu";
        boss.email="Jalili@sharif.edu";
        evc.courses.add(AP14002);
        boss.courses.add(AP14002);
        //username:10001

        Student me =new Student("Matin","1","0410858961",math,new ArrayList<>(),99, Grade.BS,evc, Student.Status.STUDYING);
        me.setEmail("shahnaziamirmatin@gmail.com"); //username:99000001
        me.courses.add(AP14002);
        AP14002.teachers.add(evc);

    }
}
