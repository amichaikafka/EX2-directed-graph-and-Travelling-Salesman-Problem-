package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.game_service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex2Test {

    private game_service game = Game_Server_Ex2.getServer(0);

    @Test
    void loadgraph() {
        directed_weighted_graph g=Ex2.loadgraph(game.getGraph());

        assertTrue(g instanceof directed_weighted_graph);
    }
}