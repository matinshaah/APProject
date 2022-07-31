package models;

import java.util.Random;

public class Dormitory extends Request{
    public Dormitory(Student student) {
        super(student);
        this.name= "Dormitory";
        result = new Random().nextInt(2)+1;
    }
}
