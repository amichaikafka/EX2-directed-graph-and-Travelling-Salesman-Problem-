package api;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
/**
 * the goal of this test class is to check the reliability of DWGraph_DS.
 * part of the testes taken from or based on the testes from the course's github.(including creating graph)
 */
class DWGraph_DSTest {
    private directed_weighted_graph g=DWGraph_AlgoTest.small_graph();
    private static Random _rnd = null;
    /**
     * check the getV function
     */

    @org.junit.jupiter.api.Test
    void getV() {
        Iterator<node_data> i=g.getV().iterator();
        assertTrue(i.next() instanceof node_data);

    }
    /**
     * check the getE function
     */
    @org.junit.jupiter.api.Test
    void getE() {
        Iterator<edge_data> i=g.getE(0).iterator();
        assertTrue(i.next() instanceof edge_data);

    }
    /**
     * check the removeNode function-removing node from the graph then check how many node are there in the graph
     */
    @org.junit.jupiter.api.Test
    void removeNode() {

        assertEquals(16,g.edgeSize());
        g.removeNode(10);

        assertEquals(10,g.nodeSize());
        assertEquals(12,g.edgeSize());
        g.removeNode(10);
        assertEquals(10,g.nodeSize());
        g.removeNode(100);
    }
    /**
     * check the removeEdge function-removing edge from the graph then check how many edges are there in the graph
     */
    @org.junit.jupiter.api.Test
    void removeEdge() {

        assertEquals(16,g.edgeSize());
        g.removeEdge(0,1);
        assertEquals(15,g.edgeSize());
        g.removeEdge(0,1);
        assertEquals(15,g.edgeSize());
    }
    /**
     * check the nodeSize function-removing node from the graph then check how many node are there in the graph
     */
    @org.junit.jupiter.api.Test
    void nodeSize() {

        assertEquals(11,g.nodeSize());
        g.removeNode(10);
        assertEquals(10,g.nodeSize());
        g.removeNode(10);
        assertEquals(10,g.nodeSize());
    }
    /**
     * check the edgeSize function-removing edge from the graph then check how many edges are there in the graph
     */
    @org.junit.jupiter.api.Test
    void edgeSize() {

        assertEquals(16,g.edgeSize());
        g.removeEdge(0,1);
        assertEquals(15,g.edgeSize());
        g.removeEdge(0,1);
        assertEquals(15,g.edgeSize());
        g.removeNode(10);
        assertEquals(11,g.edgeSize());
        g.removeNode(10);
        assertEquals(11,g.edgeSize());
    }
    /**
     * check the getNode function
     */
    @org.junit.jupiter.api.Test
    void getNode() {
        directed_weighted_graph g2=new DWGraph_DS();
        node_data n=new NodeData();
        g2.addNode(n);
        node_data n2=g2.getNode(n.getKey());
        assertEquals(n,n2);
    }

    /**
     * check the addNode function-add node to the graph and check if the node is found in the graph
     */
    @org.junit.jupiter.api.Test
    void addNode() {
        directed_weighted_graph g2=new DWGraph_DS();
        node_data n=new NodeData();
        g2.addNode(n);
        assertNotNull(g2.getNode(n.getKey()));
    }
    /**
     * check the connect function-connect two node in the graph and check how many edges in the graph
     */
    @org.junit.jupiter.api.Test
    void connect() {
        directed_weighted_graph g2=new DWGraph_DS();
        node_data n=new NodeData();
        g2.addNode(n);
        node_data n2=new NodeData();
        g2.addNode(n2);
        g2.connect(n.getKey(), n2.getKey(), 1);
        assertEquals(1,g2.edgeSize());
        g2.connect(n2.getKey(), n.getKey(), 1);
        assertEquals(2,g2.edgeSize());


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
            node_data n = new NodeData(i);
            g.addNode(n);
        }
        return g;
    }
    public static directed_weighted_graph small_graph() {
        directed_weighted_graph g0 = DWGraph_DSTest.graph_creator(11);
        g0.connect(0,1,1);
        g0.connect(0,2,2);
        g0.connect(0,3,3);

        g0.connect(1,4,0.5);
        g0.connect(1,5,1);
        g0.connect(2,4,1);
        g0.connect(3, 5,10);
        g0.connect(3,6,100);
        g0.connect(5,7,1.1);
        g0.connect(6,7,10);
        g0.connect(7,10,2);
        g0.connect(6,8,11);
        g0.connect(8,10,0.5);
        g0.connect(4,10,30);
        g0.connect(3,9,10);
        g0.connect(10,9,1);


        return g0;
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