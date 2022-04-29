package GUI;

import Controller.Controller;
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

public class StudentTempScoresPanel extends UserMainPanel{
    JScrollPane pane;
    String[][] data;
    JTable table;
    JButton recordObjections;
    JLabel idLabel;
    JTextField idField;
    public StudentTempScoresPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setData(findCourses());
        updatePane();
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        recordObjections = new JButton("record objection");
        idLabel = new JLabel("enter course id you want to object: ");
        idField = new JTextField();
    }

    @Override
    protected void align() {
        super.align();
        this.add(idLabel);
        this.add(idField);
        this.add(recordObjections);
        idLabel.setBounds(240,600,210,30);
        idField.setBounds(460,600,150,30);
        recordObjections.setBounds(615,605,150,20);
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
    private ArrayList<Course> findCourses(){
        ArrayList<Course> userCourse = new ArrayList<>();
        for (Course c :
                user.courses) {
            if (c.status== Course.ScoreStatus.Temporary) {
                userCourse.add(c);
            }
        }
        return Course.getCurrentTermCourses(userCourse);
    }
    private void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
        String[] column = {"course id","course name","score","score status","objection","teacher answer"};
        table = new JTable(data,column);
        table.setFont(new Font("",Font.PLAIN,20));

        DefaultTableModel tableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return (column==4||column==5);
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
    private void setData(ArrayList<Course> list){
        int size=list.size();
        data=new String[size][6];
        Student student=(Student) user;
        for (int i = 0; i < size; i++) {
            Course course = list.get(i);
            Student.Score score = student.scores.get(course.id+"");
            data[i][0]=course.id+"";
            data[i][1]=course.absCourse.name;
            data[i][2]=score.score;
            data[i][3]=score.passed?"passed":"failed";
            data[i][4]=score.objectionText;
            data[i][5]= score.objectionAnswer;
        }
    }
    private void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        recordObjections.addActionListener(e->{
            int row=-1;
            ArrayList<Course> list = findCourses();
            for (int i = 0; i < list.size(); i++) {
                if(idField.getText().trim().equals(list.get(i).id+"")) {
                    row=i;
                    break;
                }
            }
            if(row==-1){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"course not found");
                MasterLogger.getInstance().log("course not found",true,this.getClass());
            }else{
                int state=Controller.recordObjection((Student) user,list.get(row).id+"",(table.getValueAt(row,4)+"").trim());
                if(state==1) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "objection recorded successfully");
                    MasterLogger.getInstance().log("objection recorded successfully",false,this.getClass());
                }
                else if(state==2){
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"You have already recorded an objection");
                    MasterLogger.getInstance().log("You have already recorded an objection",true,this.getClass());
                }
                else if(state==0) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"Please fill the objection part of the course");
                    MasterLogger.getInstance().log("Please fill the objection part of the course",true,this.getClass());
                }
                new StudentTempScoresPanel(user, lastLogin);
            }
        });
    }
}
