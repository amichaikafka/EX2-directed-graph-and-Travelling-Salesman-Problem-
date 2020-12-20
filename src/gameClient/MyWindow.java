package gameClient;

import api.directed_weighted_graph;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * this class is the frame of the  game (GUI)
 */
public class MyWindow extends JFrame  {
    private  MyPanel panel;
    private Arena ar;
    private gameClient.util.Range2Range _w2f;
    /**
     * constructor
     */
    public MyWindow(Arena arena){
        super();

        this.ar=arena;
       // this.updateFrame();
        panel=new MyPanel(ar);
        this.add(panel);
        this.setTitle("");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //panel.update(ar);
        ImageIcon icon=new ImageIcon("./resources/Poke_Ball.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.cyan);
       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);


        this.setVisible(true);
        //panel.update(ar);

    }

    @Override
    public void paint(Graphics g) {
       panel.repaint();
    }

}
