package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    static class NodeData implements node_data{

        int Key,Tag;
        geo_location Geo_Loc;
        double Weight;
        String Info;

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

    static class Geo implements geo_location{

        double X,Y,Z;

        //constructor
        public Geo(double _x, double _y, double _z){
            this.X = _x;
            this.Y = _y;
            this.Z = _z;

        }
        //copy constructor
        public Geo(geo_location p){
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
            double pow_point = Math.pow((this.X-g.x()),2)+Math.pow((this.Y-g.y()),2)+Math.pow((this.Z-g.z()),2);
            pow_point = Math.sqrt(pow_point);
            return pow_point;
        }

    }
    //-------------------inner class end Geo (geo_location)-------------------//



    static class Edge_Data implements edge_data{

        int Src,Dest,Tag;
        double Weight;
        String Info;


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
    HashMap<Integer,node_data> nodes = new HashMap<Integer,node_data>();
    HashMap<Integer,HashMap<Integer,edge_data>> Edges = new HashMap<Integer,HashMap<Integer,edge_data>>();
    int MC;

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

    }

    @Override
    public void connect(int src, int dest, double w) {

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
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return null;
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
