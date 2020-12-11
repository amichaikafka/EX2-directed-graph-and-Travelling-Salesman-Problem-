package gameClient;

import api.*;

import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class where implements Runnable {
    private game_service game;
    private Arena a = new Arena();
    private dw_graph_algorithms ga;
    private int agentsize;

    public where(game_service game) {
        ga = new DWGraph_Algo();
        this.game = game;
        directed_weighted_graph g = Ex2.loadgraph(game.getGraph());
        ga.init(g);
        ArrayList<CL_Pokemon> poks = Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon c : poks) {
            Arena.updateEdge(c, g);
        }
        List<CL_Agent> ag = Arena.getAgents(game.getAgents(), g);
        a.setGraph(g);
        a.setPokemons(poks);
        a.setAgents(ag);
        JSONObject json;
        try {
            json = new JSONObject(game.toString());
            JSONObject gamedata = json.getJSONObject("GameServer");
            this.agentsize = gamedata.getInt("agents");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void prepare_in_advance() {

        List<CL_Pokemon> p = this.a.getPokemons();

        for (int i = 0; i < agentsize; i++) {
            CL_Pokemon c = p.get(i);
            edge_data e = c.get_edge();
            int start_node = e.getSrc();
            if (c.getType() > 0) {
                start_node = e.getDest();
            }
            game.addAgent(start_node);
        }
    }

    public void next_step(CL_Pokemon pokemon) {
        List<CL_Agent> agents = Arena.getAgents(game.getAgents(), a.getGraph());
        int dest;
        if (pokemon.getType() < 0) {
            dest = pokemon.get_edge().getDest();
        } else {
            dest = pokemon.get_edge().getSrc();
        }
        List<node_data> path = null;
        double closestime = Double.MAX_VALUE;
        int closest = -1;
        CL_Agent ca = null;
        for (CL_Agent ag : agents) {
            if (ag.getNextNode() == -1) {
                int src = ag.getSrcNode();
                edge_data e = pokemon.get_edge();
                double t = 0;

                t = ga.shortestPathDist(src, dest) / ag.getSpeed();

                if (t >= 0 && closestime > t) {
                    closestime = t;
                    closest = ag.getID();
                    ca = ag;
                }
            }
        }
        if(ca!=null) {
            double[] arr = next_step(ca);
            if (arr[1] < closestime) {
                path = ga.shortestPath(ca.getSrcNode(), (int) arr[0]);
            } else {
                path = ga.shortestPath(ca.getSrcNode(), dest);
            }
            if (path != null) {

                game.chooseNextEdge(closest, path.get(1).getKey());
                System.out.println("Agent: "+ca.getID()+", val: "+ca.getValue()+"   turned to node: "+ path.get(1).getKey());

            }
        }
    }


    public double[] next_step(CL_Agent agent) {
        ArrayList<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        int dest = -1;
        double[] arr = new double[2];
        List<node_data> path = null;
        double closestime = Double.MAX_VALUE;
        int fineldest = -1;
        CL_Pokemon po = null;
        for (CL_Pokemon c : pokemons) {
            if (c.getType() < 0) {
                dest = c.get_edge().getDest();
            } else {
                dest = c.get_edge().getSrc();
            }
            double t = ga.shortestPathDist(agent.getSrcNode(), dest);
            if (t >= 0 && closestime > t) {
                closestime = t;
                fineldest = dest;
            }
        }
        arr[0] = 1.0 * fineldest;
        arr[1] = closestime;
        return arr;

    }


    @Override
    public void run() {

    }
}
