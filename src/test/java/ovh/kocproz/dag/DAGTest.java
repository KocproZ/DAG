package ovh.kocproz.dag;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

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

    @Test
    public void chaoticTest() {
        System.out.println("Hello World!");
        DAG<String> dag = new DAG<>();

        Random r = new Random();
        dag.createNode("Node0");
        for (int i = 1; i < 501; i++) {
            Node<String> n = dag.createNode("Node" + i);
            for (int j = 0; j < (i / 2); j++) {
                n.addChild(dag.getNode("Node" + r.nextInt(i)));
            }
        }

        System.out.println("Generating graph...");

        long startTime = System.currentTimeMillis();
        dag.generateGraph();
        long endTime = System.currentTimeMillis();

        System.out.println("Graph with " + dag.getNodes().size() + " nodes generated in " + (endTime - startTime) + " miliseconds.");

    }

    @Test
    public void createNodeTest() {
        Node<String> node = dag.createNode("Node0");
        assertTrue(dag.getNodes().size() == 1);
    }

}
