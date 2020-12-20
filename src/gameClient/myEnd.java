package gameClient;

import javax.swing.*;
import java.awt.*;

/**
 * this class is the frame of the end of the game(GUI)
 */
public class myEnd extends JFrame {
    private endgame end;
    /**
     * constructor
     * @param game
     */
    public myEnd(String game){
        super();
        end=new endgame(game);
        this.add(end);
        this.setTitle("");
        this.setSize(1000, 700);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //panel.update(ar);
        ImageIcon icon=new ImageIcon("./resources/Poke_Ball.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.cyan);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.setVisible(true);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        end.repaint();
    }
}
