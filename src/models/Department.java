package models;


import java.util.ArrayList;

public class Department {
    public static ArrayList<Department> list=new ArrayList<>();
    public String name;
    public Teacher evc,dc;
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
