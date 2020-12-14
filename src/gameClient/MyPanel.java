package gameClient;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class MyPanel extends JPanel {
private static final  int E=10;
    private LinkedList<geo_location> pok = new LinkedList<>();
    private LinkedList<geo_location> ag = new LinkedList<>();
    private LinkedList<geo_location> v = new LinkedList<>();
    private directed_weighted_graph ga;

    public MyPanel(Arena ar) {
        super();
        this.ga = ar.getGraph();
        List<CL_Pokemon> p=ar.getPokemons();
        List<CL_Agent> a=ar.getAgents();
        this.setBackground(new Color(96, 55, 170));
        for (CL_Pokemon c : p) {
            pok.add(c.getLocation());

        }
        if(a!=null){
        for (CL_Agent c : a) {
            ag.add(c.getLocation());

        }
        }
        for (node_data n : ga.getV()) {
            v.add(n.getLocation());

        }
    }
    public void update(Arena ar){
        pok=new LinkedList<geo_location>();
        ag=new LinkedList<geo_location>();
        List<CL_Pokemon> p=ar.getPokemons();
        List<CL_Agent> a=ar.getAgents();

        for (CL_Pokemon c : p) {
            pok.add(c.getLocation());

        }
        if(a!=null) {
            for (CL_Agent c : a) {
                ag.add(c.getLocation());

            }
        }
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(154, 22, 22));
        for (geo_location l : v) {
            g.setColor(new Color(154, 22, 22));
            g.fillOval((int)( l.x()*E), (int)( l.y()*E), 20, 20);
        }
        for (node_data n : ga.getV()) {
            for (edge_data e : ga.getE(n.getKey())) {
                node_data t = ga.getNode(e.getDest());
                geo_location l1 = t.getLocation();
                geo_location l2 = n.getLocation();
                String ws = "" + e.getWeight();
                g.drawLine((int) (l1.x()*E), (int) (l1.y()*E), (int)( l2.x()*E), (int) (l2.y()*E));
                g.drawString(ws,((int) (l1.x()*E)+(int) (l2.x()*E))/2,((int)( l1.y()*E)+(int) (l2.y()*E))/2);
            }

        }
        for (geo_location l : pok) {
            g.setColor(new Color(154, 117, 22));
            g.fillOval((int) (l.x()*E), (int)( l.y()*E), 20, 20);
        }
        for (geo_location l : ag) {
            g.setColor(new Color(22, 75, 154));
            g.fillOval((int)( l.x()*E), (int) (l.y()*E), 20, 20);
        }
    }
}
