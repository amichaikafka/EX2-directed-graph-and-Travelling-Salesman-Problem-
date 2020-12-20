package gameClient;

import api.directed_weighted_graph;
import api.geo_location;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * this class is the panel of the frame in the start of the game(GUI)
 */

public class openPanel extends JPanel implements ActionListener {

    JButton Button = new JButton();
    private int id=-1;
    private int level=-1;
    private Image beckground = new ImageIcon("./resources/ultrapica.png").getImage();
    /**
     * constructor
     * create the start button
     */

    public openPanel() {
        super();


        Button.setBounds(350, 100, 200, 50);
        Button.setText("Start game!");
        Button.setHorizontalTextPosition(JButton.CENTER);
        Button.setVerticalTextPosition(JButton.BOTTOM);
        Button.setFont(new Font("Comic Sans", Font.BOLD, 15));
        Button.setIconTextGap(-15);
        Button.setForeground(Color.black);
        Button.setBackground(Color.white);
        Button.setBorder(BorderFactory.createEtchedBorder());
        Button.addActionListener(this);
        this.add(Button);


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = this.getWidth();
        int h = this.getHeight();
       // g.clearRect(0, 0, w, h);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(beckground, 0, 0, w, h, null);
        this.setBackground(new Color(59, 61, 161));


    }



    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == Button) {
            while (level < 0 || level > 23) {
                try {
                    level = Integer.parseInt(JOptionPane.showInputDialog(null, "What level do you want to play? ", "pokemon", JOptionPane.WIDTH));
                    if (level < 0 || level > 23) {
                        JOptionPane.showMessageDialog(null, "Worng choice, choose level 0-23 ", "pokemon", JOptionPane.WIDTH);

                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Worng choice, choose level 0-23 ", "pokemon", JOptionPane.WIDTH);
                }
            }
            while (id == -1) {
                try {
                    id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert your id ", "pokemon", JOptionPane.WIDTH));

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid id number ", "pokemon", JOptionPane.WIDTH);
                }
            }


        }
    }
}
