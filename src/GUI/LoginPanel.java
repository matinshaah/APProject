package GUI;

import Controller.Controller;
import Models.*;
import resources.Captcha;
import resources.ImageResource;
import resources.ResourceManager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;


public class LoginPanel extends JPanel {
    final private JLabel username=new JLabel("Username"),password=new JLabel("Password"),captchaName = new JLabel("Enter the number");
    final private JLabel captchaImage= new JLabel();
    final private JTextField nameField= new JTextField(),captchaField = new JTextField();
    final private JPasswordField passwordField= new JPasswordField();
    final private JButton loginButton = new JButton("Log in"),changeCaptcha = new JButton(new ImageIcon(ResourceManager.get(ImageResource.reloadIcon)));
    final private JCheckBox showPass = new JCheckBox();
    private static int captchaCounter =0;
    private String captchaNum;
    public LoginPanel(){
        initPanel();
        initCom();
        align();
        setListeners();
    }

    private void initPanel(){
        MainFrame.mainFrame.getContentPane().removeAll();
        MainFrame.mainFrame.update();
        MainFrame.mainFrame.add(this);
        this.setBounds(0,0,MainFrame.mainFrame.getWidth(),MainFrame.mainFrame.getHeight());
        this.setBackground(Color.blue);
    }

    private void initCom(){
        this.setLayout(null);
        this.add(username);
        this.add(password);
        this.add(nameField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(showPass);
        this.add(captchaName);
        this.add(captchaImage);
        this.add(captchaField);
        this.add(changeCaptcha);
        for (int i = 0; i < 6; i++) {
            new Captcha(i);
        }
        loginButton.setBackground(Color.red);
    }
    private void align(){
        username.setBounds(400,250,200,40);
        password.setBounds(400,300,200,40);
        nameField.setBounds(550,250,400,40);
        passwordField.setBounds(550,300,400,40);
        captchaName.setBounds(400,350,130,40);
        captchaImage.setBounds(775,350,130,40);
        captchaField.setBounds(550,350,200,40);
        changeCaptcha.setBounds(910,350,40,40);
        loginButton.setBounds(700,450,80,50);
        showPass.setBounds(970,320,20,20);
        nameField.setFont(new Font("",Font.PLAIN,20));
        passwordField.setFont(new Font("",Font.PLAIN,20));
        updateCaptcha();
        loginButton.setFocusable(false);
    }

    private void updateCaptcha(){
        captchaCounter = (captchaCounter+1)%6;
        captchaImage.setIcon(new ImageIcon(Captcha.getCaptcha.get(captchaCounter).image));
        captchaNum=Captcha.getCaptcha.get(captchaCounter).string;
        captchaField.setText("");
    }
    private void clearText() {
        nameField.setText("");
        passwordField.setText("");
    }
    private void setListeners(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String password =null;
                try {
                    password = User.hashPassword(passwordField.getText());

                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
                String cap = captchaField.getText();
                if(name.equals("")||password.equals("")||cap.equals("")){
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"Please fill all fields");
                    updateCaptcha();
                }else if(! cap.equals(captchaNum)){
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"The captcha is wrong");
                    updateCaptcha();
                    clearText();
                }else if(Controller.LoginButton(name,password)==null){
                    JOptionPane.showMessageDialog(MainFrame.mainFrame,"Username or password is not valid");
                    updateCaptcha();
                    clearText();
                }else {
                    User user = Controller.LoginButton(name,password);
                    if(user instanceof Student) new StudentMainPanel((Student) user, LocalDateTime.now());
                    else if(user instanceof Teacher) {
                        if(user instanceof EducationViceChar){
                            new EVC_MainPanel((EducationViceChar)user,LocalDateTime.now());
                        }else if(user instanceof DepartmentChair){
                            new DC_MainPanel((DepartmentChair)user,LocalDateTime.now());
                        }else {
                            new TeacherMainPanel( user, LocalDateTime.now());
                        }
                    }
                }
            }
        });
        showPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPass.isSelected()){
                    passwordField.setEchoChar((char) 0);
                }else passwordField.setEchoChar('•');
            }
        });
        changeCaptcha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCaptcha();
            }
        });
    }

}