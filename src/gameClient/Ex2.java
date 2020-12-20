package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.*;

/**
 * This class represents the  "Client-Game" main class
 * which uses the "server for moving the "Agents".
 */
public class Ex2 implements Runnable {
    private static MyWindow window;
    private static Arena arna;
    private static int level = -312486699;
    private static int id = -235;
    public static myOpen p;

    private static int SLEEP = 20;

    public static void main(String[] args) {
        Thread client = new Thread(new Ex2());
        if (args.length == 0) {

            p = new myOpen();
            while (level == -312486699 || id == -235) {
                id = p.getPanel().getId();
                level = p.getPanel().getLevel();
                p.repaint();
            }
            p.dispose();
        } else {
            try {
                id = Integer.parseInt(args[0]);
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                p = new myOpen();
                while (level == -312486699 || id == -235) {
                    id = p.getPanel().getId();
                    level = p.getPanel().getLevel();
                    p.repaint();
                }
                p.dispose();
            }

        }

        client.start();
        SimplePlayer player = new SimplePlayer("./resources/dragon_ball_z.mp3");
        Thread playerThread = new Thread(player);
        playerThread.start();


    }


    @Override
    public void run() {
        //int level = 10;

        try {


            game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
            game.login(id);
            String g = game.getGraph();
            directed_weighted_graph gg = Ex2.loadgraph(g);
            System.out.println(gg);
            init(game);
            game.startGame();
            window.setTitle("Level:" + level + " " + game.toString());
            int ind = 10;
            long dt = 100;

            while (game.isRunning()) {
                arna.updateTime(game.timeToEnd());
                Runnable nextstep = new Runnable() {
                    @Override
                    public void run() {
                        moveAgants(game, gg);
                    }

                };

                Thread t = new Thread(nextstep);
                t.start();
                try {
                    if (ind % 1 == 0) {
                        window.repaint();
                    }
                    Thread.sleep(dt);
                    ind++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String res = game.toString();

            System.out.println(res);
            window.dispose();
            new myEnd(res);
        } catch (Exception e) {
            System.out.println("worng level");
            JOptionPane.showMessageDialog(null, "Worng level", "pokemon", JOptionPane.WIDTH);
            System.exit(0);
        }
    }

    /**
     * this method decide the next move of all the agent
     *
     * @param game
     * @param g
     */

    public  void moveAgants(game_service game, directed_weighted_graph g) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, g);
        this.arna.setAgents(log);

        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        //System.out.println(ffs.get(1).get_edge());
        this.arna.setPokemons(ffs);
        for (int i = 0; i < log.size(); i++) {
            CL_Agent agent = log.get(i);
            int id = agent.getID();
            int dest = agent.getNextNode();
            // System.out.println(dest);
            int src = agent.getSrcNode();
            double v = agent.getValue();
            if (dest == -1) {
                dest = nextNode(g, agent, src, this.arna.getPokemons());
                if (dest != -1) {
                    //dest = nextNode(gg,src);

                    game.chooseNextEdge(agent.getID(), dest);
                    System.out.println("Agent: " + id + " src:" + src + ", val: " + v + "   turned to node: " + dest);
                    try {
                        Thread.sleep(SLEEP);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    /**
     * this method compute the best next move for the agent
     *
     * @param g
     * @param agent
     * @param src
     * @param c
     * @return next node to move
     */
    public static int nextNode(directed_weighted_graph g, CL_Agent agent, int src, List<CL_Pokemon> c) {
        int ans = -1;
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g);
        for (int a = 0; a < c.size(); a++) {
            Arena.updateEdge(c.get(a), g);
        }
        int fdest = -1;
        double mint = Double.MAX_VALUE;
        CL_Pokemon ca = null;
        for (CL_Pokemon pokemon : c) {
            if (arna.can_he_go(agent.getID(), pokemon)) {
                int dest = pokemon.get_edge().getDest();
                if (pokemon.getType() > 0) {
                    dest = pokemon.get_edge().getSrc();
                }
                double t = ga.shortestPathDist(src, dest) / agent.getSpeed() / pokemon.getValue();
                if (t >= 0 && t < mint) {
                    ca = pokemon;
                    mint = t;
                    fdest = dest;
                    if (t == 0) {
                        fdest = pokemon.get_edge().getSrc();
                        if (pokemon.getType() > 0) {
                            fdest = pokemon.get_edge().getDest();
                        }
                    }
                }

            }
        }

        if (fdest != -1) {
            agent.set_curr_fruit(ca);
            arna.i_am_going(agent.getID(), ca.get_edge());
            List<node_data> p = ga.shortestPath(src, fdest);
            ans = p.get(1).getKey();
            agent.setNextNode(p.get(1).getKey());
        } else {
            double min = Double.MAX_VALUE;
            Iterator<edge_data> edge = g.getE(agent.getSrcNode()).iterator();
            while (edge.hasNext()) {
                edge_data t = edge.next();
                if (min > g.getNode(agent.getSrcNode()).getLocation().distance(g.getNode(t.getDest()).getLocation())) {
                    min = g.getNode(agent.getSrcNode()).getLocation().distance(g.getNode(t.getDest()).getLocation());
                    ans = t.getDest();

                }

            }
        }
        return ans;
    }

    /**
     * this method load the graph of the game from the server
     *
     * @param json
     * @return the game's graphg
     */
    public static directed_weighted_graph loadgraph(String json) {


        GsonBuilder Gbuilde = new GsonBuilder();
        Gbuilde.registerTypeAdapter(DWGraph_DS.class, new graph_game_reader());
        Gson gson = Gbuilde.create();

        return gson.fromJson(json, DWGraph_DS.class);
    }

    /**
     * init the arena of the game and plce the agent at the right spot
     *
     * @param game
     */
    public static void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = loadgraph(g);
        arna = new Arena();
        arna.setGraph(gg);
        arna.setPokemons(Arena.json2Pokemons(fs));
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject json = line.getJSONObject("GameServer");
            int num_agent = json.getInt("agents");
            Comparator<CL_Pokemon> compi = new Comparator<CL_Pokemon>() {

                @Override
                public int compare(CL_Pokemon o1, CL_Pokemon o2) {
                    return Double.compare(o2.getValue(), o1.getValue());
                }
            };
            PriorityQueue<CL_Pokemon> pokemons_val = new PriorityQueue<CL_Pokemon>(compi);
            ArrayList<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < pokemons.size(); a++) {
                Arena.updateEdge(pokemons.get(a), gg);
                pokemons_val.add(pokemons.get(a));
            }
            while (!pokemons_val.isEmpty() && num_agent != 0) {
                CL_Pokemon c = pokemons_val.poll();
                System.out.println(pokemons_val);
                int dest = c.get_edge().getDest();
                if (c.getType() < 0) {
                    dest = c.get_edge().getSrc();
                }
                game.addAgent(dest);
                num_agent--;
            }
            arna.setAgents(Arena.getAgents(game.getAgents(), gg));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        window = new MyWindow(arna);

    }

}
