package gameClient;

import Server.Game_Server_Ex2;
import api.directed_weighted_graph;
import api.edge_data;
import api.game_service;
import api.node_data;
import org.json.JSONException;
import org.json.JSONObject;
import api.*;

import java.util.*;

public class Ex2_Client implements Runnable {
    private static MyFrame _win;
    private static Arena _ar;
    private static int SLEEP = 40;


    public static void main(String[] a) {
        Thread client = new Thread(new Ex2_Client());
        client.start();
    }

    @Override
    public void run() {
        int scenario_num = 10;
        try {
            game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games


            //	int id = 999;
//        	game.login(id);
            String g = game.getGraph();
            String pks = game.getPokemons();
//		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
            directed_weighted_graph gg = Ex2.loadgraph(g);
            System.out.println(gg);
            init(game);


            game.startGame();
            System.out.println(game.timeToEnd());
            _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
            int ind = 0;
            long dt = 100;
            while (game.isRunning()) {
                Runnable nextstep = new Runnable() {
                    @Override
                    public void run() {
                        moveAgants(game, gg);
                    }

                };
//            Runnable nextstep = new Runnable() {
//                @Override
//                public void run() {
//                   while (noteat(Arena.json2Pokemons(game.getPokemons()))==_ar.getAgents().size()) {
//                        try {
//                            System.out.println("uhu");
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                       game.move();
//                    }
//                    moveAgants(game, gg);
//
//
//                }
//            };
//
//            Runnable check = new Runnable() {
//                @Override
//                public void run() {
//                    while (noteat(Arena.getAgents(game.getAgents(), gg))) {
//                        try {
//                            System.out.println("hi2");
//                            wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                //}
//                   if (noteat(Arena.json2Pokemons(game.getPokemons()))!=_ar.getAgents().size()) {
//                       System.out.println("hi2");
//                       notifyAll();
//                   }
//                }
//            };

//            for(int i=0;i<_ar.getAgents().size();i++) {
//                Thread t = new Thread(nextstep);
//                arr[i]=t;
//
////            Thread t_check = new Thread(check);
//            }
//            for(int i=0;i<arr.length;i++) {
//             arr[i].start();

//            Thread t_check = new Thread(check);
                //}
                Thread t = new Thread(nextstep);
                t.start();

//            t_check.start();
                // if(noteat(Arena.getAgents(game.move(), gg))) {
//                moveAgants(game, gg);
                // }
                try {
                    if (ind % 5 == 0) {
                        _win.repaint();
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
        } catch (Exception e) {
            System.out.println("worng level");
            System.exit(0);
        }
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     *
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();

        // System.out.println(lg);
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs = game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        //System.out.println(ffs.get(1).get_edge());
        _ar.setPokemons(ffs);
        for (int i = 0; i < log.size(); i++) {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            // System.out.println(dest);
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if (dest == -1) {
                dest = nextNode(gg, ag, src, _ar.getPokemons(), _ar.getAgents(), game);
                if (dest != -1) {
                    //dest = nextNode(gg,src);

                    game.chooseNextEdge(ag.getID(), dest);
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

    public synchronized int noteat(List<CL_Pokemon> pokemons) {
        // boolean flag = false;
        int count = 0;
        for (CL_Pokemon p : pokemons) {
            if (p.isWilleat()) {
                count++;
                System.out.println(count);
            }
            System.out.println(count);
        }
        return count;
    }

    /**
     * a very simple random walk implementation!
     *
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {

        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int) (Math.random() * s);
        int i = 0;
        while (i < r) {
            itr.next();
            i++;
        }
        ans = itr.next().getDest();
        return ans;
    }

    private static int nextNode(directed_weighted_graph g, CL_Agent agent, int src, List<CL_Pokemon> c, List<CL_Agent> ag, game_service game) {
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
            // if (_ar.try_again(agent, pokemon)) {
            if (_ar.can_he_go(agent.getID(), pokemon)) {
                // pokemon.setWilleat(true);
                int dest = pokemon.get_edge().getDest();

                if (pokemon.getType() > 0) {
                    dest = pokemon.get_edge().getSrc();
                    //   dest=Math.max(pokemon.get_edge().getDest(),pokemon.get_edge().getSrc());
                }
                // if (!_ar.pokemon_is_out(agent.getID(), dest)) {

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
                    // }
                    // }
                }
            }//else{
//                count++;
//                System.out.println(count);
//            }
        }
//        for (CL_Agent a:ag) {
//            if(!a.isMoving()){
//                double t = ga.shortestPathDist(a.getSrcNode(), fdest) / agent.getSpeed() / ca.getValue();
//
//                if (t >= 0 && t < mint) {
//                    agent=a;
//                    mint = t;
//                }
//            }
//
//        }
        if (fdest != -1) {
            agent.set_curr_fruit(ca);
          //  _ar.setCount_try_eat(ca, agent);
            // _ar.can_i_eat(agent);
            _ar.i_am_going(agent.getID(), ca.get_edge());

           // _ar.add_to_out(agent.getID(), fdest);

            List<node_data> p = ga.shortestPath(src, fdest);


            ans = p.get(1).getKey();
            agent.setNextNode(p.get(1).getKey());
//            } else {
//                double max = 0;
//
//                Iterator<edge_data> edge = g.getE(agent.getSrcNode()).iterator();
//
//                while (edge.hasNext()) {
//                    edge_data t = edge.next();
//                    if (!t.equals(ca.get_edge())) {
////                        if (max < g.getNode(agent.getSrcNode()).getLocation().distance(g.getNode(t.getDest()).getLocation())) {
////                            max = g.getNode(agent.getSrcNode()).getLocation().distance(g.getNode(t.getDest()).getLocation());
////                            ans = t.getDest();
////                        }
//                        if (max < t.getWeight()) {
//                            max =t.getWeight();
//                            ans = t.getDest();
//                        }
//                    }
//
//                }
//            }
            //System.out.println(agent.getNextNode());
        } else {
            double min = Double.MAX_VALUE;

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

    private void init(game_service game) {
        System.out.println(game.timeToEnd());

        String g = game.getGraph();
        String fs = game.getPokemons();
//        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        directed_weighted_graph gg = Ex2.loadgraph(g);

        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            Comparator<CL_Pokemon> compi = new Comparator<CL_Pokemon>() {

                @Override
                public int compare(CL_Pokemon o1, CL_Pokemon o2) {
                    return Double.compare(o2.getValue(), o1.getValue());
                }
            };
            PriorityQueue<CL_Pokemon> pokemons_val = new PriorityQueue<CL_Pokemon>(compi);
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) {
                Arena.updateEdge(cl_fs.get(a), gg);
//					System.out.println(cl_fs.get(a).get_edge().getDest());
                pokemons_val.add(cl_fs.get(a));
            }
//            for (int a = 0; a < rs; a++) {
//                int ind = a % cl_fs.size();
//                CL_Pokemon c = cl_fs.get(ind);
//                int nn = c.get_edge().getDest();
////                int nn = Math.min(c.get_edge().getDest(),c.get_edge().getSrc());
//                if (c.getType() < 0) {
//                    nn = c.get_edge().getSrc();
////                    nn = Math.max(c.get_edge().getDest(),c.get_edge().getSrc());
//                }

            while (!pokemons_val.isEmpty() && rs != 0) {

                CL_Pokemon c = pokemons_val.poll();
                int nn = c.get_edge().getDest();
//                int nn = Math.min(c.get_edge().getDest(),c.get_edge().getSrc());
                if (c.getType() < 0) {
                    nn = c.get_edge().getSrc();
//                    nn = Math.max(c.get_edge().getDest(),c.get_edge().getSrc());
                }

                game.addAgent(nn);
                rs--;
            }
            _ar.setAgents(Arena.getAgents(game.getAgents(), gg));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


