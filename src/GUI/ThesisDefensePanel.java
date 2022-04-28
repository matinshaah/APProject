package GUI;

import Controller.Controller;
import Models.ThesisDefense;
import Models.Request;
import Models.Student;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ThesisDefensePanel extends UserMainPanel {
    JButton newReq;
    JScrollPane pane;
    JTable table;
    JLabel request;
    String[][] data;
    public ThesisDefensePanel(User user, LocalDateTime loginTime) {
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
        String[] column = {"type","name","student number","result","text"};
        table = new JTable(data,column);
        table.setFont(new Font("",Font.PLAIN,20));

        DefaultTableModel tableModel = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==4;
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
    protected ArrayList<ThesisDefense> findRequests(){
        ArrayList<ThesisDefense> userRequest = new ArrayList<>();
        for (Request request :
                Request.requests) {
            if(request instanceof ThesisDefense && request.student.id== user.id) userRequest.add((ThesisDefense) request);
        }
        return userRequest;
    }
    protected void setData(ArrayList<ThesisDefense> list){
        int size=list.size();
        data=new String[size][5];
        for (int i = 0; i < size; i++) {
            ThesisDefense thesisDefense = list.get(i);
            data[i][0]="thesisDefense";
            data[i][1]=thesisDefense.student.name;
            data[i][2]=thesisDefense.student.id+"";
            data[i][4]="-";
            if(thesisDefense.result==1) {
                data[i][3]="accepted";
                data[i][4]=thesisDefense.text();
            }
            else if(thesisDefense.result==2) data[i][3]="rejected";
            else  data[i][3]="registered";
        }
    }
    protected void setListeners(){
        newReq.addActionListener(e->{
            if(Controller.newThesisDefenseReq((Student) user)) {
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "Your request successfully registered");
            }else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"You have already registered a request");
            }
            new ThesisDefensePanel(user, lastLogin);
        });
    }


}
