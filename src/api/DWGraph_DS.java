package api;

import java.util.*;
import java.util.Collection;
import java.util.HashMap;
/**
 * This class implements directed_weighted_graph interface represents a directional weighted graph.
 * can support a large number of nodes (over 100,000).
 * The implementation using a hashmap.
 */

public class DWGraph_DS implements directed_weighted_graph {

    //Field
    private HashMap<Integer, node_data> nodes = new HashMap<Integer, node_data>();//contain all the nodes in the graph
    private HashMap<Integer, HashMap<Integer, edge_data>> Edges = new HashMap<Integer, HashMap<Integer, edge_data>>();//contain all the naiber for each nodes in the graph and the edge between
    private int MC = 0;//number of changes done in this graph
  private int edgesize = 0;//number of edges in the graph

    public DWGraph_DS() {
        this.MC = 0;
        this.edgesize = 0;
    }
    /**
     *copy constructor
     * @param g
     */
    public DWGraph_DS(directed_weighted_graph g) {
        node_data n1;
        Iterator<node_data> e = g.getV().iterator();
        while (e.hasNext()) {
            node_data t = e.next();
            node_data n = new NodeData(t);
            this.addNode(t);
        }

        e = g.getV().iterator();
        while (e.hasNext()) {
            n1 = e.next();
            Iterator<edge_data> e2 = g.getE(n1.getKey()).iterator();
            while (e2.hasNext()) {
                edge_data edge = e2.next();
                this.connect(n1.getKey(), edge.getDest(), edge.getWeight());
            }
        }
        this.MC = g.getMC();
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        DWGraph_DS that = (DWGraph_DS) o;

//        if (edgesize != that.edgesize) return false;
//        if (nodes != null ? !nodes.equals(that.nodes) : that.nodes != null) return false;
//        return Edges != null ? Edges.equals(that.Edges) : that.Edges == null;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DWGraph_DS that = (DWGraph_DS) o;
        if (this.nodeSize() != that.nodeSize()) return false;

        if (edgesize != that.edgesize) return false;
        Iterator<node_data> i1=that.getV().iterator();
        Iterator<node_data> i2=this.getV().iterator();
        while (i1.hasNext()&&i2.hasNext()){
            node_data t1=i1.next();
            node_data t2=i2.next();
            if(!t2.equals(t1)){
                return false;
            }
            Iterator<edge_data> i3=that.getE(t1.getKey()).iterator();
            Iterator<edge_data> i4=this.getE(t2.getKey()).iterator();
            while (i1.hasNext()&&i2.hasNext()){
                if(!i2.next().equals(i1.next())){
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public int hashCode() {
        int result = nodes != null ? nodes.hashCode() : 0;
        result = 31 * result + (Edges != null ? Edges.hashCode() : 0);
        result = 31 * result + edgesize;
        return result;
    }
    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        return this.nodes.get(key);
    }
    /**
     * returns the data of the edge (src,dest), null if none.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        return this.Edges.get(src).get(dest);
    }
    /**
     * adds a new node to the graph with the given node_data.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        nodes.put(n.getKey(), n);
        HashMap<Integer, edge_data> naibers = new HashMap<Integer, edge_data>();
        Edges.put(n.getKey(), naibers);
    }
    /**
     * Connects an edge with weight w between node src to node dest.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src--dest.
     */

    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest) {
            if (this.getNode(src) != null && this.getNode(dest) != null) {
                if (Edges.get(src).get(dest) == null) {
                    edge_data e = new edgeData(src, dest, w);
                    Edges.get(src).put(dest, e);
                    edgesize++;
                    MC++;
                } else if (Edges.get(src).get(dest).getWeight() != w) {
                    edge_data e = new edgeData(src, dest, w);
                    Edges.get(src).replace(dest, e);
                    MC++;
                }
            }
        }
    }
    /**
     * This method returns a pointer  for the
     * collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */

    @Override
    public Collection<node_data> getV() {
        return this.nodes.values();
    }
    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * @return Collection<edge_data>
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return this.Edges.get(node_id).values();
    }
    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_data removeNode(int key) {
        node_data t = this.getNode(key);//the node we want to remove
        if (t != null) {
            Iterator<node_data> it = this.getV().iterator();
            while (it.hasNext()) {
                node_data t1 = it.next();
                if (Edges.get(t1.getKey()).get(key) != null) {
                    this.removeEdge(t1.getKey(), key);
                }

            }
            Iterator<edge_data> i = this.getE(key).iterator();
            while (i.hasNext()) {
                edge_data e = i.next();
                this.removeEdge(key, e.getDest());
                i = this.getE(key).iterator();

            }
            Edges.remove(key, Edges.get(key));
            MC++;
            nodes.remove(key, t);
        }

        return t;
    }
    /**
     * Deletes the edge from the graph,
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {

        edge_data e = this.getEdge(src, dest);
        if (e != null) {
            Edges.get(src).remove(dest, e);
            edgesize--;
            MC++;
        }
        return e;
    }
    /** Returns the number of vertices (nodes) in the graph.
     * @return number of vertex of this graph
     */
    @Override
    public int nodeSize() {
        return this.nodes.size();
    }
    /**
     * Returns the number of edges .
     * @return nuber of edge
     */
    @Override
    public int edgeSize() {
        return edgesize;
    }
    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return Mode Count
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    @Override
    public String toString() {
        String s = "DWGraph_DS{ nodes{";
        Iterator<node_data> i = this.getV().iterator();
        while (i.hasNext()) {
            node_data t = i.next();
            s = s +"{"+ t.getKey()+","+t.getLocation().toString() + "}";
        }
        s = s + "} Edges {";
        i = this.getV().iterator();
        while (i.hasNext()) {
            node_data t = i.next();
            Iterator<edge_data> e = this.getE(t.getKey()).iterator();
            while (e.hasNext()) {
                s = s + e.next().toString()+",";
            }
        }
        s = s + "}";
        return s;
    }
}
