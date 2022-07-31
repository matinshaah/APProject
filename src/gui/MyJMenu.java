package gui;

import javax.swing.*;
import java.awt.*;

public class MyJMenu extends JMenu {
    final static Font font = new Font("",Font.PLAIN,20);
    MyJMenu(String text){
        super(text);
        this.setFont(font);
    }
}
