package gameClient;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {
    private  MyPanel panel;
    private Arena ar;
    public MyWindow(Arena arena){
        super();
        this.ar=arena;
        panel=new MyPanel(ar);
    }
    public void look(){
        this.add(panel);
        this.setTitle("");
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        int h=d.height;
        int w=d.width;
        this.setSize(w,h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.update(ar);
        this.setVisible(true);
    //    panel.update(ar);
        ImageIcon icon=new ImageIcon("./resources/t.jpg");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.cyan);

    }
//    public static void main(String[]args){
//        MyWindow w=new MyWindow();
//        w.look();
//    }

}
