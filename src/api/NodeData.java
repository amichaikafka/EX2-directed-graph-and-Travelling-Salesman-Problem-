package api;

public class NodeData implements node_data{
    public static int id = -1;
    int Key, Tag = 0;
    geo_location Geo_Loc = null;
    double Weight = Double.MAX_VALUE;
    String Info = "";

    public NodeData() {
        id++;
        Key = id;
    }

    public NodeData(int key, int tag, geo_location geo_Loc, double weight, String info) {
        Key = key;
        Tag = tag;
        Geo_Loc = geo_Loc;
        Weight = weight;
        Info = info;
    }

    public NodeData (node_data n){
        this.Key=n.getKey();
        this.Tag=0;
        this.Geo_Loc=new Geo(n.getLocation());
        this.Weight=n.getWeight();
        this.Info=n.getInfo();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeData nodeData = (NodeData) o;

        if (Key != nodeData.Key) return false;
        if (Tag != nodeData.Tag) return false;
        if (Double.compare(nodeData.Weight, Weight) != 0) return false;
        if (Geo_Loc != null ? !Geo_Loc.equals(nodeData.Geo_Loc) : nodeData.Geo_Loc != null) return false;
        return Info.equals(nodeData.Info);
    }

    @Override
    public int hashCode() {
        return Key;
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

    @Override
    public String toString() {
        return "NodeData{" +
                "Key=" + Key +
                ", Geo_Loc=" + Geo_Loc.toString() +
                '}';
    }
}

