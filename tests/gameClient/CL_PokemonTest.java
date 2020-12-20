package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CL_PokemonTest {
    private game_service game = Game_Server_Ex2.getServer(0);
    private Arena arena=new Arena();
    private List<CL_Pokemon> pokemons=Arena.json2Pokemons(game.getPokemons());


    @Test
    void getLocation() {
        for (CL_Pokemon c:pokemons) {
            assertNotNull(c.getLocation());
        }
    }

    @Test
    void getType() {
        for (CL_Pokemon c:pokemons) {
            assertNotNull(c.getType());
        }

    }

    @Test
    void getValue() {
        for (CL_Pokemon c:pokemons) {
            assertNotNull(c.getValue());
        }
    }


}