package GUI;

import Models.Course;
import Models.Student;
import Models.User;
import resources.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StudentEduStatusPanel extends UserMainPanel{
    JScrollPane pane;
    String[][] data;
    JTable table;
    JLabel passedCreditNumber,totalAverage;

    public StudentEduStatusPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setData(user.courses);
        updatePane();
    }

    @Override
    protected void initCom() {
        super.initCom();
        passedCreditNumber = new JLabel("passed credit number: "+((Student)user).getPassedCreditNumber());
        totalAverage = new JLabel("total average: "+((Student)user).getTotalAverage());
        Font font = new Font("",Font.PLAIN,20);
        passedCreditNumber.setFont(font);
        totalAverage.setFont(font);
    }

    @Override
    protected void align() {
        super.align();
        this.add(passedCreditNumber);
        this.add(totalAverage);

        passedCreditNumber.setBounds(150,120,280,40);
        totalAverage.setBounds(500,120,200,40);
    }
    private  void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(150,200,1080,350);
    }


    private void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
        String[] column = {"course id","course name","term","credit","score"};
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
        for (int i = 0; i < 5; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }


    private void setData(ArrayList<Course> list){//"course id","course name","credit","score"
        int size=list.size();
        data=new String[size][5];
        Student student=(Student) user;
        for (int i = 0; i < size; i++) {
            Course course = list.get(i);
            Student.Score score = student.scores.get(course.id+"");
            data[i][0]=course.id+"";
            data[i][1]=course.absCourse.name;
            data[i][2]=course.term+"";
            data[i][3]=course.absCourse.credit+"";
            data[i][4]=course.status== Course.ScoreStatus.Final?score.score:"N/A";
        }
    }
}
