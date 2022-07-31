package gui;


import models.Department;
import models.Teacher;
import models.User;
import resources.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeacherListPanel  extends UserMainPanel{
    JScrollPane pane;
    String[][] data;
    JTable table;
    JMenu filter;
    public TeacherListPanel(User user, LocalDateTime loginTime) {
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
        filter.setBorder(BorderFactory.createLineBorder(Color.black));
    }
    private  void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(180,150,1200,300);
    }
    private void initFilterMenu(){
        filter = new JMenu("Filter teachers");
        JMenuItem none = new JMenuItem("none");
        none.addActionListener(e -> filteredCourses("none",""));
        JMenu department = new JMenu("department");
        for (Department d :
                Department.list) {
            JMenuItem m = new JMenuItem(d.name);
            department.add(m);
            m.addActionListener(e ->filteredCourses("department",d.name));
        }
        JMenu degree = new JMenu("degree");
        JMenuItem m1 = new JMenuItem(Teacher.Degree.ASSISTANT_PROFESSOR.name);
        JMenuItem m2 = new JMenuItem(Teacher.Degree.ASSOCIATE_PROFESSOR.name);
        JMenuItem m3 = new JMenuItem(Teacher.Degree.PROFESSOR.name);
        degree.add(m1); degree.add(m2); degree.add(m3);
        m1.addActionListener(e -> filteredCourses("degree",m1.getText()));
        m2.addActionListener(e -> filteredCourses("degree",m2.getText()));
        m3.addActionListener(e -> filteredCourses("degree",m3.getText()));
        JMenuItem evc = new JMenuItem("educational vice chairs");
        evc.addActionListener(e ->filteredCourses("evc",""));
        JMenuItem dc = new JMenuItem("department chairs");
        dc.addActionListener(e ->filteredCourses("dc",""));
        filter.add(none); filter.add(department); filter.add(degree); filter.add(evc); filter.add(dc);
    }
    private void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
        String[] column = {"name","department","degree","Email","phone number","ID"};
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 6; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private void filteredCourses(String filter,String detail){
        MainFrame.mainFrame.update();
        ArrayList<Teacher> list=new ArrayList<>();
        switch (filter) {
            case "none":
                list = Teacher.list;
                break;
            case "evc":
                for (Teacher t :
                        Teacher.list) {
                    if (t.isEVC) list.add(t);
                }
                break;
            case "dc":
                for (Teacher t :
                        Teacher.list) {
                    if (t.isDC) list.add(t);
                }
                break;
            case "department":
                for (Teacher t :
                    Teacher.list) {
                    if (t.department.name.equals(detail))
                        list.add(t);
                }
//                list = Department.getDepartmentByName(detail).teachers;
                break;
            default:
                for (Teacher t :
                        Teacher.list) {
                    if (t.degree.name.equals(detail))
                        list.add(t);
                }
                break;
        }
        setData(list);
        updatePane();
    }
    private void setData(ArrayList<Teacher> list){
        int size=list.size();
        data=new String[size][6];
        for (int i = 0; i < size; i++) {
            Teacher teacher = list.get(i);
            data[i][0]=teacher.name;
            data[i][1]=teacher.department.name;
            data[i][2]=teacher.degree.name;
            data[i][3]=teacher.email;
            data[i][4]=teacher.phoneNumber!=null&&teacher.phoneNumber.length()!=0?teacher.phoneNumber:"-";
            data[i][5]= teacher.id+"";

        }
    }
}
