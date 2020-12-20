package api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;


import java.io.*;
import java.util.*;
import java.util.List;
/**
 * This class implements the interface directed_weighted_graph including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected) using BFS algorithm
 * 3. double shortestPathDist(int src, int dest);using Dijkstra algorithm;
 * 4. List<node_data> shortestPath(int src, int dest);using Dijkstra algorithm;
 * 5. Save(file); // JSON file
 * 6. Load(file); // JSON file
 *
 */

public class DWGraph_Algo implements dw_graph_algorithms {
    /**
     * inner class assigned to the implementation of Dijkstra algorithm
     * each node_algo connect to a specific node in the graph
     */
    private class node_algo {
        private node_data node;
        private int key;
        private double w;
        private boolean visit;
        public node_algo (node_data n){
            this.node=n;
            this.key=n.getKey();
            this.w=Double.MAX_VALUE;
            this.visit=false;

        }

        @Override
        public String toString() {
            return "{" +
                    "key=" + key +
                    ", w=" + w +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            node_algo node_algo = (node_algo) o;

            if (key != node_algo.key) return false;
            if (Double.compare(node_algo.w, w) != 0) return false;
            if (visit != node_algo.visit) return false;
            return node.equals(node_algo.node);
        }

        @Override
        public int hashCode() {
            return key;
        }

        public int getKey() {
            return key;
        }

        public double getW() {
            return w;
        }

        public void setW(double w) {
            this.w = w;
        }

        public node_data getNode() {
            return node;
        }

        public boolean isVisit() {
            return visit;
        }

        public void setVisit(boolean visit) {
            this.visit = visit;
        }
    }
    private class comp implements Comparator<node_algo> {

        @Override
        public int compare(node_algo o1, node_algo o2) {
            return Double.compare(o1.getW(), o2.getW());
        }
    }
    private directed_weighted_graph g;

    public DWGraph_Algo() {
        this.g = new DWGraph_DS();
    }

    @Override
    public void init(directed_weighted_graph g) {
        this.g = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.g;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g1=new DWGraph_DS(this.g);
        return g1;
    }
    /**
     * this function implements the BFS algorithm on the graph
     * marking any node that has been visited.
     *
     * @param key
     */
    private void BFS(int key) {

        Queue<node_data> q = new LinkedList<node_data>();
        node_data t = this.g.getNode(key);
        q.add(t);
        t.setTag(1);
        while (!q.isEmpty()) {
            t = q.poll();
            Iterator<edge_data> e = this.g.getE(t.getKey()).iterator();
            while (e.hasNext()) {
                edge_data ed = e.next();
                if (this.g.getNode(ed.getDest()).getTag() != 1) {//check if  visited
                    this.g.getNode(ed.getDest()).setTag(1);//set as visited
                    q.add(this.g.getNode(ed.getDest()));
                }
            }
        }
    }
    /**
     * initialization of all the vertex, to avoid mistakes in the next operations.
     */
    private void initgraph() {//initialization of all the vertex, to avoid mistakes in the next operations.
        Iterator<node_data> e = this.g.getV().iterator();
        while (e.hasNext()) {
            node_data t = e.next();
            t.setTag(0);
            t.setWeight(Double.MAX_VALUE);
            t.setInfo("");
        }
    }
    /**
     * @return true if and only if (iff) there is a valid path from EVREY node to each
     *  other node.  directed graph. using BFS algorithm
     */

    @Override
    public boolean isConnected() {
        if (this.g == null)
            return true;
        if (this.g.getV().isEmpty()||this.g.nodeSize()==1)
            return true;
        if (this.g.nodeSize() > this.g.edgeSize() + 1)
            return false;
        initgraph();//initialization of all the vertex, to avoid mistakes in the next operations.
        Iterator<node_data> e = this.g.getV().iterator();
        while (e.hasNext()) {
            node_data t = e.next();
            BFS(t.getKey());
            Iterator<node_data> i = this.g.getV().iterator();
            while (i.hasNext()) {
                t = i.next();
                if (t.getTag() != 1) { //check if not visited,if there is one vertex like that return false
                    return false;
                }
            }
            initgraph();
        }
        return true;
    }


//    private void initnode(HashMap<Integer,node_algo> p){
//        Iterator<node_algo> i=p.values().iterator();
//        while(i.hasNext()){
//            node_algo t=i.next();
//            t.setW(Double.MAX_VALUE);
//            t.setVisit(false);
//        }
//    }
    /**
     * this function implements the Dijkstra algorithm on the graph
     * using the inner class node_algo, every node_algo connect to a spcific node in the graph
     * marking any node that has been visited and setting the Tag to the distance from the src node (source node)
     * for each node, in this search, until destination node .
     * @param src
     * @param dest
     * @return HashMap<Integer, node_info> that contain every parent of each node (consider minimum distance )
     *  in this search starting at key (id) node.
     */
    private HashMap<Integer, node_algo> Dijkstra(node_algo src ,node_algo dest) {
        HashMap<Integer, node_algo> ph = new HashMap<Integer, node_algo>();
        HashMap<Integer, node_algo> pa = new HashMap<Integer, node_algo>();
        comp compare = new comp();//comperator to determine the order in the priorityqueue.
        PriorityQueue<node_algo> pq = new PriorityQueue(compare);
        src.setW(0);
        pq.add(src);
        while (!(pq.isEmpty())) {
            node_algo na = pq.poll();
            if(pq.peek()!=null&&pq.peek().getW()< na.getW()){//double check to avoid mistake in the order
                pq.add(na);
                na=pq.poll();
            }
            na.getNode().setTag(1);
            if(na.equals(dest)){
                return ph;
            }
            Iterator<edge_data>  e = this.g.getE(na.getKey()).iterator();
            while (e.hasNext()) {
                edge_data de = e.next();
                node_data t = this.g.getNode(de.getDest());
                if (t.getKey() == dest.getKey()) {
                    double w = de.getWeight() + na.getW();
                    double o =dest.getW();
                    int y=dest.getKey();
                    if (w < dest.getW()) {//chek if it's the minimum distance from source's node.
                        dest.setW(w);//if so set the new distance
                        if (ph.containsKey(dest.getKey())) {//check it its not the first time we get to this node
                            ph.replace(dest.getKey(), na);//if so just replace his parent, to make sure that's the correct parent in shortest path
                        } else {
                            ph.put(dest.getKey(), na);//if not enter new key and parent to the map
                            pq.add(dest);// add the new node (at the search ) to the queue
                        }
                    }
                } else {
                    node_algo th;
                    if(!pa.containsKey(t.getKey())) {
                         th = new node_algo(t);
                        pa.put(th.getKey(),th);
                    }else{
                         th=pa.get(t.getKey());
                    }
                    if (th.getNode().getTag()==0) {//do the next operation if not visited
                        double l=th.getW();
                        int f= th.getKey();
                        double b=na.getW();
                        int v= na.getKey();

                        double w = de.getWeight() + na.getW();
                        if (w < th.getW()) {//chek if it's the minimum distance from source's node.
                            th.setW(w);//if so set the new distance
                            if (ph.containsKey(th.getKey())) {//check it its not the first time we get to this node
                                ph.replace(th.getKey(), na);//if so just replace his parent, to make sure that's the correct parent in shortest path
                            } else {
                                ph.put(th.getKey(), na);//if not enter new key and parent to the map
                                pq.add(th);// add the new node (at the search ) to the queue
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * returns the length of the shortest path between src to dest using Dijkstra algorithm
     *
     * if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return double
     */

    @Override
    public double shortestPathDist(int src, int dest) {

        if (src == dest)
            return 0;
        if(this.g.getNode(src)==null||this.g.getNode(dest)==null)
            return -1;
        initgraph();
        node_algo s=new node_algo(g.getNode(src));
        node_algo d=new node_algo(g.getNode(dest));
        HashMap<Integer, node_algo> p = Dijkstra(s,d);
        if(p==null){
            return -1;
        }

        return d.getW();

    }

    /**
     * returns the the shortest path between src to dest using Dijkstra algorithm- as an ordered List of nodes:
     * src-- n1--n2--...dest
     * if no such path -- returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return List<node_data>
     */

    @Override
    public List<node_data> shortestPath(int src, int dest) {

        initgraph();
        node_algo s=new node_algo(g.getNode(src));

        node_algo d=new node_algo(g.getNode(dest));
        HashMap<Integer, node_algo> p = Dijkstra(s,d);//hash map with the parent of each node in this search
        if (p==null) {//if the map does not contain dest's key there is no path between theos nodes
            return null;
        }
        node_algo th = d;
        node_data t = th.getNode();
        LinkedList<node_data> path = new LinkedList<>();

        path.add(t);
        while (t != this.g.getNode(src) && t != null) {

            t = p.get(th.getKey()).getNode();
            path.addFirst(t);//add the nodes to the list in the correct order
            th=p.get(th.getKey());
        }

        if (t == null)
            return null;
        return path;
    }
    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        Gson gson=new GsonBuilder().create();
        String json=gson.toJson(this.g);
        try{
            PrintWriter pw= new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            GsonBuilder Gbuilde = new GsonBuilder();
            Gbuilde.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DS_To_Json());
            Gson gson = Gbuilde.create();
            FileReader fr = new FileReader(file);
            this.g=gson.fromJson(fr,DWGraph_DS.class);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
