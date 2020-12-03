package api;

import java.util.*;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {

    static class Edge_Data implements edge_data {


        int src, dest, Tag = 0;
        double w;
        String Info = "";

        public Edge_Data(int src, int dest, double weight) {
            this.src = src;
            this.dest = dest;
            w = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge_Data edge_data = (Edge_Data) o;

            return Double.compare(edge_data.w, w) == 0;
        }

        @Override
        public int hashCode() {
            long temp = Double.doubleToLongBits(w);
            return (int) (temp ^ (temp >>> 32));
        }

        @Override
        public String toString() {
            return "Edge_Data{" +
                    "src=" + src +
                    ", dest=" + dest +
                    ", Weight=" + w +
                    '}';
        }

        @Override
        public int getSrc() {
            return this.src;
        }

        @Override
        public int getDest() {
            return this.dest;
        }

        @Override
        public double getWeight() {
            return this.w;
        }

        @Override
        public String getInfo() {
            return this.Info;
        }

        @Override
        public void setInfo(String s) {
            this.Info = s;
        }

        @Override
        public int getTag() {
            return this.Tag;
        }

        @Override
        public void setTag(int t) {
            this.Tag = t;
        }
    }
    //-------------------inner class end Edge_Data (edge_data)-------------------//

    //Field
    private HashMap<Integer, node_data> nodes = new HashMap<Integer, node_data>();
    private HashMap<Integer, HashMap<Integer, edge_data>> Edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
    private int MC = 0, edgesize = 0;

    public DWGraph_DS() {
         this.MC = 0;
         this.edgesize = 0;
    }
    public DWGraph_DS(directed_weighted_graph g) {
        node_data n1;
        Iterator<node_data> e = g.getV().iterator();
        while (e.hasNext()) {
            node_data t = e.next();
            node_data n=new NodeData(t);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DWGraph_DS that = (DWGraph_DS) o;

        if (edgesize != that.edgesize) return false;
        if (nodes != null ? !nodes.equals(that.nodes) : that.nodes != null) return false;
        return Edges != null ? Edges.equals(that.Edges) : that.Edges == null;
    }

    @Override
    public int hashCode() {
        int result = nodes != null ? nodes.hashCode() : 0;
        result = 31 * result + (Edges != null ? Edges.hashCode() : 0);
        result = 31 * result + edgesize;
        return result;
    }

    @Override
    public node_data getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        return this.Edges.get(src).get(dest);
    }

    @Override
    public void addNode(node_data n) {
        nodes.put(n.getKey(), n);
        HashMap<Integer,edge_data> naibers= new HashMap<Integer, edge_data>();
        Edges.put(n.getKey(),naibers);
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest) {
            if (this.getNode(src) != null && this.getNode(dest) != null) {
              if (Edges.get(src).get(dest) == null) {
                    edge_data e = new Edge_Data(src, dest, w);
                    Edges.get(src).put(dest, e);
                    edgesize++;
                    MC++;
                } else if (Edges.get(src).get(dest).getWeight() != w) {
                    edge_data e = new Edge_Data(src, dest, w);
                    Edges.get(src).replace(dest, e);
                    MC++;
                }
            }
        }
    }


    @Override
    public Collection<node_data> getV() {
        return this.nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return this.Edges.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        node_data t = this.getNode(key);//the node we want to remove
        if (t != null) {

            Iterator<edge_data> i = this.getE(key).iterator();
            while (i.hasNext()) {

                edge_data e = i.next();
                this.removeEdge(key, e.getDest());
                if(Edges.get(e.getDest()).get(key)!=null){
                    this.removeEdge( e.getDest(),key);

                }
                i = this.getE(key).iterator();

                MC++;
            }
            Edges.remove(key, Edges.get(key));
        }
        nodes.remove(key, t);
        return t;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        edge_data e = this.getEdge(src, dest);
        if (e != null) {
            Edges.get(src).remove(dest, e);
            edgesize--;
        }

        return e;
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgesize;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

   @Override
    public String toString() {
        String s ="DWGraph_DS{";
        Iterator<node_data> i=this.getV().iterator();
        while (i.hasNext()) {
            node_data t = i.next();
            s = s +t.getKey()+":{";
                    Iterator < edge_data > e = this.getE(t.getKey()).iterator();
            while (e.hasNext()) {
                s = s + e.next().toString();
            }
            s=s+"}";
        }
        s=s+"}";
        return s;
    }
}
