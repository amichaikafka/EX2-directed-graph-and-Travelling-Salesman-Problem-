package gameClient;
import api.*;

import Server.Game_Server_Ex2;
import api.game_service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArenaTest {
    private Arena arena=new Arena();
  private   game_service game = Game_Server_Ex2.getServer(0);
private directed_weighted_graph g=Ex2.loadgraph(game.getGraph());

    @Test
    void setGraph() {
        arena.setGraph(g);
        assertEquals(g,arena.getGraph());
    }


    @Test
    void get_setPokemons() {
        arena.setPokemons(Arena.json2Pokemons(game.getPokemons()));
        assertEquals(Arena.json2Pokemons(game.getPokemons()),arena.getPokemons());
    }

    @Test
    void can_he_go() {
    }

    @Test
    void i_am_going() {
    }
    @Test
    void getGraph() {
        arena.setGraph(g);
        assertEquals(g,arena.getGraph());
    }


    @Test
    void json2Pokemons() {
        arena.setPokemons(Arena.json2Pokemons(game.getPokemons()));
        assertEquals(Arena.json2Pokemons(game.getPokemons()),arena.getPokemons());
    }

    @Test
    void updateEdge() {
    }


}