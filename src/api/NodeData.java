package api;
/**
 * This class implements node_data interface represents the set of operations applicable on a
 * node (vertex) in a (directional) weighted graph.
 *
 */
public class NodeData implements node_data{
    public static int uniqe = -1;
   private int id, Tag = 0;
   private geo_location pos =new Geo();
   private double Weight = 0;
   private String Info = "";
    /**
     *constructor
     */
    public NodeData() {
        uniqe++;
        id = uniqe;
    }
    public NodeData(int id){//use only in testes class
        this.id=id;
    }
    public NodeData(int id,geo_location location){//use only in testes class
        this.id=id;
        this.pos=location;
    }
    public NodeData(int key, int tag, geo_location geo_Loc, double weight, String info) {
        id = key;
        Tag = tag;
        pos = geo_Loc;
        Weight = weight;
        Info = info;
    }
    /**
     *copy constructor
     */
    public NodeData (node_data n){
        this.id=n.getKey();
        this.Tag=0;
        if(n.getLocation()!=null) {
            this.pos = new Geo(n.getLocation());
        }else{
            this.pos=null;
        }
        this.Weight=n.getWeight();
        this.Info=n.getInfo();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeData nodeData = (NodeData) o;

        return pos != null ? pos.equals(nodeData.pos) : nodeData.pos == null;
    }

    @Override
    public int hashCode() {
        return id;
    }
    /**
     * Returns the key (id) associated with this node.
     * @return
     */
    @Override
    public int getKey() {
        return this.id;
    }
    /**
     * Returns the location of this node, if
     * @return
     */
    @Override
    public geo_location getLocation() {
        return this.pos;
    }
    /**
     * Allows changing this node's location.
     * @param p - new new location  of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        pos = new Geo(p);
    }
    /**
     * Returns the weight associated with this node.
     * @return
     */
    @Override
    public double getWeight() {
        return this.Weight;
    }
    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.Weight = w;
    }
    /**
     * Returns the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public String getInfo() {
        return this.Info;
    }
    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.Info = s;
    }
    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * @return
     */
    @Override
    public int getTag() {
        return this.Tag;
    }
    /**
     * Allows setting the "tag" value for temporal marking an node
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.Tag = t;
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "id=" + id +
                ", pos=" + pos.toString() +
                '}';
    }
}

