package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.game_service;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Ex2Test {

    private game_service game = Game_Server_Ex2.getServer(0);
    private Arena arena=new Arena();

    /**
     * this test is to check if the agent place in the right spot at the beginning of the game.
     */
    @Test
    void init() {

        Ex2.init(game);
        List<CL_Pokemon> pokemons=Arena.json2Pokemons(game.getPokemons());
        for (int a = 0; a < pokemons.size(); a++) {
            Arena.updateEdge(pokemons.get(a), Ex2.loadgraph(game.getGraph()));
        }
        double val=0;
        CL_Pokemon p=null;
        for (CL_Pokemon c:pokemons) {
            if(val<c.getValue()){
                val=c.getValue();
                p=c;
            }
        }
        int dest = p.get_edge().getDest();
        if (p.getType() < 0) {
            dest = p.get_edge().getSrc();
        }
        List<CL_Agent> agents=Arena.getAgents(game.getAgents(),Ex2.loadgraph(game.getGraph()));
        assertEquals(dest,agents.get(0).getSrcNode());


    }
    /**
     * this test is to check if the  next move of the agent  is according to the algorithm
     */
    @Test
    void moveagent() {
        Ex2.init(game);
        List<CL_Pokemon> pokemons=Arena.json2Pokemons(game.getPokemons());
        for (int a = 0; a < pokemons.size(); a++) {
            Arena.updateEdge(pokemons.get(a), Ex2.loadgraph(game.getGraph()));
        }
        double val=0;
        CL_Pokemon p=null;
        for (CL_Pokemon c:pokemons) {
            if(val<c.getValue()){
                val=c.getValue();
                p=c;
            }
        }
        int dest = p.get_edge().getDest();
        if (p.getType() > 0) {
            dest = p.get_edge().getSrc();
        }
        List<CL_Agent> agents=Arena.getAgents(game.getAgents(),Ex2.loadgraph(game.getGraph()));
int next=Ex2.nextNode(Ex2.loadgraph(game.getGraph()),agents.get(0),agents.get(0).getSrcNode(),pokemons);
        assertEquals(dest,next);
    }
    /**
     * this test is to check if the program load a graph
     */
    @Test
    void loadgraph() {
        directed_weighted_graph g=Ex2.loadgraph(game.getGraph());

        assertTrue(g instanceof directed_weighted_graph);
    }
}