package models;

public class Recommendation extends Request{
    public Teacher teacher;
    public Recommendation(Student student,Teacher teacher) {
        super(student);
        this.name= "Recommendation";
        this.teacher=teacher;
    }
    public String text(){
        return "I testify that student \""+student.name
                +"\" with student number \""+student.id+
                "\" passed lessons \""+courses()[0]+
                "\" with a score of \""+courses()[1]+
                "\" and worked in lessons \""+taCourse()
                +"\" as a teaching assistant.";
    }

    private String[] courses(){
        String[] str = {"",""};
        if(teacher.courses==null ) return str;
        for (Course c :
                teacher.courses) {
            if (student.courses.contains(c)) {
                double d = Double.parseDouble(student.scores.get(c.id+"").score);
                if(d>10){
                    str[0]+=c.absCourse.name+",";
                    str[1]+=d+",";
                }
            }
        }
        if(str[0].length()!=0) str[0]=str[0].substring(0,str[0].length()-1);
        else str[0]="-";
        if(str[1].length()!=0) str[1]=str[1].substring(0,str[1].length()-1);
        else str[1]="-";
        return str;
    }
    private String taCourse(){
        StringBuilder str = new StringBuilder("-");
        if(teacher.courses==null ) return str.toString();
        for (Course c :
                teacher.courses) {
            if (!student.courses.contains(c)) {
                if(passed(c)){
                    str.append(c.absCourse.name).append(",");
                }
            }
        }
        if(str.length()!=0) str = new StringBuilder(str.substring(0, str.length() - 1));
        else str=new StringBuilder("-");
        return str.toString();
    }

    private boolean passed(Course course){
        for (Course c :
                student.courses) {
            if (c.absCourse == course.absCourse)
                if(Double.parseDouble(student.scores.get(c.id+"").score)>12) return true;
        }
        return false;
    }
}
