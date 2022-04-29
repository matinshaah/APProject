package GUI;

import Controller.Controller;
import Models.Teacher;
import Models.User;
import resources.ImageResource;
import resources.MasterLogger;
import resources.ResourceManager;
import resources.SavingData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TeacherEditPanel extends UserMainPanel{
    Teacher teacher;
    JButton saveButton,delete,promote;
    JTable table;
    JLabel image;
    public TeacherEditPanel(User user, LocalDateTime loginTime, Teacher teacher) {
        super(user, loginTime);
        this.teacher=teacher;
        setListeners();
        initTable();
        setTable();
        this.add(table);
        if(teacher!=null) {
            Image imageIcon = new ImageIcon(teacher.image).getImage().getScaledInstance(96,96,Image.SCALE_DEFAULT);
            image.setIcon(new ImageIcon(imageIcon));
            if(teacher!=user){
            this.add(delete);
            delete.setBounds(420,605,120,30);
            promote.setBounds(600,605,140,30);
            }
        }else {
            image.setIcon(new ImageIcon(ResourceManager.get(ImageResource.defaultProfile)));
        }
    }
    @Override
    protected void initCom() {
        saveButton = new JButton("save");
        delete = new JButton("delete Teacher");
        promote = new JButton("promote to EVC");
        image = new JLabel();
        super.initCom();
    }
    @Override
    protected void align() {
        super.align();
        this.add(saveButton);
        this.add(image);
        this.add(promote);
        saveButton.setBounds(820,605,80,30);
        image.setBounds(1200,500,96,96);
    }
    private void initTable(){
        MasterLogger.getInstance().log("table is initialized",false,this.getClass());
        table = new JTable();
        table.setBounds(275,150,800,440);
        table.setBackground(user.color);
        table.setFont(new Font("",Font.BOLD,20));
        table.setBorder(BorderFactory.createLineBorder(Color.black));

        DefaultTableModel tableModel = new DefaultTableModel(8,2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0) return false;
                else if(row==2||row==3){
                    return false;
                }else return teacher == null || teacher.isDC || teacher != user || row != 4;
            }
        };
        table.setModel(tableModel);

        table.setRowHeight(55);

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
        table.setValueAt("room number",3,0);
        table.setValueAt("degree",4,0);
        table.setValueAt("email",5,0);
        table.setValueAt("phone number",6,0);
        table.setValueAt("image path",7,0);

        table.setValueAt(user.department.name,2,1);
        table.setValueAt("-",3,1);
        table.setValueAt("assistant professor/professor/associate professor",4,1);
        table.setValueAt("src/resources/Images/defaultProfile.png",7,1);
        if(teacher!=null){
            table.setValueAt(teacher.name, 0, 1);
            table.setValueAt(teacher.nationalCode, 1, 1);
            table.setValueAt(teacher.department.name, 2, 1);
            table.setValueAt(teacher.roomNumber+"", 3, 1);
            table.setValueAt(teacher.degree.name, 4, 1);
            table.setValueAt(teacher.email, 5, 1);
            table.setValueAt(teacher.phoneNumber, 6, 1);
            table.setValueAt(teacher.image, 7, 1);
        }
    }
    protected void setListeners(){
        MasterLogger.getInstance().log("listeners are set",false,this.getClass());
        delete.addActionListener(e ->{
            if(Controller.removeTeacher(teacher)){
                ArrayList<String> str = new ArrayList<>();
                str.add("removeTeacher");
                str.add("id:"+teacher.id);
                SavingData.addToFile(str);
                MasterLogger.getInstance().log("the teacher with id "+teacher.id+" is  deleted",false,this.getClass());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"The teacher deleted successfully");
                new DCTeacherPanel(user,lastLogin);
            }
            else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"You can't delete yourself");
                MasterLogger.getInstance().log("deleting him/herself",true,this.getClass());
            }
        });
        saveButton.addActionListener(e ->{
            String message=Controller.editTeacher(teacher,(table.getValueAt(0,1)+"").trim(),(table.getValueAt(1,1)+"").trim()
            ,(table.getValueAt(2,1)+"").trim(),(table.getValueAt(4,1)+"").trim(),(table.getValueAt(5,1)+"").trim(),
                    (table.getValueAt(6,1)+"").trim(),(table.getValueAt(7,1)+"").trim());
            if(! message.equals("")){
                if(message.equals("id"))
                {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"The user already exists");
                    MasterLogger.getInstance().log("user already exists",true,this.getClass());
                }
                else {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"We've got a problem,please check the \""+message+"\" part and try again");
                    MasterLogger.getInstance().log("invalid input in "+message+" part",true,this.getClass());
                }
            }else {
                if(teacher==null) {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "The teacher is registered successfully");
                    MasterLogger.getInstance().log("new teacher",false,this.getClass());
                }else {
                    JOptionPane.showMessageDialog(MainFrame.mainFrame, "The teacher is edited successfully");
                    MasterLogger.getInstance().log("The teacher with id "+teacher.id+" is edited successfully",false,this.getClass());
                }
                    if(((Teacher)user).isDC) new DCTeacherPanel(user, lastLogin);
                    else  new UserMainPanel(user, lastLogin);
            }
        });
        promote.addActionListener(e -> {
            int state = Controller.setEVC(teacher);
            if(state==0){
                ArrayList<String> str = new ArrayList<>();
                str.add("setEVC");
                str.add("id:"+teacher.id);
                SavingData.addToFile(str);
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"The teacher is promoted successfully");
                MasterLogger.getInstance().log("The teacher with id "+teacher.id+" is promoted successfully",false,this.getClass());

                new DCTeacherPanel(user,lastLogin);
            }else if(state==1){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"You can't promote yourself to edu vice chair");
                MasterLogger.getInstance().log("promoting him/herself",true,this.getClass());
            }else {
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"The teacher has been already edu vice chair");
                MasterLogger.getInstance().log("the teacher has benn already promoted",true,this.getClass());
            }
        });
    }
}
