package Controller;

import Models.*;

import java.util.HashSet;

public class Controller {
    public static User LoginButton(String name,String password){
        if(name.length()==0||password.length()==0) return null;
        for (int i = 0; i < name.length(); i++) {
            if(name.charAt(i)-'0'<0||name.charAt(i)-'0'>9)
                return null;
        }
        for (User user :
                User.userList) {
            if(user.id==Integer.parseInt(name)&&user.password.equals(password))
                return user;
        }
        return null;
    }
    public static Course findCourseByID(String id){
        for (Course c :
                Course.courseList) {
            if (id.equals(c.absCourse.id + ""))
                return c;
        }
        return null;
    }
    public static String editCourse(Course course,String name,String credit,String total,
                                    String teachers,String department,String preCourse,String term){
        if(name.equals("")) return "name";
        if(credit.equals("")) return "credit";
        if(total.equals("")) return "total";
        if(teachers.equals("")) return "teachers";
//        if(department.equals("")) return "department";
        if(term.equals("")) return "term";
        if(! isNumeric(credit)) return "credit";
        if(! isNumeric(total)) return "total";
        if(! isNumeric(term)) return "term";
        String[] teacher = teachers.split(",");
        for (String t :
                teacher) {
            boolean b=false;
            for (Teacher teacher1:
                 Department.getDepartmentByName(department).teachers) {
                if(t.equals(teacher1.name)){
                    b=true;
                    break;
                }
            }
            if(!b) return "teachers";
        }
        String[] pre = preCourse.split(",");
        if(! preCourse.equals("")) {
            for (String p :
                    pre) {
                boolean b = false;
                for (AbsCourse c :
                        Department.getDepartmentByName(department).absCourses) {
                    if (c.name.equals(p)) {
                        b = true;
                        break;
                    }
                }
                if (!b) return "";
            }
        }


        HashSet<AbsCourse> absCourses= new HashSet<>();
        for (String p :
                pre) {
            AbsCourse absCourse =AbsCourse.getAbsCourseByName(p);
            if(absCourse!=null) absCourses.add(absCourse);
        }

        HashSet<Teacher> teacherHashSet = new HashSet<>();
        for (String t :
                teacher) {
            Teacher teacher1=Teacher.getTeacherByName(t);
            if(teacher1!=null) teacherHashSet.add(teacher1);
        }
        if(course==null){
            AbsCourse absCourse =new AbsCourse(name,Integer.parseInt(credit),Department.getDepartmentByName(department),absCourses);
            new Course(Integer.parseInt(term),Integer.parseInt(total),absCourse,teacherHashSet,new HashSet<>());
        }else {
            course.absCourse.name=name;
            course.absCourse.credit=Integer.parseInt(credit);
            course.absCourse.preCourses=absCourses;
            course.term=Integer.parseInt(term);
            course.total=Integer.parseInt(total);
            course.teachers=teacherHashSet;
        }
        return "";
    }

    private static boolean isNumeric(String num){
        for (char c :
                num.toCharArray()) {
            if(c-'0'<0||c-'0'>9) return false;
        }
        return true;
    }
}
