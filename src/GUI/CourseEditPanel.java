package GUI;

import Controller.Controller;
import Models.AbsCourse;
import Models.Course;
import Models.Teacher;
import Models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class CourseEditPanel extends UserMainPanel{
    JButton saveButton,delete;
    Course course;
    JTable table;
    public CourseEditPanel(User user, LocalDateTime loginTime, Course course) {
        super(user, loginTime);
        this.course=course;
        setListeners();
        initTable();
        setTable();
        this.add(table);
        if(course!=null) {
            this.add(delete);
            delete.setBounds(420,580,120,30);
        }
    }

    @Override
    protected void initCom() {
        super.initCom();
        saveButton = new JButton("save");
        delete = new JButton("delete course");
    }
    @Override
    protected void align() {
        super.align();
        this.add(saveButton);
        saveButton.setBounds(860,580,80,30);
    }
    private void initTable(){
        table = new JTable();
        table.setBounds(300,120,800,450);
        table.setBackground(user.color);
        table.setFont(new Font("",Font.BOLD,20));
        table.setBorder(BorderFactory.createLineBorder(Color.black));

        DefaultTableModel tableModel = new DefaultTableModel(10,2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0) return false;
                else return row != 4;
            }
        };
        table.setModel(tableModel);

        table.setRowHeight(45);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 2; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void setTable(){
        table.setValueAt("name",0,0);
        table.setValueAt("credit",1,0);
        table.setValueAt("total",2,0);
        table.setValueAt("teachers",3,0);
        table.setValueAt("department",4,0);
        table.setValueAt("Prerequisite Courses",5,0);
        table.setValueAt("term",6,0);
        table.setValueAt("class time",7,0);
        table.setValueAt("grade",8,0);
        table.setValueAt("exam time",9,0);
        table.setValueAt("yyyy/MM/dd hh:mm",9,1);


        table.setValueAt(user.department.name, 4, 1);
        if(course!=null) {
            table.setValueAt(course.absCourse.name, 0, 1);
            table.setValueAt(course.absCourse.credit + "", 1, 1);
            table.setValueAt(course.total+"", 2, 1);
            StringBuilder teachers = new StringBuilder();
            for (Teacher t :
                    course.teachers) {
                teachers.append(t.name).append(",");
            }
            table.setValueAt(teachers.toString(), 3, 1);
            StringBuilder pre = new StringBuilder();
            for (AbsCourse c : course.absCourse.preCourses) {
                pre.append(c.name).append(",");
            }
            table.setValueAt(pre.toString(), 5, 1);
            table.setValueAt(course.term+"", 6, 1);
            table.setValueAt(course.classTime, 7, 1);
            table.setValueAt(course.absCourse.grade.name(), 8, 1);
            table.setValueAt(course.examTime,9,1);
        }
    }
    private void setListeners(){
        saveButton.addActionListener(e -> {
            String message = Controller.editCourse(course,(table.getValueAt(0,1)+"").trim(),(table.getValueAt(1,1)+"").trim(),
                    (table.getValueAt(2,1)+"").trim(),(table.getValueAt(3,1)+"").trim(),
                    (table.getValueAt(4,1)+"".trim()),(table.getValueAt(5,1)+"").trim(),(table.getValueAt(6,1)+"").trim(),
                    (table.getValueAt(7,1)+"").trim(),(table.getValueAt(8,1)+"").trim(),(table.getValueAt(9,1)+"").trim());
            if(! message.equals("")){
                if(message.equals("id"))
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"The course already exists");
                else JOptionPane.showMessageDialog(MainFrame.mainFrame,"We've got a problem,please check the \""+message+"\" part and try again");
            }else {
                if(course==null) JOptionPane.showMessageDialog(MainFrame.mainFrame,"The course is added successfully");
                else JOptionPane.showMessageDialog(MainFrame.mainFrame,"The course edited successfully");
                new EVCCoursePanel(user,lastLogin);
            }
        });
        delete.addActionListener(e -> {
            Controller.removeCourse(course);
            JOptionPane.showMessageDialog(MainFrame.mainFrame,"The course deleted successfully");
            new EVCCoursePanel(user,lastLogin);
        });
    }

}
