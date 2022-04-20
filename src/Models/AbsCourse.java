package Models;

import java.util.HashSet;

public class AbsCourse {
    private static int idCounter=0;
    final int id;
    String name;
    int credit;
    Department department;
    final HashSet<AbsCourse> preCourses;
    public AbsCourse(String name,int credit,Department department,HashSet<AbsCourse> preCourses){
        this.name=name;
        this.credit=credit;
        this.preCourses=preCourses;
        this.department=department;

        idCounter++;
        this.id=idCounter+10000;
    }

}
