package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * this class is the panel of the frame in the game(GUI)
 */
public class MyPanel extends JPanel {
    private static final int E = 10;
    private Arena arena;
    private directed_weighted_graph ga;
    private gameClient.util.Range2Range _w2f;
    private Image Pokemon = new ImageIcon("./resources/pika3.png").getImage();
    private Image ultra_pokemon = new ImageIcon("./resources/dragon.png").getImage();
    private Image Agent = new ImageIcon("./resources/pokador.png").getImage();
    private Image vertex = new ImageIcon("./resources/vertex.png").getImage();
    private Image beckground = new ImageIcon("./resources/beckground.jpg").getImage();
    /**
     * constructor
     */
    public MyPanel(Arena ar) {
        super();
        this.ga = ar.getGraph();
        this.arena = ar;

      //  this.setBackground(new Color(250, 0, 0));
        updateFrame();



    }

    /**
     * compute the new size of the frame to allow resizeable
     */

    private void updateFrame() {
        Range rx = new Range(20, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 10, 150);
        Range2D frame = new Range2D(rx, ry);
        _w2f = Arena.w2f(ga, frame);


    }

    public void update(Arena ar) {
        arena = ar;
        this.repaint();
    }

    /**
     * this method draw the graph of the game
     * @param g
     */
    private void drawGraph(Graphics g) {

        Iterator<node_data> i = ga.getV().iterator();
        while (i.hasNext()) {
            node_data n = i.next();
            g.setColor(new Color(26, 22, 22));
            drawNode(n, 10, g);
            Iterator<edge_data> itr = ga.getE(n.getKey()).iterator();
            while (itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(new Color(5, 1, 1, 255));
                drawEdge(e, g);
            }
        }
    }

    /**
     * draw the vertex of the graph
     * @param n
     * @param r
     * @param g
     */
    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location location = this._w2f.world2frame(pos);
        g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 10));
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(vertex, (int) location.x() - r, (int) location.y() - r, 2 * r, 2 * r, null);

//        g.fillOval((int) location.x() - r, (int) location.y() - r, 2 * r, 2 * r);
        g.drawString("" + n.getKey(), (int) location.x(), (int) location.y() - 2 * r);
    }

    /**
     * draw the edges of the graph
     * @param e
     * @param g
     */
    private void drawEdge(edge_data e, Graphics g) {

        geo_location s = ga.getNode(e.getSrc()).getLocation();
        geo_location d = ga.getNode(e.getDest()).getLocation();
        geo_location sf = this._w2f.world2frame(s);
        geo_location df = this._w2f.world2frame(d);
        g.drawLine((int) sf.x(), (int) sf.y(), (int) df.x(), (int) df.y());
        g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 10));
        //g.drawString("" + e.getWeight(), ((int) (sf.x() ) + (int) (sf.x() )) / 2, ((int) (df.y() ) + (int) (df.y() )) / 2);

    }

    /**
     * draw the pokemons of this level at the right spot
     * @param g
     */
    private void drawPokemons(Graphics g) {
        List<CL_Pokemon> pokemons = arena.getPokemons();
        Graphics2D g2D = (Graphics2D) g;
        g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 10));
        if (pokemons != null) {
            Iterator<CL_Pokemon> i = pokemons.iterator();

            while (i.hasNext()) {

                CL_Pokemon pokemon = i.next();
                Point3D pos = pokemon.getLocation();
                int r = 13;
                g.setColor(new Color(11, 53, 160, 255));
                //if(pokemon.getType()<0) {g.setColor(new Color(52, 104, 156));}
                if (pos != null) {
                    geo_location location = this._w2f.world2frame(pos);
                    if (pokemon.getType() > 0) {
                        g2D.drawImage(Pokemon, (int) location.x() - r, (int) location.y() - r, 2 * r, 2 * r, null);
                    } else
                        g2D.drawImage(ultra_pokemon, (int) location.x() - r, (int) location.y() - r, 2 * r, 2 * r, null);
//                    g.fillOval((int)location.x()-r, (int)location.y()-r, 2*r, 2*r);
//                    g.drawImage()
                    g.drawString("" + pokemon.getValue(), (int) location.x(), (int) location.y() - 2 * r);
                }
            }
        }
    }
    /**
     * draw the agent of this level at the right spot
     * @param g
     */
    private void drawAgants(Graphics g) {
        List<CL_Agent> agents = arena.getAgents();
        Graphics2D g2D = (Graphics2D) g;
        //	Iterator<OOP_Point3D> itr = rs.iterator();
        g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 10));
        g.setColor(new Color(177, 9, 9));
        int i = 0;
        while (agents != null && i < agents.size()) {
            geo_location location1 = agents.get(i).getLocation();
            int r = 9;

            if (location1 != null) {

                geo_location location = this._w2f.world2frame(location1);
                g2D.drawImage(Agent, (int) location.x() - r, (int) location.y() - r, 2 * r, 2 * r, null);
//                g.fillOval((int)location.x()-r, (int)location.y()-r, 2*r, 2*r);
                g.drawString("" + agents.get(i).getID(), (int) location.x(), (int) location.y() - 2 * r);
            }
            i++;
        }
    }
    /**
     * display the time to end of this game
     * @param g
     */
    private void drawtime(Graphics g) {

        String dt = "time to end :";
        g.setFont(new Font("MV Boli", Font.TRUETYPE_FONT, 20));
        g.setColor(new Color(177, 9, 9));
            g.drawString(dt+arena.getTime()+" sec", 100, 60 + 1 * 20);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = this.getWidth();
        int h = this.getHeight();
//        Image background = Toolkit.getDefaultToolkit().createImage("./resources/beckground.jpg");
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(beckground, 0, 0,w, h,null);
       // g.clearRect(0, 0, w, h);
       // this.setBackground(new Color(59, 61, 161));
        updateFrame();
        drawPokemons(g);
        drawGraph(g);
        drawAgants(g);
        drawtime(g);

    }

}
