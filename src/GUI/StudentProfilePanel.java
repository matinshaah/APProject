package GUI;

import Models.Student;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class StudentProfilePanel extends StudentEditPanel {
    JButton changePass;
    JMenu color;
    JMenuItem blue,green,red,pink;
    public StudentProfilePanel(User user, LocalDateTime loginTime, Student student) {
        super(user, loginTime,student);
    }

    @Override
    protected void initCom() {
        super.initCom();
        changePass = new JButton("change password");
        color = new JMenu("color");
        color.setBorder(BorderFactory.createLineBorder(Color.black));
        color.setOpaque(true);
        blue = new JMenuItem("blue");
        blue.setForeground(Color.blue);
        green = new JMenuItem("green");
        green.setForeground(Color.green);
        red = new JMenuItem("red");
        red.setForeground(Color.red);
        pink = new JMenuItem("pink");
        pink.setForeground(Color.pink);
    }

    @Override
    protected void align() {
        super.align();
        this.add(changePass);
        changePass.setBounds(800,100,150,30);
        this.add(color);
        color.setBounds(1100,100,80,30);
        color.add(blue);
        color.add(pink);
        color.add(green);
        color.add(red);
    }

    @Override
    protected void initTable() {
        table = new JTable();
        table.setBounds(275,150,800,450);
        table.setBackground(user.color);
        table.setFont(new Font("",Font.BOLD,20));
        table.setBorder(BorderFactory.createLineBorder(Color.black));

        DefaultTableModel tableModel = new DefaultTableModel(10,2) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0) return false;
                else return !(row==2||row==3||row==4||row==8||row==9);
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
    @Override
    protected void setListeners(){
        super.setListeners();
        changePass.addActionListener(e-> new ChangePasswordPanel(user,LocalDateTime.now()));
        blue.addActionListener(e->{
            user.color=Color.blue;
            new StudentProfilePanel(user,lastLogin,(Student) user);
        });
        red.addActionListener(e->{
            user.color=Color.red;
            new StudentProfilePanel(user,lastLogin,(Student) user);
        });
        green.addActionListener(e->{
            user.color=Color.green;
            new StudentProfilePanel(user,lastLogin,(Student) user);
        });
        pink.addActionListener(e->{
            user.color=Color.pink;
            new StudentProfilePanel(user,lastLogin,(Student) user);
        });
    }
}
