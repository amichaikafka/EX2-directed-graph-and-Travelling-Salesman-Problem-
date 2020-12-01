package api;

import java.util.*;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {

    static class Edge_Data implements edge_data {


        int Src, Dest, Tag = 0;
        double Weight;
        String Info = "";

        public Edge_Data(int src, int dest, double weight) {
            Src = src;
            Dest = dest;
            Weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge_Data edge_data = (Edge_Data) o;

            if (Src != edge_data.Src) return false;
            if (Dest != edge_data.Dest) return false;
            return Double.compare(edge_data.Weight, Weight) == 0;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = Src;
            result = 31 * result + Dest;
            temp = Double.doubleToLongBits(Weight);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "Edge_Data{" +
                    "Src=" + Src +
                    ", Dest=" + Dest +
                    ", Weight=" + Weight +
                    '}';
        }

        @Override
        public int getSrc() {
            return this.Src;
        }

        @Override
        public int getDest() {
            return this.Dest;
        }

        @Override
        public double getWeight() {
            return this.Weight;
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
            nodes.remove(key, t);
            Iterator<edge_data> i = this.getE(key).iterator();
            while (i.hasNext()) {
                edge_data e = i.next();
                this.removeEdge(key, e.getDest());
                edgesize--;
                MC++;
            }
            Edges.remove(key, Edges.get(key));
        }
        return t;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        edge_data e = this.getEdge(src, dest);
        if (e != null) {
            Edges.get(src).remove(dest, e);
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
        while (i.hasNext()){
            Iterator<edge_data> e=this.getE(i.next().getKey()).iterator();
            s=s+e.next().toString();
        }
        s=s+"}";
        return s;
    }
}
