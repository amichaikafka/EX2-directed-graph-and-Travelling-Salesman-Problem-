package api;

import java.util.*;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {
    static class NodeData implements node_data {
        public static int id = -1;

        int Key, Tag = 0;
        geo_location Geo_Loc = null;
        double Weight = Double.MAX_VALUE;
        String Info = "";

        public NodeData() {
            id++;
            Key = id;
        }

        @Override
        public int getKey() {
            return this.Key;
        }

        @Override
        public geo_location getLocation() {
            return this.Geo_Loc;
        }

        @Override
        public void setLocation(geo_location p) {
            Geo_Loc = new Geo(p);
        }

        @Override
        public double getWeight() {
            return this.Weight;
        }

        @Override
        public void setWeight(double w) {
            this.Weight = w;
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
    //-------------------inner class end NodeData (node_data)-------------------//

    static class Geo implements geo_location {

        double X, Y, Z;

        //constructor
        public Geo(double _x, double _y, double _z) {
            this.X = _x;
            this.Y = _y;
            this.Z = _z;

        }

        //copy constructor
        public Geo(geo_location p) {
            this.X = p.x();
            this.Y = p.y();
            this.Z = p.z();
        }

        @Override
        public double x() {
            return this.X;
        }

        @Override
        public double y() {
            return this.Y;
        }

        @Override
        public double z() {
            return this.Z;
        }

        @Override
        public double distance(geo_location g) {
            double pow_point = Math.pow((this.X - g.x()), 2) + Math.pow((this.Y - g.y()), 2) + Math.pow((this.Z - g.z()), 2);
            pow_point = Math.sqrt(pow_point);
            return pow_point;
        }

    }
    //-------------------inner class end Geo (geo_location)-------------------//


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
        return this.Edges.size();  //maybe it's mistake need to check
    }

    @Override
    public int getMC() {
        return this.MC;
    }
}
