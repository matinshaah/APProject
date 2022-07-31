package controller;


import models.*;
import resources.MasterLogger;
import resources.SavingData;

import javax.swing.*;
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
            ArrayList<String> str = new ArrayList<>();
            str.add("changePassword");
            str.add("id:"+user.id);
            str.add("newPassword:"+user.password);
            SavingData.addToFile(str);
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
    public static boolean newMinorReq(Student student,String secDep){
        Department secondDep = Department.getDepartmentByName(secDep);
        if(secondDep==null||secondDep==student.department){
            MasterLogger.getInstance().log("new minor req not successful",false,Controller.class);
            return false;
        }
        else {
            new Minor(student,secondDep);
            MasterLogger.getInstance().log("new minor req successful",false,Controller.class);
            ArrayList<String> str = new ArrayList<>();
            str.add("newRequest");
            str.add("type:"+"minor");
            str.add("studentID:"+student.id);
            str.add("department:"+secDep);
            SavingData.addToFile(str);
        }
        return true;
    }
    public static boolean newRecommendReq(Student student,String teacherID){
        Teacher teacher = Teacher.getTeacherByID(teacherID);
        if(teacher==null) {
            MasterLogger.getInstance().log("new recommend req not successful",false,Controller.class);
            return false;
        }
        else {
            new Recommendation(student,teacher);
            MasterLogger.getInstance().log("new recommend req  successful",false,Controller.class);
            ArrayList<String> str = new ArrayList<>();
            str.add("newRequest");
            str.add("type:"+"recommend");
            str.add("studentID:"+student.id);
            str.add("teacherID:"+teacherID);
            SavingData.addToFile(str);
        }
        return true;
    }
    public static boolean newEduCertificateReq(Student student){
        for (Request r :
                Request.requests) {
            if (r instanceof EduCertificate && r.student == student && r.result == 0) {
                MasterLogger.getInstance().log("new edu certificate req not successful",false,Controller.class);
                return false;
            }
        }
        ArrayList<String> str = new ArrayList<>();
        str.add("newRequest");
        str.add("type:"+"eduCertificate");
        str.add("studentID:"+student.id);
        SavingData.addToFile(str);
        MasterLogger.getInstance().log("new edu certificate req  successful",false,Controller.class);
        new EduCertificate(student);
        return true;
    }
    public static boolean newThesisDefenseReq(Student student){
        for (Request r :
                Request.requests) {
            if (r instanceof ThesisDefense && r.student == student && r.result == 0){
                MasterLogger.getInstance().log("new thesis defense req not successful",false,Controller.class);
                return false;
            }
        }
        ArrayList<String> str = new ArrayList<>();
        str.add("newRequest");
        str.add("type:"+"thesis");
        str.add("studentID:"+student.id);
        SavingData.addToFile(str);
        MasterLogger.getInstance().log("new thesis defense req  successful",false,Controller.class);
        new ThesisDefense(student);
        return true;
    }
    public static boolean newDormitoryReq(Student student){
        for (Request r :
                Request.requests) {
            if (r instanceof Dormitory && r.student == student){
                MasterLogger.getInstance().log("new dormitory req not successful",false,Controller.class);
                return false;
            }
        }
        ArrayList<String> str = new ArrayList<>();
        str.add("newRequest");
        str.add("type:"+"dormitory");
        str.add("studentID:"+student.id);
        SavingData.addToFile(str);
        MasterLogger.getInstance().log("new dormitory req  successful",false,Controller.class);
        new Dormitory(student);
        return true;
    }
    public static boolean newWithdrawReq(Student student){
        for (Request r :
                Request.requests) {
            if (r instanceof Withdraw && r.student == student){
                MasterLogger.getInstance().log("new withdraw req not successful",false,Controller.class);
                return false;
            }
        }
        ArrayList<String> str = new ArrayList<>();
        str.add("newRequest");
        str.add("type:"+"withdraw");
        str.add("studentID:"+student.id);
        SavingData.addToFile(str);
        MasterLogger.getInstance().log("new withdraw req successful",false,Controller.class);
        new Withdraw(student);
        return true;
    }
    public static void replyReq(Request request,Teacher teacher,int isAccepted){//1)accepted 2)rejected
        ArrayList<String> str = new ArrayList<>();
        str.add("request");
        str.add("id:"+request.id);
        if(request instanceof Minor minor){
            if(minor.secondDep==teacher.department) {
                minor.secondAccepted=isAccepted;
                str.add("second:2"+isAccepted);
            }
            else {
                minor.firstAccepted=isAccepted;
                str.add("first:1"+isAccepted);
            }
            minor.checkResult();
        }else {
            request.result=isAccepted;
            if(request instanceof Withdraw && isAccepted==1) {
                request.student.status= Student.Status.WITHDRAW_FROM_EDUCATION;
            }
        }
        str.add("result:"+request.result);
        SavingData.addToFile(str);
    }
    public static int recordObjection(Student student,String courseID,String objectionText){//0)empty objection 1)success 2)has already object
        if(objectionText.equals("")) return 0;
        if(student.scores.get(courseID).objectionText.equals("")) {
            student.scores.get(courseID).objectionText=objectionText;
            ArrayList<String> str = new ArrayList<>();
            str.add("setObjection");
            str.add("studentID:"+student.id);
            str.add("courseID:"+courseID);
            str.add("objectionText:"+objectionText);
            SavingData.addToFile(str);
            return 1;
        }else return 2;
    }
    public static int recordObjectionAnswer(Course course, String studentID, JTable table) {//-1)student not found 0)empty body 1)success 2)already answered
        int row = -1;
        for (int i = 0; i < table.getRowCount(); i++) {
            if ((table.getValueAt(i, 1) + "").trim().equals(studentID)) {
                row = i;
                break;
            }
        }
        if (row == -1) return -1;
        Student student = (Student) findUserById(studentID);
        String text = (table.getValueAt(row, 5) + "").trim();
        if (text.equals("")) return 0;
        if (student.scores.get(course.id + "").objectionAnswer.equals("")) {
            student.scores.get(course.id+"").objectionAnswer=text;
            ArrayList<String> str = new ArrayList<>();
            str.add("setObjectionAnswer");
            str.add("studentID:"+student.id);
            str.add("courseID:"+course.id);
            str.add("objectionText:"+text);
            SavingData.addToFile(str);
            return 1;
        }else return 2;
    }
    public static int setTempScores(Course course,JTable table){//0) not all score 1)success 2)already given 3)score out of bound
        if(course.status== Course.ScoreStatus.Final) return 2;
        for (int i = 0; i < table.getRowCount(); i++) {
            String score = (table.getValueAt(i, 3) + "").trim();
            if (score.equals("")) return 0;
            if (!isValidScore(score)) return 3;
        }
        course.status= Course.ScoreStatus.Temporary;
        ArrayList<String> string = new ArrayList<>();
        string.add("makeFinal");
        string.add("courseID:"+course.id);
        string.add("1");//1temp 2:final
        SavingData.addToFile(string);
        for (int i = 0; i < table.getRowCount(); i++) {
            String score = (table.getValueAt(i, 3) + "").trim();
            String studentID =(table.getValueAt(i, 1) + "").trim();
            Student student = (Student) findUserById(studentID);
            student.scores.get(course.id+"").setScore(score);
            ArrayList<String> str = new ArrayList<>();
            str.add("setScore");
            str.add("studentID:"+student.id);
            str.add("courseID:"+course.id);
            str.add("score:"+score);
            SavingData.addToFile(str);
        }
        return 1;
    }
    public static int setFinalScores(Course course,JTable table){//0)not temporary  1)success 2)already given
        if(course.status== Course.ScoreStatus.NotGiven) return 0;
        if(course.status== Course.ScoreStatus.Final) return 2;
        course.status= Course.ScoreStatus.Final;
        ArrayList<String> string = new ArrayList<>();
        string.add("makeFinal");
        string.add("courseID:"+course.id);
        string.add("2");//1temp 2:final
        SavingData.addToFile(string);
        for (int i = 0; i < table.getRowCount(); i++) {
//            String score = (table.getValueAt(i, 3) + "").trim();
            String studentID =(table.getValueAt(i, 1) + "").trim();
            Student student = (Student) findUserById(studentID);
            student.scores.get(course.id+"").isFinal=true;
        }
        return 1;
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
        if(teacher.department.evc!=null) teacher.department.evc.isEVC=false;
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
                    ArrayList<String> str = new ArrayList<>();
                    Teacher teacher =Teacher.getTeacherByName(supervisor);
                    str.add("newStudent");
                    str.add("name:"+name);
                    str.add("password:"+"1");
                    str.add("nationalCode:"+nationalCode);
                    str.add("department:"+department);
                    str.add("enteringYear:"+enteringYear);
                    str.add("grade:"+grade);
                    str.add(teacher==null?"supervisor:":"supervisor:"+teacher.id+"");
                    str.add("status:"+status);
                    str.add("imagePath:"+imagePath);
                    str.add("email:"+email);
                    str.add("phoneNumber:"+phoneNumber);

                    SavingData.addToFile(str);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
        }else {
            student.name=name;
            student.nationalCode=nationalCode;
//            student.department=Department.getDepartmentByName(department);
            student.grade=Grade.getGradeByName(grade);
            Teacher teacher =Teacher.getTeacherByName(supervisor);
            student.supervisor=Teacher.getTeacherByName(supervisor);
            student.status= Student.Status.getStatusByName(status);
            student.phoneNumber=phoneNumber;
            student.image=imagePath;
            student.email=email;
            ArrayList<String> str = new ArrayList<>();
            str.add("student");
            str.add("id:"+student.id);
            str.add("name:"+name);
            str.add("nationalCode:"+nationalCode);
//            str.add("department:"+department);
            str.add("grade:"+grade);
            str.add(teacher==null?"supervisor:":"supervisor:"+teacher.id+"");
            str.add("status:"+status);
            str.add("phoneNumber:"+phoneNumber);
            str.add("imagePath:"+imagePath);
            str.add("email:"+email);

            SavingData.addToFile(str);
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

                ArrayList<String> str = new ArrayList<>();
                str.add("newTeacher");
                str.add("name:"+name);
                str.add("password:"+"1");
                str.add("nationalCode:"+nationalCode);
                str.add("department:"+department);
                str.add("degree:"+degree);
                str.add("email:"+email);
                str.add("phoneNumber:"+phoneNumber);
                str.add("imagePath:"+imagePath);
                SavingData.addToFile(str);

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

            ArrayList<String> str = new ArrayList<>();
            str.add("teacher");
            str.add("id:"+teacher.id);
            str.add("name:"+name);
            str.add("nationalCode:"+nationalCode);
            str.add("degree:"+degree);
            str.add("phoneNumber:"+phoneNumber);
            str.add("imagePath:"+imagePath);
            str.add("email:"+email);
            SavingData.addToFile(str);
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
        ArrayList<Teacher> list = new ArrayList<>();
        for (Teacher t :
                Teacher.list) {
            if (t.department.name.equals(Department.getDepartmentByName(department).name))
                list.add(t);
        }
        for (String t :
                teacher) {
            boolean b=false;
            for (Teacher teacher1:
                 list) {
                if(t.equals(teacher1.name)){
                    b=true;
                    break;
                }
            }
            if(!b) return "teachers";
        }
        String[] pre = preCourse.split(",");
        if(! preCourse.equals("")) {
            ArrayList<AbsCourse> absCourseList = new ArrayList<>();
            for (AbsCourse a :
                    AbsCourse.list) {
                if(a.department==Department.getDepartmentByName(department))
                    absCourseList.add(a);
            }
            for (String p :
                    pre) {
                boolean b = false;
                for (AbsCourse c :
                        absCourseList) {
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
            ArrayList<AbsCourse> absCourseList = new ArrayList<>();
            for (AbsCourse a :
                    AbsCourse.list) {
                if(a.department==Department.getDepartmentByName(department))
                    absCourseList.add(a);
            }
            for (AbsCourse a :
                    absCourseList) {
                if(a.name.equals(name)) {
                    absCourse = a;
                    for (Course c :
                            a.courses) {
                        if (term.equals(c.term + "")) return "id";
                    }
                }
            }
            if(absCourse==null) {
                absCourse =new AbsCourse(name,Integer.parseInt(credit),Department.getDepartmentByName(department),absCourses, Grade.getGradeByName(grade));
                ArrayList<String> str = new ArrayList<>();
                str.add("newAbsCourse");
                str.add("name:"+name);
                str.add("credit:"+credit);
                str.add("grade:"+grade);
                str.add("preCourses:"+preCourse);
                str.add("department:"+department);
                SavingData.addToFile(str);
            }
            course=new Course(Integer.parseInt(term),Integer.parseInt(total),absCourse,teacherHashSet,new ArrayList<>(),classTime,examTime);
            for (Teacher t:
                    teacherHashSet) {
                if(!t.courses.contains(course))t.courses.add(course);
            }

            ArrayList<String> str = new ArrayList<>();
            str.add("newCourse");
            str.add("term:"+term);
            str.add("total:"+total);
            str.add("absCourse:"+absCourse.id);
            str.add("teachers:"+teachers);
            str.add("classTime:"+classTime);
            str.add("examTime:"+examTime);
            SavingData.addToFile(str);
        }else {
            for (Teacher t :
                    Teacher.list) {
                t.courses.remove(course);
            }
            for (Teacher t:
                    teacherHashSet) {
                if(!t.courses.contains(course))t.courses.add(course);
            }

            course.absCourse.name=name;
            course.absCourse.credit=Integer.parseInt(credit);
            course.absCourse.preCourses=absCourses;
            course.absCourse.grade=Grade.getGradeByName(grade);
            course.classTime=classTime;
            course.term=Integer.parseInt(term);
            course.total=Integer.parseInt(total);
            course.teachers=teacherHashSet;
            course.examTime=examTime;

            ArrayList<String> str = new ArrayList<>();
            str.add("course");
            str.add("id:"+course.id);
            str.add("name:"+name);
            str.add("credit:"+credit);
            str.add("preCourses:"+preCourse);
            str.add("grade:"+grade);
            str.add("classTime:"+classTime);
            str.add("term:"+term);
            str.add("total:"+total);
            str.add("teachers:"+teachers);
            str.add("examTime:"+examTime);
            SavingData.addToFile(str);

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
            if(user instanceof Student student){
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
        course.absCourse.courses.remove(course);
        Course.courseList.remove(course);
    }
    public static User findUserById(String id){
        for (User u :
                User.userList) {
            if(id.equals(u.id+""))
                return u;
        }
        return null;
    }
    private static boolean isNotNumeric(String num){
        for (char c :
                num.toCharArray()) {
            if(c-'0'<0||c-'0'>9) return true;
        }
        return false;
    }
    private static boolean isValidScore(String score){
        double d;
        try {
            d=Double.parseDouble(score);
        } catch (NumberFormatException e) {
            MasterLogger.getInstance().log("invalid double format",true,Controller.class);
            return false;
        }
        return d>=0d&&d<=20d;
    }
    public static boolean isValidDate(String dateStr) {
        DateTimeFormatter dateFormatter =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        try {
            LocalDateTime.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            MasterLogger.getInstance().log("invalid dateTime format",true,Controller.class);
            return false;
        }
        return true;
    }
}
