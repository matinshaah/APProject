package GUI;

import Controller.Controller;
import Models.Minor;
import Models.Request;
import Models.Student;
import Models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MinorPanel extends UserMainPanel{
    JButton newReq;
    JScrollPane pane;
    JTable table;
    String[][] data;
    JLabel secDepLabel,request;
    JTextField secDepField;
    public MinorPanel(User user, LocalDateTime loginTime) {
        super(user, loginTime);
        setData(findRequests());
        updatePane();
        setListeners();
    }

    @Override
    protected void initCom() {
        super.initCom();
        newReq = new JButton("new request");
        secDepField=new JTextField();
        secDepLabel = new JLabel("enter second department name");
        request = new JLabel("your requests:");
    }

    @Override
    protected void align() {
        super.align();
        this.add(secDepField); this.add(secDepLabel); this.add(newReq); this.add(request);
        secDepLabel.setBounds(100,150,300,40);
        secDepLabel.setFont(new Font("",Font.PLAIN,15));
        secDepField.setBounds(400,150,100,40);
        newReq.setBounds(550,150,120,40);
        request.setFont(new Font("", Font.PLAIN,15));
        request.setBounds(150,250,120,40);
        newReq.setFocusable(false);
    }
    private ArrayList<Minor> findRequests(){
        ArrayList<Minor> userRequest = new ArrayList<>();
        for (Request request :
             Request.requests) {
            if(request instanceof Minor && request.student.id== user.id) userRequest.add((Minor) request);
        }
        return userRequest;
    }

    private  void  updatePane(){
        if(pane!= null) this.remove(pane);
        initTable();
        pane = new JScrollPane(table);
        this.add(pane);
        pane.getViewport().setBackground(user.color);
        pane.setBorder(BorderFactory.createLineBorder(Color.black));
        pane.setBounds(150,300,1000,300);
    }
    private void initTable(){
        String[] column = {"type","name","student number","first department","second department","result"};
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
    private void setData(ArrayList<Minor> list){
        int size=list.size();
        data=new String[size][6];
        for (int i = 0; i < size; i++) {
            Minor minor = list.get(i);
            data[i][0]="minor";
            data[i][1]=minor.student.name;
            data[i][2]=minor.student.id+"";
            data[i][3]=minor.student.department.name;
            data[i][4]=minor.secondDep.name;
            if(minor.result==1) data[i][5]="accepted";
            else if(minor.result==2) data[i][5]="rejected";
            else  data[i][5]="registered";
        }
    }
    private void setListeners(){
        newReq.addActionListener(e->{
            if(Controller.newMinorReq((Student) user,secDepField.getText())){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Your request successfully registered");
            }else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Department not found");
            }
            new MinorPanel(user,lastLogin);
        });
    }
}
