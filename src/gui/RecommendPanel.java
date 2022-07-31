package gui;

import controller.Controller;
import models.Recommendation;
import models.Request;
import models.Student;
import models.User;
import resources.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecommendPanel extends UserMainPanel{
    JButton newReq;
    JScrollPane pane;
    JTable table;
    String[][] data;
    JLabel teacherLabel,request;
    JTextField teacherField;
    public RecommendPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setData(findRequests());
        updatePane();
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        newReq = new JButton("new request");
        teacherField=new JTextField();
        teacherLabel = new JLabel("enter teacher id");
        request = new JLabel("your requests:");
    }

    @Override
    protected void align() {
        super.align();
        this.add(teacherField); this.add(teacherLabel); this.add(newReq); this.add(request);
        teacherLabel.setBounds(100,150,300,40);
        teacherLabel.setFont(new Font("",Font.PLAIN,15));
        teacherField.setBounds(400,150,100,40);
        newReq.setBounds(550,150,120,40);
        request.setFont(new Font("", Font.PLAIN,15));
        request.setBounds(150,250,120,40);
        newReq.setFocusable(false);
    }



    private  void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(150,300,1080,300);
    }
    private void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
        String[] column = {"type","name","student number","teacher","result","text"};
        table = new JTable(data,column);
        table.setFont(new Font("",Font.PLAIN,20));

        DefaultTableModel tableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==5;
            }
        };
        table.setModel(tableModel);
        table.setRowHeight(40);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int i = 0; i < column.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            if(i==column.length-1)
                table.getColumnModel().getColumn(i).setPreferredWidth(800);
            else
                table.getColumnModel().getColumn(i).setPreferredWidth(160);
        }
    }
    private ArrayList<Recommendation> findRequests(){
        ArrayList<Recommendation> userRequest = new ArrayList<>();
        for (Request request :
                Request.requests) {
            if(request instanceof Recommendation && request.student.id== user.id) userRequest.add((Recommendation) request);
        }
        return userRequest;
    }
    private void setData(ArrayList<Recommendation> list){
        int size=list.size();
        data=new String[size][6];
        for (int i = 0; i < size; i++) {
            Recommendation recommendation = list.get(i);
            data[i][0]="recommendation";
            data[i][1]=recommendation.student.name;
            data[i][2]=recommendation.student.id+"";
            data[i][3]=recommendation.teacher.name;
            data[i][5]="-";
            if(recommendation.result==1) {
                data[i][4]="accepted";
                data[i][5]=recommendation.text();
            }
            else if(recommendation.result==2) data[i][4]="rejected";
            else  data[i][4]="registered";
        }
    }

    private void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        newReq.addActionListener(e->{
            if(Controller.newRecommendReq((Student) user,teacherField.getText())){
                MasterLogger.getInstance().log("request registered",false,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Your request successfully registered");
            }else {
                MasterLogger.getInstance().log("teacher not found",true,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Teacher not found");
            }
            new RecommendPanel(user,lastLogin);
        });
    }
}
