package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
    /**
     * check save and load of the program
     */
    @Test
    void save_load() {
        directed_weighted_graph g0 =DWGraph_DSTest.graph_creator(10,30,1);
       // directed_weighted_graph g0=small_graph();
        directed_weighted_graph ga=new DWGraph_DS(g0);
        System.out.println(ga.toString());

        System.out.println(g0.toString());
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        String str = "graph.json";
        ag0.save(str);
        boolean bo=ag0.save(str);
        assertTrue(bo);

        boolean b=ag0.load(str);
        System.out.println(g0.toString());
        assertTrue(b);
        assertEquals(ga,g0);
       // g0.removeNode(0);
       // assertNotEquals(g0,ga);
    }
    private directed_weighted_graph small_graph() {
        directed_weighted_graph g0 = DWGraph_DSTest.graph_creator(11);
        g0.connect(0,1,1);
        g0.connect(0,2,2);
        g0.connect(0,3,3);

        g0.connect(1,4,17);
        g0.connect(1,5,1);
        g0.connect(2,4,1);
        g0.connect(3, 5,10);
        g0.connect(3,6,100);
        g0.connect(5,7,1.1);
        g0.connect(6,7,10);
        g0.connect(7,10,2);
        g0.connect(6,8,30);
        g0.connect(8,10,10);
        g0.connect(4,10,30);
        g0.connect(3,9,10);
        g0.connect(9,10,1);
        g0.connect(8,10,10);

        return g0;
    }

}