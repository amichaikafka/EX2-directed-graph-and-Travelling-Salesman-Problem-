package api;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void init() {
        directed_weighted_graph g0 =DWGraph_DSTest.graph_creator(10,30,1);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        assertEquals(g0,ag0.getGraph());
    }

    @Test
    void getGraph() {
        directed_weighted_graph g0 =DWGraph_DSTest.graph_creator(10,30,1);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        assertEquals(g0,ag0.getGraph());
    }

    @Test
    void copy() {
        directed_weighted_graph g0 =DWGraph_DSTest.graph_creator(10,30,1);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        directed_weighted_graph g1= ag0.copy();
        assertEquals(g0,g1);
        g1.removeNode(0);
        assertNotEquals(g0,g1);
    }

    @Test
    void isConnected() {
        directed_weighted_graph g0 =small_graph();
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        assertFalse(ag0.isConnected());
        makeconnected(ag0.getGraph());
        assertTrue(ag0.isConnected());
        g0.removeNode(0);
        assertFalse(ag0.isConnected());
    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g0 =small_graph();
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        double d=ag0.shortestPathDist(6,10);
        assertEquals(11.5,d);
        g0.removeNode(1);
        d=ag0.shortestPathDist(0,4);
        assertEquals(3,d);
        d=ag0.shortestPathDist(2,0);
        assertEquals(-1,d);
    }

    @Test
    void shortestPath() {

      directed_weighted_graph g0 = small_graph();
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        ag0.init(g0);
        List<node_data> sp = ag0.shortestPath(0,10);

//        int[] checkKey = {0, 1, 5, 7, 10};
//        int i = 0;
//        for(node_data n: sp) {
//            assertEquals(checkKey[i], n.getKey());
//            i++;
//        }
        System.out.println(sp);



    }

    @Test
    void save() {
        directed_weighted_graph g0 =DWGraph_DSTest.graph_creator(10,30,1);
        directed_weighted_graph ga=new DWGraph_DS(g0);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        System.out.println(g0.toString());
        System.out.println(ga.toString());
        ag0.init(g0);
        String str = "graph.json";
        ag0.save(str);
        boolean bo=ag0.save(str);
        assertTrue(bo);
    }

    /**
     * check save and load of the program
     */
    @Test
    void save_load() {
        directed_weighted_graph g0 =DWGraph_DSTest.graph_creator(10,30,1);
        directed_weighted_graph ga=new DWGraph_DS(g0);
        dw_graph_algorithms ag0 = new DWGraph_Algo();
        System.out.println(g0.toString());
        System.out.println(ga.toString());
        ag0.init(g0);
        String str = "graph.json";
        ag0.save(str);
        boolean bo=ag0.save(str);
        assertTrue(bo);
        boolean b=ag0.load(str);
        System.out.println(g0.toString());

        assertTrue(b);
        assertEquals(ga,g0);

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
    private void makeconnected(directed_weighted_graph g){
        Iterator<node_data> i=g.getV().iterator();
        node_data n=i.next();
        while (i.hasNext()){
            node_data t=i.next();
            g.connect(n.getKey(),t.getKey(),1);
            g.connect(t.getKey(),n.getKey(),1);
        }
    }

}