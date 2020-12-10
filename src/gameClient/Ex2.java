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
    public static void main (String[]args){
       game_service game = Game_Server_Ex2.getServer(23);
//        System.out.println(game.getGraph());
//
//        directed_weighted_graph g=loadgraph(game.getGraph());
//        System.out.println(g);
        System.out.println(game.getPokemons());
        List<CL_Pokemon> c=loadpokemons(game.getPokemons());
        System.out.println(c);
    }

    public static directed_weighted_graph loadgraph(String json) {


            GsonBuilder Gbuilde = new GsonBuilder();
            Gbuilde.registerTypeAdapter(DWGraph_DS.class, new graph_game_reader());
            Gson gson = Gbuilde.create();

            return gson.fromJson(json,DWGraph_DS.class);
    }
    public static List<CL_Pokemon> loadpokemons(String json) {

            GsonBuilder Gbuilde = new GsonBuilder();
            Gbuilde.registerTypeAdapter(List.class, new pokemons_reader());
            Gson gson = Gbuilde.create();
            return gson.fromJson(json,List.class);


    }
}
