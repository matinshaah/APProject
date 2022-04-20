package GUI;

import javax.swing.*;
import java.awt.*;

public class MyMenuItem extends JMenuItem {
    final static Font font = new Font("",Font.PLAIN,20);
    MyMenuItem(String text){
        super(text);
        this.setFont(font);
    }
}
