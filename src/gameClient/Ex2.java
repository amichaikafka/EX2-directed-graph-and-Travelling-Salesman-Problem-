package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable{
    private static MyWindow window;
    private static Arena arna;
   // private static Where where;
    private static int SLEEP = 20;

    public static void main(String[] args) {
      Thread client = new Thread(new Ex2());
//        game_service game = Game_Server_Ex2.getServer(11);
//
//        directed_weighted_graph g = loadgraph(game.getGraph());
        client.start();
        SimplePlayer player = new SimplePlayer("./resources/dragon_ball_z.mp3");
        Thread playerThread = new Thread(player);
        playerThread.start();


    }




    @Override
    public void run() {
        int level = 11;

        game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
        //	int id = 999;
//        	game.login(id);
        String g = game.getGraph();
        directed_weighted_graph gg = Ex2.loadgraph(g);
        System.out.println(gg);
        init(game);
        game.startGame();
        window.setTitle("Level:"+level+" "+ game.toString());
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
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
        System.exit(0);
    }


    public   void moveAgants(game_service game, directed_weighted_graph g) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, g);
        this.arna.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
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
                dest = nextNode(g, agent, src, this.arna.getPokemons(), this.arna.getAgents(), game);
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
            if (arna.try_again(agent, pokemon)) {
                if (arna.can_he_go(agent.getID(), pokemon)) {
                    // pokemon.setWilleat(true);
                    int dest = pokemon.get_edge().getDest();

                    if (pokemon.getType() > 0) {
                        dest = pokemon.get_edge().getSrc();
                        //   dest=Math.max(pokemon.get_edge().getDest(),pokemon.get_edge().getSrc());
                    }
                    if (!arna.pokemon_is_out(agent.getID(), dest)) {

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
            arna.setCount_try_eat(ca, agent);
            arna.can_i_eat(agent);
            arna.i_am_going(agent.getID(), ca.get_edge());

            arna.add_to_out(agent.getID(), fdest);

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
    public static directed_weighted_graph loadgraph(String json) {


        GsonBuilder Gbuilde = new GsonBuilder();
        Gbuilde.registerTypeAdapter(DWGraph_DS.class, new graph_game_reader());
        Gson gson = Gbuilde.create();

        return gson.fromJson(json, DWGraph_DS.class);
    }
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        // directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        directed_weighted_graph gg=loadgraph(g);
        //gg.init(g);
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
            while (!pokemons_val.isEmpty()&&num_agent!=0) {
                CL_Pokemon c = pokemons_val.poll();
                System.out.println(pokemons_val);
                int nn = c.get_edge().getDest();
                if (c.getType() < 0) {
                    nn = c.get_edge().getSrc();
                }
                game.addAgent(nn);
                num_agent--;
            }
            arna.setAgents(Arena.getAgents(game.getAgents(), gg));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        window = new MyWindow(arna);
        // window.setSize(1000, 700);
        //_win.update(arna);
        // _win.show();

    }

}
