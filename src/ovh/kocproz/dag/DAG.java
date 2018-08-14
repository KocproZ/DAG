package ovh.kocproz.dag;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author KocproZ
 * Created 2018-08-14 at 10:35
 */
public class DAG {

    private List<Node> nodes;
    Queue<Node> output;

    public DAG() {
        nodes = new ArrayList<Node>();
        output = new ArrayDeque<Node>();
    }

    void insertNode(Node node) {
        nodes.add(node);
    }

}
