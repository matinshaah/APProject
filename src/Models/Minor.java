package Models;

public class Minor extends Request {
    public Department secondDep;
    public int firstAccepted=0,secondAccepted=0;  // 0)not answered 1)accepted 2)rejected
    public Minor(Student student,Department secondDep) {
        super(student);
        this.name= "Minor";
        this.secondDep=secondDep;
        if(student.getTotalAverage()<17d) result=2;
    }

    public void checkResult(){
        if(firstAccepted==1&&secondAccepted==1) result=1;
        else if(firstAccepted==2||secondAccepted==2) result=2;
    }
}
