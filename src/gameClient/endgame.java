package gameClient;

import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * this class is the panel of the frame in the end of the game (GUI)
 */

public class endgame extends JPanel implements ActionListener {
    private int moves;
    private int grade;
    private int level;
    private JButton Buttonyes = new JButton();


    private Image beckground = new ImageIcon("./resources/newend.jpg").getImage();

    /**
     * constructor
     * @param game
     */
    public endgame(String game) {
        super();
        JSONObject line;
        try {
            line = new JSONObject(game);
            JSONObject gameserver = line.getJSONObject("GameServer");
            moves = gameserver.getInt("moves");
            grade = gameserver.getInt("grade");
            level = gameserver.getInt("game_level");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        Buttonyes.setBounds(350, 700, 300, 70);
        Buttonyes.setLocation(350, 350);
        Buttonyes.setText("Save your score");
        Buttonyes.setHorizontalTextPosition(JButton.CENTER);
        Buttonyes.setVerticalTextPosition(JButton.BOTTOM);
        Buttonyes.setFont(new Font("Comic Sans", Font.BOLD, 30));
        Buttonyes.setIconTextGap(-15);
        Buttonyes.setForeground(Color.black);
        Buttonyes.setBackground(Color.white);
        Buttonyes.setBorder(BorderFactory.createEtchedBorder());
        Buttonyes.addActionListener(this);
        this.add(Buttonyes);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = this.getWidth();
        int h = this.getHeight();
        // g.clearRect(0, 0, w, h);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(beckground, 0, 0, w, h, null);
        g.setColor(Color.cyan);
        g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 50));
        g.drawString("Your score:", (int) (w / 4), (int) (h /5));
        g.setColor(new Color(0, 72, 255));
        g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 40));
        g.drawString("Level:" + level + " Grade:" + grade + " Moves:" + moves, (int) (w /5), (int) (h / 3));



    }

    /**
     * iff the save button pressed save a text file with the score
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Buttonyes) {

            String save = JOptionPane.showInputDialog(null, "Insert your name", "pokemon", JOptionPane.WIDTH);
            String savefile = save + ".txt";

            try {
                FileWriter myWriter = new FileWriter(savefile);
                myWriter.write(save + ": level: " + level + " grade: " + grade + " moves: " + moves);
                myWriter.close();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }



        }

    }
}
