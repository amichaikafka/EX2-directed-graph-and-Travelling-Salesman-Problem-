package api;

/**
 * This class implements edge_data interface the set of operations applicable on a
 * directional edge(src,dest) in a (directional) weighted graph.
 */

class edgeData implements edge_data {


    int src, dest, Tag = 0;
    double w;
    String Info = "";
    //constructor

    public edgeData(int src, int dest, double weight) {
        this.src = src;
        this.dest = dest;
        w = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        edgeData edge_data = (edgeData) o;

        return Double.compare(edge_data.w, w) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(w);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "{" +
                "src=" + src +
                ", dest=" + dest +
                ", Weight=" + w +
                '}';
    }
    /**
     * @return The id of the source node of this edge.
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * @return The id of the destination node of this edge
     */
    @Override
    public int getDest() {
        return this.dest;
    }
    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.w;
    }
    /**
     * Returns the remark  associated with this edge.
     * @return
     */
    @Override
    public String getInfo() {
        return this.Info;
    }
    /**
     * Allows changing the remark  associated with this edge.
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
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.Tag = t;
    }
}
