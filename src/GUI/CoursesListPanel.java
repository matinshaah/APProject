package GUI;

import Models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class CoursesListPanel extends UserMainPanel{
    JScrollPane pane;
    String[][] data;
    JTable table;
    JMenu filter;
    public CoursesListPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
    }

    @Override
    protected void initCom() {
        super.initCom();
        filteredCourses("none","",Course.courseList);
        initFilterMenu();
        this.add(filter);
        filter.setBounds(30,150,100,30);
        filter.setOpaque(true);
    }
    private  void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(150,150,1200,300);
    }
    private void initFilterMenu(){
        filter = new JMenu("Filter courses");
        JMenuItem none = new JMenuItem("none");
        none.addActionListener(e -> filteredCourses("none","",Course.courseList));
        JMenu department = new JMenu("department");
        for (Department d :
                Department.list) {
            JMenuItem m = new JMenuItem(d.name);
            department.add(m);
            m.addActionListener(e -> filteredCourses("department",d.name,Course.courseList));
        }
        JMenu term = new JMenu("term");
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
            int min =1399;
            JMenuItem m = new JMenuItem(min+i+""+j);
            term.add(m);
            m.addActionListener(e -> filteredCourses("term",m.getText(),Course.courseList));
            }
        }
        JMenu credit = new JMenu("credit");
        for (int i = 1; i <5 ; i++) {
            JMenuItem m = new JMenuItem(i+"");
            credit.add(m);
            m.addActionListener(e -> filteredCourses("credit",m.getText(),Course.courseList));
        }
        JMenu grade = new JMenu("grade");
        JMenuItem BS=new JMenuItem("BS"),MS=new JMenuItem("MS"),PHD=new JMenuItem("PHD");
        grade.add(BS); grade.add(MS); grade.add(PHD);
        BS.addActionListener(e -> filteredCourses("grade","BS",Course.courseList));
        MS.addActionListener(e -> filteredCourses("grade","MS",Course.courseList));
        PHD.addActionListener(e -> filteredCourses("grade","PHD",Course.courseList));
        filter.add(none); filter.add(department); filter.add(term); filter.add(credit); filter.add(grade);
    }
    protected void filteredCourses(String filter,String detail,ArrayList<Course> list){
        MainFrame.mainFrame.update();
//        ArrayList<Course> list=new ArrayList<>();
        if(list==Course.courseList) {
            list = new ArrayList<>();
            switch (filter) {
                case "none":
                    list = Course.courseList;
                    break;
                case "department":
                    for (Course c :
                            Course.courseList) {
                        if (c.absCourse.department.name.equals(detail))
                            list.add(c);
                    }
                    break;
                case "term":
                    for (Course c :
                            Course.courseList) {
                        if ((c.term + "").equals(detail))
                            list.add(c);
                    }
                    break;
                case "credit":
                    for (Course c :
                            Course.courseList) {
                        if ((c.absCourse.credit + "").equals(detail))
                            list.add(c);
                    }
                    break;
                case "grade":
                    Grade grade = Grade.getGradeByName(detail);
                    for (Course c :
                            Course.courseList) {
                        if (c.absCourse.grade == grade) list.add(c);
                    }
                    break;
            }
        }
        setData(list);
        updatePane();
    }

    private void initTable(){
        String[] column = {"Name","Department","Term","Credit","Teachers","ID","Prerequisite Courses","Total","CLass time","Grade","Exam time"};
        table = new JTable(data,column);
        table.setFont(new Font("",Font.PLAIN,20));

        DefaultTableModel tableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 11; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        for (int i = 0; i < 11; i++) {
            int j =100;
            if(i==3||i==7||i==9) j=40;
            if(i==8) j=280;
            if(i==5||i==6) j=150;
            if(i==10) j=200;
            table.getColumnModel().getColumn(i).setPreferredWidth(j);
        }

    }

    private void setData(ArrayList<Course> list){
        Course.sortedList(list);
        int size = list.size();
        data = new String[size][11];
        for (int i = 0; i < size ; i++) {
            Course course = list.get(i);
            data[i][0]=course.absCourse.name;
            data[i][1]=course.absCourse.department.name;
            data[i][2]=course.term+"";
            data[i][3]=course.absCourse.credit+"";
            StringBuilder teachers= new StringBuilder();
            for (Teacher t :
                    course.teachers) {
                teachers.append(t.name).append(" ");
            }
            data[i][4]= teachers.toString();
            data[i][5]=course.id+"";
            StringBuilder courses= new StringBuilder();
            for (AbsCourse c :
                    course.absCourse.preCourses) {
                courses.append(c.name).append(" ");
            }
            if(courses.toString().equals("")) courses = new StringBuilder("-");
            data[i][6]= courses.toString();
            data[i][7]= course.total+"";
            data[i][8]=course.classTime;
            data[i][9]=course.absCourse.grade.name();
            data[i][10]=course.examTime;
        }
    }
}
