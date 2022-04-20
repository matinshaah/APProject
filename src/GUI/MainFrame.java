package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static  MainFrame mainFrame;
    public static int WIDTH=1400,HEIGHT=800;
    public MainFrame(){
        super("Edu");
        init();
        update();
    }

    private void init(){
        this.setVisible(true);
//        this.setResizable(false);
        this.setSize(new Dimension(1400, 800));
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void update(){
        repaint();
        revalidate();
    }

}
