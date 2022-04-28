package GUI;

import Controller.Controller;
import Models.Course;
import Models.Student;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DepCourseScorePanel extends UserMainPanel{
    JScrollPane pane;
    String[][] data;
    JTable table;
    Course course;
    JLabel average,averageWithoutFailed,passedNumber,failedNumber;
    public DepCourseScorePanel(User user, LocalDateTime loginTime, Course course) {
        super(user, loginTime);
        this.course=course;
        filteredCourses(course.id+"");
        setComp();
    }
    private void setComp(){
        average = new JLabel("course average: "+course.getCourseAverage());
        averageWithoutFailed = new JLabel("average of passed: "+course.getCoursePassAverage());
        passedNumber = new JLabel("passed number: "+course.getPassedNumber());
        failedNumber = new JLabel("failed number: "+course.getFailedNumber());
        Font font = new Font("", Font.PLAIN,20);
        average.setFont(font);
        averageWithoutFailed.setFont(font);
        failedNumber.setFont(font);
        passedNumber.setFont(font);

        this.add(average);
        this.add(averageWithoutFailed);
        this.add(failedNumber);
        this.add(passedNumber);
        average.setBounds(100,150,240,40);
        averageWithoutFailed.setBounds(100,200,240,40);
        passedNumber.setBounds(360,150,240,40);
        failedNumber.setBounds(360,200,240,40);
    }


    private void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(100,300,1200,300);
    }
    private void initTable(){
        String[] column = {"student name","student id","course name","score","objection text","answer"};
        table = new JTable(data,column);
        table.setFont(new Font("",Font.PLAIN,20));

        DefaultTableModel tableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column>=3);
            }
        };
        table.setModel(tableModel);
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < 6; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            int size;
            if(i==4||i==5) size=400;
            else size=150;
            table.getColumnModel().getColumn(i).setPreferredWidth(size);
        }
    }

    private void filteredCourses(String courseID){
        MainFrame.mainFrame.update();
        Course course = Controller.findCourseByID(courseID);
        assert course != null;
        setData(course);
        this.course=course;
        updatePane();
    }
    private void setData(Course course){//"student name","student id","course name","score","objection text","answer"
        ArrayList<Student> list =course.students;
        int size=list.size();
        data=new String[size][6];
        for (int i = 0; i < size; i++) {
            Student student = list.get(i);
            Student.Score score =student.scores.get(course.id+"");
            data[i][0]=student.name;
            data[i][1]=student.id+"";
            data[i][2]=course.absCourse.name;
            data[i][3]=score.score;
            data[i][4]=score.objectionText;
            data[i][5]=score.objectionAnswer;
        }
    }
}
