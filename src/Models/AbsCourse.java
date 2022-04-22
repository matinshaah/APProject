package Models;

import java.util.HashSet;

public class AbsCourse {
    private static int idCounter=0;
    public static HashSet<AbsCourse> list = new HashSet<>();
    public final int id;
    public String name;
    public int credit;
    public Department department;
    public  HashSet<AbsCourse> preCourses;
    public AbsCourse(String name,int credit,Department department,HashSet<AbsCourse> preCourses){
        list.add(this);
        this.name=name;
        this.credit=credit;
        this.preCourses=preCourses;
        this.department=department;
        if(department!= null) department.absCourses.add(this);
        idCounter++;
        this.id=idCounter+10000;
    }

    public static AbsCourse getAbsCourseByName(String name){
        for (AbsCourse absCourse:
             list) {
            if(absCourse.name.equals(name)) return absCourse;
        }
        return null;
    }

}
