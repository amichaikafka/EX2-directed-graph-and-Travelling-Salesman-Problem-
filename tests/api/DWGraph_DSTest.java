package api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
/**
 * the goal of this test class is to check the reliability of DWGraph_DS.
 * part of the testes taken from or based on the testes from the course's github.(including creating graph)
 */

class DWGraph_DSTest {
    private static Random _rnd = null;

    @org.junit.jupiter.api.Test
    void getNode() {
    }

    @org.junit.jupiter.api.Test
    void getEdge() {
    }

    @org.junit.jupiter.api.Test
    void addNode() {
    }

    @org.junit.jupiter.api.Test
    void connect() {
    }

    @org.junit.jupiter.api.Test
    void getV() {
    }

    @org.junit.jupiter.api.Test
    void getE() {
    }

    @org.junit.jupiter.api.Test
    void removeNode() {
    }

    @org.junit.jupiter.api.Test
    void removeEdge() {
    }

    @org.junit.jupiter.api.Test
    void nodeSize() {
    }

    @org.junit.jupiter.api.Test
    void edgeSize() {
    }

    @org.junit.jupiter.api.Test
    void getMC() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

    ///////////////////////////////////

    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */

    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            node_data n = new NodeData();
            g.addNode(n);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    public static directed_weighted_graph graph_creator(int v_size) {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < v_size; i++) {
            node_data n = new NodeData();
            g.addNode(n);
        }
        return g;
    }

    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }


}