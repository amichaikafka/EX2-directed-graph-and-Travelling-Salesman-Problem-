package gameClient;
import api.*;

import api.directed_weighted_graph;
import api.game_service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class where implements Runnable {
    private game_service game;
private Arena a=new Arena();
private dw_graph_algorithms ga;


    public where(game_service game) {
        ga=new DWGraph_Algo();
        this.game=game;
        directed_weighted_graph g = Ex2.loadgraph(game.getGraph());
        ga.init(g);
        ArrayList<CL_Pokemon> poks=Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon c:poks) {
            Arena.updateEdge(c,g);
        }
        List<CL_Agent> ag=Arena.getAgents(game.getAgents(),g);
        a.setGraph(g);
        a.setPokemons(poks);
        a.setAgents(ag);

    }
    public void prepare_in_advance(){
        List<CL_Pokemon >p=this.a.getPokemons();
        int size_a=p.size()/2;
        int count=0;
        for (CL_Pokemon c:p) {
            edge_data e=c.get_edge();
            int start_node=e.getSrc();
            game.addAgent(start_node);
            count++;
            if(count==size_a){
                break;
            }

        }

    }
    public void next_step(CL_Pokemon pokemon){
        List<CL_Agent> agents=Arena.getAgents(game.getAgents(), a.getGraph());
        int dest =pokemon.get_edge().getSrc();
        List<node_data> path=null;
        double closestime=Double.MAX_VALUE;
        int closest=-1;
        CL_Agent ca=null;
        for (CL_Agent ag:agents) {
            if(!ag.isMoving()){
                int src=ag.getSrcNode();
                double t=ga.shortestPathDist(src,dest)/ag.getSpeed();
                if(closestime>t){
                    closestime=t;
                    closest=ag.getID();
                     path=ga.shortestPath(src,dest);
                     ca=ag;
                }
            }
        }
        if(path!=null) {
            int i=0;

            while (i<path.size()) {
                if(!ca.isMoving()) {
                    game.chooseNextEdge(closest, path.get(i).getKey());
                    game.move();
                    i++;
                }
            }
        }
    }

    @Override
    public void run() {

    }
}
