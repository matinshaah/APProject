package gui;

import controller.Controller;
import models.Course;
import models.Student;
import models.User;
import resources.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeacherTempScorePanel extends UserMainPanel{
    JScrollPane pane;
    String[][] data;
    JTable table;
    JMenu filter;
    Course course;
    JButton recordObjectionsAnswer,tempRegistration,finalRegistration;
    JLabel idLabel,courseStatus;
    JTextField idField;
    public TeacherTempScorePanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setListeners();
    }
    @Override
    protected void initCom() {
        super.initCom();
        initFilterMenu();
        recordObjectionsAnswer = new JButton("record answer");
        tempRegistration = new JButton("record temporary score");
        finalRegistration = new JButton("record final score");
        idLabel = new JLabel("enter the student id to record answer");
        idField = new JTextField();
        courseStatus = new JLabel(course!=null?"Status: "+course.status:"select course");

    }

    @Override
    protected void align() {
        super.align();
        this.add(filter);
        filter.setBounds(50,150,100,30);
        filter.setOpaque(true);
        this.add(recordObjectionsAnswer);
        this.add(tempRegistration);
        this.add(finalRegistration);
        this.add(idField);
        this.add(idLabel);
        this.add(courseStatus);
        courseStatus.setBounds(300,110,200,30);
        idLabel.setBounds(200,600,250,30);
        idField.setBounds(460,600,150,30);
        recordObjectionsAnswer.setBounds(615,605,150,20);
        tempRegistration.setBounds(800,600,180,30);
        finalRegistration.setBounds(1000,600,180,30);
    }

    private void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(180,150,1200,300);
    }
    private void initFilterMenu(){
        filter = new JMenu("select course");
        filter.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(filter);
        for (Course c :
                Course.getCurrentTermCourses(user.courses)) {
            JMenuItem m = new JMenuItem(c.absCourse.name);
            filter.add(m);
            m.addActionListener(e->filteredCourses(c.id+""));
        }
    }
    private void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
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
        Course course =Controller.findCourseByID(courseID);
        assert course != null;
        setData(course);
        this.course=course;
        courseStatus.setText("Status: "+course.status);
//        setListeners();
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
    private void setListeners(){
//        recordObjectionsAnswer
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        recordObjectionsAnswer.addActionListener(e->{
            if(course==null){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Please select a course first");
                MasterLogger.getInstance().log("course not selected",false,this.getClass());
            }else {
                int state = Controller.recordObjectionAnswer(course, idField.getText(), table);
                if (state == -1) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "student not found.");
                    MasterLogger.getInstance().log("course not found",false,this.getClass());
                } else if (state == 1) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "answer recorded successfully");
                    MasterLogger.getInstance().log("answer recorded successfully",false,this.getClass());
                } else if (state == 2) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "You have already recorded an answer");
                    MasterLogger.getInstance().log("recording again",false,this.getClass());
                } else if (state == 0){
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Please fill the answer part of the course");
                    MasterLogger.getInstance().log("empty field",false,this.getClass());
                }

            }
        });
        tempRegistration.addActionListener(e->{//0) not all score 1)success 2)already given 3)score out of bound
            if(course==null){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Please select a course first");
                MasterLogger.getInstance().log("course not selected",false,this.getClass());
            }else{
                int state = Controller.setTempScores(course, table);
                if (state == 0) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "You have to give score to all students");
                    MasterLogger.getInstance().log("must give score to all",false,this.getClass());
                } else if (state == 2) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "You have already given final scores");
                    MasterLogger.getInstance().log("scores are final",false,this.getClass());
                } else if (state == 3) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Score format error,score must be a number between 0,20");
                    MasterLogger.getInstance().log("invalid score format",false,this.getClass());
                } else if (state == 1) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Temporary scores are given successfully");
                    MasterLogger.getInstance().log("Temporary scores are given successfully",false,this.getClass());
                }
            }
        });
        finalRegistration.addActionListener(e->{//0)not temporary  1)success 2)already given
            if(course==null){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Please select a course first");
                MasterLogger.getInstance().log("course not selected",false,this.getClass());
            }else {
                int state = Controller.setFinalScores(course, table);
                if (state == 0) {
                    MasterLogger.getInstance().log("Temporary scores should be given first",false,this.getClass());
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "You must first record the scores temporarily");
                } else if (state == 2) {
                    MasterLogger.getInstance().log("scores are final",false,this.getClass());
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "You have already given final scores");
                } else if (state == 1) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Final scores are given successfully");
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "Final scores are given successfully");
                }
            }
        });
    }

//    ActionListener tempListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if(course!=null){
//
//            }
//        }
//    }


}
