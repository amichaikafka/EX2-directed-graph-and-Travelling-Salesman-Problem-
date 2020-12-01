package api;

public class NodeData implements node_data{

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
    public static int id = -1;

    int Key, Tag = 0;
    geo_location Geo_Loc = null;
    double Weight = Double.MAX_VALUE;
    String Info = "";

    public NodeData() {
        id++;
        Key = id;
    }
    public NodeData (node_data n){
        this.Key=n.getKey();
        this.Tag=0;
        this.Geo_Loc=new Geo(n.getLocation());
        this.Weight=n.getWeight();
        this.Info=n.getInfo();

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

