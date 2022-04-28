package Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ThesisDefense extends Request{
    LocalDate date;
    public ThesisDefense(Student student) {
        super(student);
        this.name= "ThesisDefense";
        date = LocalDate.parse("2022/05/30", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        int random = new Random().nextInt(100);
        date=date.plusDays(random);
    }
    public String text(){
        return "You can defense your thesis on "+date;
    }
}
