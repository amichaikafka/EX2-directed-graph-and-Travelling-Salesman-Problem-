package gameClient;

import api.*;

import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Where implements Runnable {
    private game_service game;
    private Arena a;
private MyFrame _win;
    private int agentsize;

    private static int SLEEP=10;


    public   void moveAgants(game_service game, directed_weighted_graph g) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, g);
        this.a.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        //System.out.println(ffs.get(1).get_edge());
        this.a.setPokemons(ffs);
        for (int i = 0; i < log.size(); i++) {
            CL_Agent agent = log.get(i);
            int id = agent.getID();
            int dest = agent.getNextNode();
            // System.out.println(dest);
            int src = agent.getSrcNode();
            double v = agent.getValue();
            if (dest == -1) {
                dest = nextNode(g, agent, src, this.a.getPokemons(), this.a.getAgents(), game);
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
    private  int nextNode(directed_weighted_graph g, CL_Agent agent, int src, List<CL_Pokemon> c, List<CL_Agent> ag, game_service game) {
        int ans = -1;
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g);
        for (int a = 0; a < c.size(); a++) {
            Arena.updateEdge(c.get(a), g);
//					System.out.println(cl_fs.get(a).get_edge().getDest());
        }
        int count = 0;
        int fdest = -1;
        double mint = Double.MAX_VALUE;
        CL_Pokemon ca = null;
        for (CL_Pokemon pokemon : c) {
            if (a.try_again(agent, pokemon)) {
                if (a.can_he_go(agent.getID(), pokemon)) {
                    // pokemon.setWilleat(true);
                    int dest = pokemon.get_edge().getDest();

                    if (pokemon.getType() > 0) {
                        dest = pokemon.get_edge().getSrc();
                        //   dest=Math.max(pokemon.get_edge().getDest(),pokemon.get_edge().getSrc());
                    }
                    if (!a.pokemon_is_out(agent.getID(), dest)) {

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
            }
        }

        if (fdest != -1) {
            agent.set_curr_fruit(ca);
            a.setCount_try_eat(ca, agent);
            a.can_i_eat(agent);
            a.i_am_going(agent.getID(), ca.get_edge());

            a.add_to_out(agent.getID(), fdest);

            List<node_data> p = ga.shortestPath(src, fdest);


            ans = p.get(1).getKey();
            agent.setNextNode(p.get(1).getKey());

        }else {
            double min= Double.MAX_VALUE;

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



    @Override
    public void run() {


    }
}
