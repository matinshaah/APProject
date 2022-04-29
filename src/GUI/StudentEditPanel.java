package GUI;

import Controller.Controller;
import Models.Student;
import Models.User;
import resources.ImageResource;
import resources.MasterLogger;
import resources.ResourceManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class StudentEditPanel extends UserMainPanel{
    Student student;
    JButton saveButton;
    JTable table;
    JLabel image;
    public StudentEditPanel(User user, LocalDateTime loginTime,Student student) {
        super(user, loginTime);
        this.student= student;
        setListeners();
        initTable();
        setTable();
        this.add(table);
        if(student!=null) {
            Image imageIcon = new ImageIcon(student.image).getImage().getScaledInstance(96,96,Image.SCALE_DEFAULT);
            image.setIcon(new ImageIcon(imageIcon));
        }else image.setIcon(new ImageIcon(ResourceManager.get(ImageResource.defaultProfile)));
    }
    @Override
    protected void initCom() {
        super.initCom();
        saveButton = new JButton("save");
        image = new JLabel();
    }
    @Override
    protected void align() {
        super.align();
        this.add(saveButton);
        this.add(image);
        saveButton.setBounds(820,605,80,30);
        image.setBounds(1200,500,96,96);
    }
    protected void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
        table = new JTable();
        table.setBounds(275,150,800,450);
        table.setBackground(user.color);
        table.setFont(new Font("",Font.BOLD,20));
        table.setBorder(BorderFactory.createLineBorder(Color.black));

        DefaultTableModel tableModel = new DefaultTableModel(10,2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0) return false;
                else return !(row==2);
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
        table.setValueAt("national code",1,0);
        table.setValueAt("department",2,0);
        table.setValueAt("supervisor",3,0);
        table.setValueAt("entering year",4,0);
        table.setValueAt("email",5,0);
        table.setValueAt("phone number",6,0);
        table.setValueAt("image path",7,0);
        table.setValueAt("status",8,0);
        table.setValueAt("grade",9,0);


        table.setValueAt(user.department.name,2,1);
        table.setValueAt("-",6,1);
        table.setValueAt("src/resources/Images/defaultProfile.png",7,1);
        table.setValueAt("studying/graduated/withdraw from education",8,1);
        table.setValueAt("BS/MS/PHD",9,1);
        if(student!=null){
            table.setValueAt(student.name, 0, 1);
            table.setValueAt(student.nationalCode, 1, 1);
            table.setValueAt(student.department.name, 2, 1);
            table.setValueAt(student.supervisor!=null?student.supervisor.name:"none", 3, 1);
            table.setValueAt(student.enteringYear+"", 4, 1);
            table.setValueAt(student.email, 5, 1);
            table.setValueAt(student.phoneNumber, 6, 1);
            table.setValueAt(student.image, 7, 1);
            table.setValueAt(student.status.name, 8, 1);
            table.setValueAt(student.grade, 9, 1);
        }

    }
    protected void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        saveButton.addActionListener(e->{
            String message=Controller.editStudent(student,(table.getValueAt(0,1)+"").trim(),(table.getValueAt(1,1)+"").trim()
                    ,(table.getValueAt(2,1)+"").trim(),(table.getValueAt(3,1)+"").trim(),(table.getValueAt(4,1)+"").trim(),(table.getValueAt(5,1)+"").trim(),
                    (table.getValueAt(6,1)+"").trim(),(table.getValueAt(7,1)+"").trim(),(table.getValueAt(8,1)+"").trim(),
                    (table.getValueAt(9,1)+"").trim());
            if(! message.equals("")) {
                if (message.equals("id")) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "The user already exists");
                    MasterLogger.getInstance().log("the user already exists",true,this.getClass());
                }
                else {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "We've got a problem,please check the \"" + message + "\" part and try again");
                    MasterLogger.getInstance().log("invalid input from "+message+" part",true,this.getClass());
                }
            }else if(student==null) {
                MasterLogger.getInstance().log("The student  is registered successfully",false,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "The student is registered successfully");
                new UserMainPanel(user,lastLogin);
            }else{
                JOptionPane.showMessageDialog(MainFrame.mainFrame, "The student is edited successfully");
                MasterLogger.getInstance().log("The student with id \""+student.id+"\" is edited successfully",false,this.getClass());
                new StudentMainPanel(user,lastLogin);
            }
        });
    }
}
