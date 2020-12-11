package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_DS;
import api.DWGraph_DS_To_Json;
import api.directed_weighted_graph;
import api.game_service;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ex2 {

    public static void main(String[] args) {
//        Thread client = new Thread(new Ex2_Client());
        game_service game = Game_Server_Ex2.getServer(11);
//        System.out.println(game.toString());
//        System.out.println(game.getGraph());
        directed_weighted_graph g = loadgraph(game.getGraph());
//        System.out.println(game.getPokemons());
//        System.out.println(game.getAgents());


//        System.out.println(g);
       ArrayList<CL_Pokemon> poks=Arena.json2Pokemons(game.getPokemons());
        for (CL_Pokemon c:poks) {
            Arena.updateEdge(c,g);
        }
        System.out.println(game.getPokemons());
        System.out.println(game.getAgents());
        game.addAgent(0);
        System.out.println(game.getAgents());
        game.startGame();
        while (game.isRunning()) {

game.chooseNextEdge(0,1);
game.move();
            System.out.println(game.getAgents());

        }
    }

    public static directed_weighted_graph loadgraph(String json) {


        GsonBuilder Gbuilde = new GsonBuilder();
        Gbuilde.registerTypeAdapter(DWGraph_DS.class, new graph_game_reader());
        Gson gson = Gbuilde.create();

        return gson.fromJson(json, DWGraph_DS.class);
    }

}
