package resources;

import Controller.Controller;
import Models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class LoadingData {
    String path="./src/resources/initialData.txt";
    File file = new File(path);
    Scanner scanner;

    {
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            MasterLogger.getInstance().log("file not found",false,LoadingData.class);
        }
    }
    //newDepartment,newAbsCourse,newCourse,newTeacher,newStudent,newRequest,studentAddCourse,course,teacher,student,removeCourse,removeTeacher
    //setEVC,setScore,setObjection,setObjectionAnswer,request,makeFinal
    public LoadingData(){
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            String function = "";
            String detail = "";
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '/') {
                    function = string.substring(0, i);
                    detail = string.substring(i + 1);
                    break;
                }
            }
            String[] details = detail.split("}");
            for (int i = 0; i < details.length; i++) {
                details[i]=details[i].trim();
                for (int j = 0; j < details[i].length(); j++) {
                    if(details[i].charAt(j)==':'){
                        details[i]=details[i].substring(j+1);
                        break;
                    }
                }            }
            System.out.println();

            switch (function){
                case "newDepartment":
                    new Department(details[1]);
                    break;
                case "newAbsCourse"://newAbsCourse/ }name:AP   }credit:4   }grade:BS    }preCourses:BP   }department:Math

                    String preCourse=details[2];
                    String[] pre = preCourse.split(",");
                    HashSet<AbsCourse> absCourses= new HashSet<>();
                    for (String p :
                            pre) {
                        AbsCourse absCourse =AbsCourse.getAbsCourseByName(p);
                        if(absCourse!=null) absCourses.add(absCourse);
                    }
//                    System.out.println(details[1]);
                    new AbsCourse(details[1],Integer.parseInt(details[2]),Department.getDepartmentByName(details[5]),absCourses, Grade.getGradeByName(details[3]));

//                    str.add("newAbsCourse");
//                    str.add("name:"+name);
//                    str.add("credit:"+credit);
//                    str.add("grade:"+grade);
//                    str.add("preCourses:"+preCourse);
//                    str.add("department:"+department);
                    break;
                case "newCourse":
//newCourse/   }term:1399  }total:100      }absCourseID:10001   }teachers:         }classTime:Thurs,Sun 17:00 19:00    }examTime:2022/5/20 09:00
                    String[] teacher = details[4].split(",");
                    HashSet<Teacher> teacherHashSet = new HashSet<>();
                    for (String t :
                            teacher) {
                        Teacher teacher1=Teacher.getTeacherByName(t);
                        if(teacher1!=null) teacherHashSet.add(teacher1);
                    }
                    Course course2 =new Course(Integer.parseInt(details[1]),Integer.parseInt(details[2]),AbsCourse.getAbsCourseById(details[3]),teacherHashSet,new ArrayList<>(),details[5],details[6]);
                    for (Teacher t:
                         teacherHashSet) {
                        if(!t.courses.contains(course2))t.courses.add(course2);
                    }
                    break;
                case   "newTeacher":
                    try {
                        boolean isDC=false;
                        if(details.length>9) isDC=Boolean.parseBoolean(details[9]);
                        new Teacher(details[1],details[2],details[3],Department.getDepartmentByName(details[4])
                                ,new ArrayList<>(), Teacher.Degree.getDegreeByName(details[5]),false,isDC,details[6],details[7],details[8]);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        MasterLogger.getInstance().log("cant new teacher",true,LoadingData.class);
                    }
                    //newTeacher/  }name:Khazaie  }password:1     }nationalCode:1   }department:Math      }degree:assistant professor     }email:kjskf  }phoneNumber:1  }imagePath:src/resources/Images/defaultProfile.png
                    break;
                case "newStudent":
                    try {
                        new Student(details[1],details[2],details[3],Department.getDepartmentByName(details[4]),new ArrayList<>(),Integer.parseInt(details[5]),Grade.getGradeByName(details[6]),Teacher.getTeacherByID(details[7]), Student.Status.getStatusByName(details[8]),details[9],details[10],details[11]);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        MasterLogger.getInstance().log("cant new student",true,LoadingData.class);
                    }
                    //newStudent/  }name:Matin  }password:   }nationalCode:2  }department:Math    }enteringYear:99  }grade:BS    }supervisor:10001   }status:studying   }imagePath:src/resources/Images/defaultProfile.png }email:shahnaziamirmatin@gamil.com   }phoneNumber:1235
                    break;
                case "newRequest":
                    Student student5 = Student.getStudentById(details[2]);
                    if(student5==null) System.out.println("khfshfskhf");
                    switch (details[1]){
                        case "minor":
                            new Minor(Student.getStudentById(details[2]),Department.getDepartmentByName(details[3]));
                            break;
                        case "recommend":
                            new Recommendation(Student.getStudentById(details[2]),Teacher.getTeacherByID(details[3]));
                            break;
                        case "withdraw":
                            new Withdraw(Student.getStudentById(details[2]));
                            break;
                        case "thesis":
                            new ThesisDefense(Student.getStudentById(details[2]));
                            break;
                        case "dormitory":
                            new Dormitory(Student.getStudentById(details[2]));
                            break;
                        case "eduCertificate":
                            new EduCertificate(Student.getStudentById(details[2]));
                            break;
                    }
                    break;
                case "studentAddCourse":
                    Student.addCourse(Student.getStudentById(details[1]), Controller.findCourseByID(details[2]));
                    break;
                case "course":
                    Course course = Controller.findCourseByID(details[1]);
                    course.absCourse.name=details[2]; course.absCourse.credit=Integer.parseInt(details[3]);
                    preCourse=details[4];
                    pre = preCourse.split(",");
                    absCourses= new HashSet<>();
                    for (String p :
                            pre) {
                        AbsCourse absCourse =AbsCourse.getAbsCourseByName(p);
                        if(absCourse!=null) absCourses.add(absCourse);
                    }
                    course.absCourse.preCourses=absCourses;
                    course.absCourse.grade=Grade.getGradeByName(details[5]);
                    course.classTime=details[6];
                    course.term=Integer.parseInt(details[7]);
                    course.total=Integer.parseInt(details[8]);
                     teacher = details[9].split(",");
                     teacherHashSet = new HashSet<>();
                    for (String t :
                            teacher) {
                        Teacher teacher1=Teacher.getTeacherByName(t);
                        if(teacher1!=null) teacherHashSet.add(teacher1);
                    }
                    for (Teacher t :
                            Teacher.list) {
                        t.courses.remove(course);
                    }
                    for (Teacher t:
                            teacherHashSet) {
                        if(!t.courses.contains(course))t.courses.add(course);
                    }
                    course.teachers=teacherHashSet;
                    course.examTime=details[10];
//
//
//            str.add("name:"+name);
//            str.add("credit:"+credit);
//            str.add("preCourses:"+preCourse);
//            str.add("grade:"+grade);
//            str.add("classTime:"+classTime);
//            str.add("term:"+term);
//            str.add("total:"+total);
//            str.add("teachers:"+teachers);
//            str.add("examTime:"+examTime);
                    break;
                case "teacher":
                    Teacher teacher1= Teacher.getTeacherByID(details[1]);
                    teacher1.name=details[2];
                    teacher1.nationalCode=details[3];
                    teacher1.degree= Teacher.Degree.getDegreeByName(details[4]);
                    teacher1.phoneNumber=details[5];
                    teacher1.image=details[6];
                    teacher1.email=details[7];
//                    str.add("teacher");
//                    str.add("id:"+teacher.id);
//                    str.add("name:"+name);
//                    str.add("nationalCode:"+nationalCode);
//                    str.add("degree:"+degree);
//                    str.add("phoneNumber:"+phoneNumber);
//                    str.add("imagePath:"+imagePath);
//                    str.add("email:"+email);
                    break;
                case "student":
                    Student student =Student.getStudentById(details[1]);
                    student.name=details[2];
                    student.nationalCode=details[3];
                    student.grade= Grade.getGradeByName(details[4]);
                    student.supervisor=Teacher.getTeacherByID(details[5]);
                    student.status= Student.Status.getStatusByName(details[6]);
                    student.phoneNumber=details[7];
                    student.image=details[8];
                    student.email=details[9];
//                    str.add("student");
//                    str.add("id:"+student.id);
//                    str.add("name:"+name);
//                    str.add("nationalCode:"+nationalCode);
//                    str.add("grade:"+grade);
//                    str.add(teacher==null?"supervisor:":"supervisor:"+teacher.id+"");
//                    str.add("status:"+status);
//                    str.add("phoneNumber:"+phoneNumber);
//                    str.add("imagePath:"+imagePath);
//                    str.add("email:"+email);

                    break;
                case "removeCourse":
                    Course course1 = Controller.findCourseByID(details[1]);
                    if (course1 != null) {
                        course1.absCourse.courses.remove(course1);
                    }else {
                        MasterLogger.getInstance().log("course not found to remove",false,LoadingData.class);
                    }
                    Course.courseList.remove(course1);
                    break;
                case "removeTeacher":
                    teacher1 = Teacher.getTeacherByID(details[1]);
                    Controller.removeTeacher(teacher1);
                    break;
                case "setEVC":
                    teacher1 = Teacher.getTeacherByID(details[1]);
                    Controller.setEVC(teacher1);
                    break;
                case "setObjection":
                    student =Student.getStudentById(details[1]);
                    student.scores.get(details[2]).objectionText=details[3];
                    break;
                case "setObjectionAnswer":
                    student =Student.getStudentById(details[1]);
                    student.scores.get(details[2]).objectionAnswer=details[3];
                    break;
                case "request":
                    Request request = Request.findReqByID(details[1]);
                    if(request instanceof Minor){
                        Minor minor =(Minor) request;
                        char a = details[2].charAt(0);
                        details[2]=details[2].substring(1);
                        int result=Integer.parseInt(details[2]);
                        if(a=='1') minor.firstAccepted=result;
                        else minor.secondAccepted=result;
                        result=Integer.parseInt(details[3]);
                        minor.result=result;
                    }else {
                        request.result=Integer.parseInt(details[2]);
                        if(request instanceof Withdraw && request.result==1){
                            request.student.status= Student.Status.WITHDRAW_FROM_EDUCATION;
                        }
                    }
                    break;
                case "makeFinal":
                    course = Controller.findCourseByID(details[1]);//1temp 2:final
                    if(details[2].equals("1")){
                        course.status= Course.ScoreStatus.Temporary;
                    }else if(details[2].equals("2")){
                        course.status= Course.ScoreStatus.Final;
                    }else {
                        MasterLogger.getInstance().log("make final state is not 1,2",false,LoadingData.class);
                    }
                    break;
                case "setScore":
                    student = Student.getStudentById(details[1]);
                    long score = Long.parseLong(details[3]);
                    student.scores.get(details[2]).score=score+"";
                    break;

            }
            //newDepartment,newAbsCourse,newCourse,newTeacher,newStudent,newRequest,studentAddCourse,course,teacher
            // ,student,removeCourse,removeTeacher
            //setEVC,setScore,setObjection,setObjectionAnswer,request,makeFinal
            

        }
    }
}
