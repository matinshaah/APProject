package Models;

public class Withdraw extends Request{
    public Withdraw(Student student) {
        super(student);
        this.name= "withdraw";
    }
}
