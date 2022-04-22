package GUI;

import Models.*;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Array;
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
        filteredCourses("none","");
        initFilterMenu();
        this.add(filter);
        filter.setBounds(50,150,100,30);
        filter.setOpaque(true);
//        pane.setBackground(Color.blue);
//        pane.setOpaque(true);

    }
    private  void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(Color.blue);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(200,150,1000,300);
    }
    private void initFilterMenu(){
        filter = new JMenu("Filter courses");
        JMenuItem none = new JMenuItem("none");
        none.addActionListener(e -> filteredCourses("none",""));
        JMenu department = new JMenu("department");
        for (Department d :
                Department.list) {
            JMenuItem m = new JMenuItem(d.name);
            department.add(m);
            m.addActionListener(e -> filteredCourses("department",d.name));
        }
        JMenu term = new JMenu("term");
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
            int min =1399;
            JMenuItem m = new JMenuItem(min+i+""+j);
            term.add(m);
            m.addActionListener(e -> filteredCourses("term",m.getText()));
            }
        }
        JMenu credit = new JMenu("credit");
        for (int i = 1; i <5 ; i++) {
            JMenuItem m = new JMenuItem(i+"");
            credit.add(m);
            m.addActionListener(e -> filteredCourses("department",m.getText()));
        }
        filter.add(none); filter.add(department); filter.add(term); filter.add(credit);
    }
    private void filteredCourses(String filter,String detail){
        MainFrame.mainFrame.update();
        ArrayList<Course> list=new ArrayList<>();
        if(filter.equals("none")) list=Course.courseList;
        else if(filter.equals("department")){
            for (Course c :
                    Course.courseList) {
                if (c.absCourse.department.name.equals(detail))
                    list.add(c);
            }
        }else if(filter.equals("term")){
            for (Course c :
                    Course.courseList) {
                if((c.term+"").equals(detail))
                    list.add(c);
            }
        }else {
            for (Course c :
                    Course.courseList) {
                if ((c.absCourse.credit + "").equals(detail))
                    list.add(c);
            }
        }
        setData(list);
        updatePane();
    }

    private void initTable(){
        String[] column = {"Name","Department","Term","Credit","Teachers","ID","Prerequisite Courses"};
        table = new JTable(data,column);
//        table.getTableHeader().setBackground(Color.red);
//        table.setBackground(Color.blue);
        table.setFont(new Font("",Font.PLAIN,20));

        DefaultTableModel tableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        table.setRowHeight(40);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 7; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }

    private void setData(ArrayList<Course> list){
        int size = list.size();
        data = new String[size][7];
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
            data[i][5]=course.absCourse.id+"";
            StringBuilder courses= new StringBuilder();
            for (AbsCourse c :
                    course.absCourse.preCourses) {
                courses.append(c.name).append(" ");
            }
            if(courses.toString().equals("")) courses = new StringBuilder("-");
            data[i][6]= courses.toString();
        }
    }
}
