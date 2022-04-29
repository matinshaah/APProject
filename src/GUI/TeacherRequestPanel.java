package GUI;

import Controller.Controller;
import Models.*;
import resources.MasterLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TeacherRequestPanel extends UserMainPanel{
    JScrollPane pane;
    JTable table;
    JButton accept,reject;
    JLabel idLabel;
    JTextField idField;
    String[][] data;
    ArrayList<Request> reqList;

    public TeacherRequestPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        reqList=findRequests();
        setData(reqList);
        updatePane();
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        accept = new JButton("accept");
        reject = new JButton("reject");
        idLabel = new JLabel("enter the request id:");
        idField = new JTextField();
    }
    @Override
    protected void align() {
        super.align();
        this.add(accept);
        this.add(reject);
        this.add(idField);
        this.add(idLabel);
        idLabel.setBounds(300,500,150,40);
        idField.setBounds(480,500,150,40);
        accept.setBounds(720,500,120,40);
        reject.setBounds(860,500,120,40);
        idLabel.setFont(new Font("", Font.PLAIN,15));
        accept.setFocusable(false);
        reject.setFocusable(false);
    }
    private  void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(150,160,1080,300);
    }
    private void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
        String[] column = {"type","student name","student number","student department","request id","time"};
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
        for (int i = 0; i < column.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(i).setPreferredWidth(180);
        }
        table.getColumnModel().getColumn(column.length-1).setPreferredWidth(240);
    }
    protected ArrayList<Request> findRequests(){
        ArrayList<Request> list=new ArrayList<>();
        for (Request r :
                Request.requests) {
            if(r.result==0&& r instanceof Recommendation && ((Recommendation)r).teacher==user)
                list.add(r);
        }
        if(((Teacher)user).isEVC){
            for (Request r :
                    Request.requests) {
                if(r.result==0&& !(r instanceof Recommendation) && (r.student.department==user.department||r instanceof Minor&&((Minor)r).secondDep==user.department))
                    list.add(r);

            }
        }
        return list;
    }
    protected void setData(ArrayList<Request> list){
        int size=list.size();
        data=new String[size][6];
        for (int i = 0; i < size; i++) {
            Request request = list.get(i);
            data[i][0]=request.name;
            data[i][1]=request.student.name;
            data[i][2]=request.student.id+"";
            data[i][3]=request.student.department.name;
            data[i][4]=request.id+"";
            data[i][5]=request.time.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        }
    }
    protected void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        accept.addActionListener(e ->{
            Request r = Request.findReqByID(idField.getText());
            if(r!=null&&reqList.contains(r)){
                Controller.replyReq(r,(Teacher) user,1);
                MasterLogger.getInstance().log("request accepted",false,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"The request is successfully accepted.");
            }else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Request not found.");
            }
            new TeacherRequestPanel(user,lastLogin);
        });
        reject.addActionListener(e ->{
            Request r = Request.findReqByID(idField.getText());
            if(r!=null&&reqList.contains(r)){
                Controller.replyReq(r,(Teacher) user,2);
                MasterLogger.getInstance().log("request rejected",false,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"The request is successfully rejected.");
            }else {
                MasterLogger.getInstance().log("request not found",true,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Request not found.");
            }
            new TeacherRequestPanel(user,lastLogin);
        });
    }
}
