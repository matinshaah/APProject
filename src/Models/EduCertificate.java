package Models;

public class EduCertificate extends Request{
    public EduCertificate(Student student) {
        super(student);
        this.name= "EduCertificate";
    }
    public String text(){
        return "This is to certify that:  \n" +
                "the student \""+student.name+"\" with student number\"" +student.id+
                "\"\n is studying in \""+student.department.name+"\" at Sharif University of Technology. " +
                "\n The present certificate is valid until 2022/7/25";
    }
}
