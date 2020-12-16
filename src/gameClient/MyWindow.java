package gameClient;

import api.directed_weighted_graph;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyWindow extends JFrame {
    private  MyPanel panel;
    private Arena ar;
    private gameClient.util.Range2Range _w2f;
    public MyWindow(Arena arena){
        super();

        this.ar=arena;
        this.updateFrame();
        panel=new MyPanel(ar);
        this.add(panel);
        this.setTitle("");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //panel.update(ar);
        ImageIcon icon=new ImageIcon("./resources/Poke_Ball.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.cyan);


        this.setVisible(true);
        //panel.update(ar);

    }

    @Override
    public void paint(Graphics g) {
//        int w = this.getWidth();
//        int h = this.getHeight();
//        g.clearRect(0, 0, w, h);
        //updateFrame();
       panel.repaint();
    }
    private void updateFrame() {
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-10,150);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }

//    public void look(){
//        this.add(panel);
//        this.setTitle("");
//        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
//        int h=d.height;
//        int w=d.width;
//        this.setSize(w,h);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        panel.update(ar);
//        this.setVisible(true);
//        panel.update(ar);
//        ImageIcon icon=new ImageIcon("./resources/t.jpg");
//        this.setIconImage(icon.getImage());
//        this.getContentPane().setBackground(Color.cyan);
//
//    }
//    public static void main(String[]args){
//        MyWindow w=new MyWindow();
//        w.look();
//    }

}
