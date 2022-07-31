import models.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

public class Test {
    public Test() throws NoSuchAlgorithmException {
        Department math = new Department("Math");
        Department physic = new Department("Physic");

        AbsCourse AP = new AbsCourse("AP",4,math,new HashSet<>(), Grade.BS);
        Course AP14002 = new Course(14002,100,AP,new HashSet<>(),new ArrayList<>(),"Tuesday,Sunday 15:00-17:00","2022/5/20 09:00");
        Course AP13992 = new Course(13992,100,AP,new HashSet<>(),new ArrayList<>(),"Tuesday,Sunday 15:00-17:00","2022/5/20 09:01");
        Course AP13982 = new Course(13982,100,AP,new HashSet<>(),new ArrayList<>(),"Tuesday,Sunday 15:00-17:00","2022/5/20 08:59");


        Teacher evc = new Teacher("Khazaie","1","0410978",math,new ArrayList<>(), Teacher.Degree.ASSISTANT_PROFESSOR,true,false,"","","");
        Teacher boss = new Teacher("Jalili","1","0414336",math,new ArrayList<>(), Teacher.Degree.ASSOCIATE_PROFESSOR,false,true,"","","");
        evc.email="Khazaie@sharif.edu";
        boss.email="Jalili@sharif.edu";
        Teacher.addCourse(evc,AP14002);
        Teacher.addCourse(boss,AP14002);
        //username:10001

        Student me =new Student("Matin","1","0410858961",math,new ArrayList<>(),99, Grade.BS,evc, Student.Status.STUDYING);
        me.setEmail("shahnaziamirmatin@gmail.com"); //username:99000001
        Student.addCourse(me,AP14002);
//        me.scores.get(AP14002.id+"").setScore("19.0");
        AP14002.teachers.add(evc);

        Teacher Pevc = new Teacher("PKhazaie","1","P0410978",physic,new ArrayList<>(), Teacher.Degree.PROFESSOR,true,false,"","","");
        Teacher Pboss = new Teacher("PJalili","1","P0414336",physic,new ArrayList<>(), Teacher.Degree.ASSOCIATE_PROFESSOR,false,true,"","","");

        Student arshad = new Student("slam","1","1",physic,new ArrayList<>(),98,Grade.MS,Pevc, Student.Status.STUDYING);
        Student.addCourse(arshad,AP13992);
        Student.addCourse(arshad,AP14002);
        Teacher.addCourse(Pevc,AP13992);
        arshad.scores.get(AP13992.id+"").setScore("18");

        Student doctor = new Student("doctor","1","2",physic,new ArrayList<>(),96,Grade.PHD,Pevc, Student.Status.GRADUATED);


    }
}
