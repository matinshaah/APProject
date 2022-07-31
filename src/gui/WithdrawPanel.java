package gui;

import controller.Controller;
import models.Withdraw;
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

public class WithdrawPanel extends UserMainPanel{
    JButton newReq;
    JScrollPane pane;
    JTable table;
    JLabel request;
    String[][] data;
    public WithdrawPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setData(findRequests());
        updatePane();
        setListeners();
    }
    @Override
    protected void initCom() {
        super.initCom();
        newReq = new JButton("new request");
        request = new JLabel("your requests:");
    }

    @Override
    protected void align() {
        super.align();
        this.add(newReq);
        this.add(request);
        newReq.setBounds(550,150,120,40);
        request.setFont(new Font("", Font.PLAIN,15));
        request.setBounds(150,250,120,40);
        newReq.setFocusable(false);
    }
    private void updatePane(){
        MasterLogger.getInstance().log("pane is updated",false,this.getClass());
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
        String[] column = {"type","name","student number","result"};
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
        for (int i = 0; i < column.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    protected ArrayList<Withdraw> findRequests(){
        MasterLogger.getInstance().log("list is found",false,this.getClass());
        ArrayList<Withdraw> userRequest = new ArrayList<>();
        for (Request request :
                Request.requests) {
            if(request instanceof Withdraw && request.student.id== user.id) userRequest.add((Withdraw) request);
        }
        return userRequest;
    }
    protected void setData(ArrayList<Withdraw> list){
        MasterLogger.getInstance().log("data is set",false,this.getClass());
        int size=list.size();
        data=new String[size][4];
        for (int i = 0; i < size; i++) {
            Withdraw withdraw = list.get(i);
            data[i][0]="withdraw";
            data[i][1]=withdraw.student.name;
            data[i][2]=withdraw.student.id+"";
            if(withdraw.result==1) {
                data[i][3]="accepted";
            }
            else if(withdraw.result==2) data[i][3]="rejected";
            else  data[i][3]="registered";
        }
    }
    protected void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        newReq.addActionListener(e->{
            if(Controller.newWithdrawReq((Student) user)) {
                MasterLogger.getInstance().log("request registered",false,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "Your request successfully registered");
            }else {
                MasterLogger.getInstance().log("already registered",true,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"You have already registered a request");
            }
            new WithdrawPanel(user, lastLogin);
        });
    }
}
