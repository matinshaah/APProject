package Models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Request {
    public static ArrayList<Request> requests = new ArrayList<>();
    public LocalDateTime time;
    public String name;
    public Student student;
    public int result;//0: registered,1: accepted, 2: rejected
    static int idCounter =1000;
    public int id;
    public Request(Student student) {
        this.time=LocalDateTime.now();
        result = 0;
        idCounter++;
        id=idCounter;
        requests.add(this);
        this.student = student;
    }

    public static Request findReqByID(String id){
        for (Request r :
                requests) {
            if (id.equals(r.id + ""))
                return r;
        }
        return null;
    }
}
