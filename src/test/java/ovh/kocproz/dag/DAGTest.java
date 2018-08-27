package ovh.kocproz.dag;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author KocproZ
 * Created 2018-08-14 at 20:02
 */
public class DAGTest {

    DAG<String> dag;

    @Before
    public void prepare() {
        dag = new DAG<>();
    }

    //    @Test
    public void chaoticTest() {
        System.out.println("Hello World!");
        DAG<String> dag = new DAG<>();

        Random r = new Random();
        dag.createNode("Node0");
        for (int i = 1; i < 4; i++) {
            Node<String> n = dag.createNode("Node" + i);
            for (int j = 0; j < (i / 2); j++) {
                n.addChild(dag.getNode("Node" + r.nextInt(i)));
            }
        }

        long startTime = System.currentTimeMillis();
        dag.update();
        long endTime = System.currentTimeMillis();

        System.out.println("Graph with " + dag.getNodes().size() + " nodes generated in " + (endTime - startTime) + " miliseconds.");
    }

    @Test
    public void createNodeTest() {
        Node<String> node = dag.createNode("Node0");
        assertEquals(1, dag.getNodes().size());
    }

    @Test
    public void edgeTest() {
        dag.addEdge("par", "chi");
        assertNotNull(dag.getNode("par"));
        assertNotNull(dag.getNode("chi"));
        assertEquals(dag.getNode("par").getChildren().size(), 1);
        assertEquals(dag.getNode("par").getParents().size(), 0);
        assertEquals(dag.getNode("chi").getChildren().size(), 0);
        assertEquals(dag.getNode("chi").getParents().size(), 1);
    }

    @Test
    public void shouldAddAsParent() {
        Node<String> a = dag.createNode("a");
        Node<String> b = dag.createNode("b");
        a.addChild(b);
        assertEquals(a.getChildren().size(), 1);
        assertEquals(b.getParents().size(), 1);
    }

    @Test
    public void shouldAddAsChild() {
        Node<String> a = dag.createNode("a");
        Node<String> b = dag.createNode("b");
        b.addParent(a);
        assertEquals(a.getChildren().size(), 1);
        assertEquals(b.getParents().size(), 1);
    }

    @Test(expected = CycleFoundException.class)
    public void shouldFindCycleOnItselfAsParent() {
        Node<String> a = dag.createNode("Node1");
        a.addParent(a);
    }

    @Test(expected = CycleFoundException.class)
    public void shouldFindCycleOnItselfAsChild() {
        Node<String> a = dag.createNode("Node1");
        a.addChild(a);
    }

    @Test(expected = CycleFoundException.class)
    public void shouldFindCycle() {
        dag.addEdge("a", "b");
        dag.addEdge("b", "a");
        dag.update();
    }

    @Test
    public void shouldNotFindCycle() {
        dag.createNode("1");
        dag.update();
    }

}
