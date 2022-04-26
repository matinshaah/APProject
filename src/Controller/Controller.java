package Controller;

import Models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    public static int changePassword(User user,String prePass,String newPass,String confirmPass){
        if(prePass.equals("")||newPass.equals("")||confirmPass.equals("")) return 3;
        if(! newPass.equals(confirmPass)) return 1;
        if(prePass.equals(newPass)) return 4;
        try {
            if(!user.password.equals(User.hashPassword(prePass)))
            return 2;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            user.password=User.hashPassword(newPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static Course findCourseByID(String id){
        for (Course c :
                Course.courseList) {
            if (id.equals(c.id + ""))
                return c;
        }
        return null;
    }
    public static Teacher findTeacherByID(String id){
        for (Teacher t :
                Teacher.list) {
            if (id.equals(t.id + ""))
                return t;
        }
        return null;
    }
    public static int setEVC(Teacher teacher){
        if(teacher.isDC) return 1;
        if(teacher.isEVC) return 2;
        teacher.department.evc.isEVC=false;
        teacher.department.evc=teacher;
        teacher.isEVC=true;
        return 0;
    }
    public static String editStudent(Student student,String name,String nationalCode,String department,
                                     String supervisor,String enteringYear,String email,String phoneNumber,
                                     String imagePath,String status,String grade){
        if(name.equals("")) return "name";
        if(nationalCode.equals("")|| isNotNumeric(nationalCode)) return "national code";
        if(enteringYear.equals("")|| isNotNumeric(enteringYear)) return "national code";
        if(email.equals("")) return "email";
        if(phoneNumber.equals("")|| isNotNumeric(phoneNumber)) return "phone number";
        if(Grade.getGradeByName(grade)==null) return "grade";
        if(Student.Status.getStatusByName(status)==null) return "status";

        File file = new File(imagePath);
        if(! file.exists()) return "image path";
        else {
            try {
                if(! Files.probeContentType(file.toPath()).equals("image/png")) return "image path";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean b=false;
        for (Teacher t :
                Teacher.list) {
            if (t.name.equals(supervisor)) {
                b = true;
                break;
            }
        }
        if(!b&&!supervisor.equals("")) return "supervisor";

        if(student==null) {
            for (User user :
                    User.userList) {
                if (user.nationalCode.equals(nationalCode)) return "id";
            }
                try {
                     new Student(name, "1", nationalCode, Department.getDepartmentByName(department), new ArrayList<>(),
                            Integer.parseInt(enteringYear), Grade.getGradeByName(grade),Teacher.getTeacherByName(supervisor),
                             Student.Status.getStatusByName(status),imagePath,email,phoneNumber);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
        }else {
            student.name=name;
            student.nationalCode=nationalCode;
            student.department=Department.getDepartmentByName(department);
            student.grade=Grade.getGradeByName(grade);
            student.supervisor=Teacher.getTeacherByName(supervisor);
            student.status= Student.Status.getStatusByName(status);
            student.phoneNumber=phoneNumber;
            student.image=imagePath;
            student.email=email;
        }
        return "";
    }
    public static String editTeacher(Teacher teacher,String name,String nationalCode,String department
                                     ,String degree,String email,String phoneNumber,String imagePath)  {
        if(name.equals("")) return "name";
        if(nationalCode.equals("")|| isNotNumeric(nationalCode)) return "national code";
        if(degree.equals("")) return "degree";
        if(email.equals("")) return "email";
        if(phoneNumber.equals("")|| isNotNumeric(phoneNumber)) return "phone number";
        if(imagePath.equals("")) return "image path";
        if(Teacher.Degree.getDegreeByName(degree)==null) return "degree";
        File file = new File(imagePath);
        if(! file.exists()) return "image path";
        else {
            try {
                if(! Files.probeContentType(file.toPath()).equals("image/png")) return "image path";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(teacher==null){
            for (User user :
                    User.userList) {
                if(user.nationalCode.equals(nationalCode)) return "id";
            }
            try {
                new Teacher(name,"1",nationalCode,Department.getDepartmentByName(department),
                        Teacher.Degree.getDegreeByName(degree),email,phoneNumber,imagePath);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }else {
            teacher.name=name;
            teacher.nationalCode=nationalCode;
            teacher.degree=Teacher.Degree.getDegreeByName(degree);
            teacher.email=email;
            teacher.phoneNumber=phoneNumber;
            teacher.image=imagePath;
        }
        return "";
    }
    public static String editCourse(Course course,String name,String credit,String total,
                                    String teachers,String department,String preCourse,String term,String classTime,String grade,String examTime){
        if(name.equals("")) return "name";
        if(credit.equals("")) return "credit";
        if(total.equals("")) return "total";
        if(teachers.equals("")) return "teachers";
        if(classTime.equals("")) return "classTime";
        if(! isValidDate(examTime)) return "exam time";
        if(Grade.getGradeByName(grade)==null) return "grade";

//        if(department.equals("")) return "department";
        if(term.equals("")) return "term";
        if(isNotNumeric(credit)) return "credit";
        if(isNotNumeric(total)) return "total";
        if(isNotNumeric(term)) return "term";
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
            AbsCourse absCourse=null;
            for (AbsCourse a :
                    Department.getDepartmentByName(department).absCourses) {
                if(a.name.equals(name)) {
                    absCourse = a;
                    for (Course c :
                            a.courses) {
                        if (term.equals(c.term + "")) return "id";
                    }
                }
            }
            if(absCourse==null) absCourse =new AbsCourse(name,Integer.parseInt(credit),Department.getDepartmentByName(department),absCourses, Grade.getGradeByName(grade));
            course=new Course(Integer.parseInt(term),Integer.parseInt(total),absCourse,teacherHashSet,new HashSet<>(),classTime,examTime);
        }else {
            course.absCourse.name=name;
            course.absCourse.credit=Integer.parseInt(credit);
            course.absCourse.preCourses=absCourses;
            course.absCourse.grade=Grade.getGradeByName(grade);
            course.classTime=classTime;
            course.term=Integer.parseInt(term);
            course.total=Integer.parseInt(total);
            course.teachers=teacherHashSet;
            course.examTime=examTime;
        }
        for (Teacher t :
                Teacher.list) {
            if (!teacherHashSet.contains(t))
                t.courses.remove(course);
        }

        return "";
    }
    public static boolean removeTeacher(Teacher teacher){
        if(teacher.isDC) return false;
        for (Course c :
                teacher.courses) {
            c.teachers.remove(teacher);
        }
        for (User user:
             User.userList) {
            if(user instanceof Student){
                Student student =(Student) user;
                if(student.supervisor==teacher) student.supervisor=null;
            }
        }
        if(teacher.isEVC) teacher.department.evc=null;
        Teacher.list.remove(teacher);
        User.userList.remove(teacher);
        return true;

    }

    public static void removeCourse(Course course){
        for (Teacher t:
             course.teachers) {
            t.courses.remove(course);
        }
        for (Student s :
                course.students) {
            s.courses.remove(course);
        }
        course.absCourse.department.courses.remove(course);
        course.absCourse.courses.remove(course);
        Course.courseList.remove(course);
    }

    private static boolean isNotNumeric(String num){
        for (char c :
                num.toCharArray()) {
            if(c-'0'<0||c-'0'>9) return true;
        }
        return false;
    }
    public static boolean isValidDate(String dateStr) {
        DateTimeFormatter dateFormatter =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        try {
            LocalDateTime.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
