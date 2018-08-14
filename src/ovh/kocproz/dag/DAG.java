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

    List<Node> nodes;
    Queue<Node> output;

    public DAG() {
        nodes = new ArrayList<Node>();
        output = new ArrayDeque<Node>();
    }

    void insertNode(Node node) {
        nodes.add(node);
    }

    void generateGraph() {
        List<Node> cycleCrawlerPath = new ArrayList<>();
        for (Node n : nodes) {
            checkForCycles(n, cycleCrawlerPath);
        }

        List<Node> tmpNodes = new ArrayList<Node>(nodes);
        List<Node> toRemove = new ArrayList<Node>();

        for (Node n : nodes)
            if (n.getDependencies().size() == 0) {
                output.add(n);
                tmpNodes.remove(n);
            }

        while (nodes.size() != output.size()) {
            for (Node n : tmpNodes) {
                if (output.containsAll(n.getDependencies())) {
                    output.add(n);
                    toRemove.add(n);
                }
            }
            tmpNodes.removeAll(toRemove);
            toRemove.clear();
        }
    }

    private void checkForCycles(Node n, List<Node> path) {
        if (path.contains(n)) {
            path.add(n);
            throw new CycleFoundException(getPath(path.subList(path.indexOf(n), path.size())));
        }
        path.add(n);
        n.getDependencies().forEach(node -> {
            checkForCycles(node, path);
        });
        path.remove(path.size() - 1);
    }

    String getPath(List<Node> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size() - 1; i++) {
            sb.append(path.get(i).getObject().toString());
            sb.append("->");
        }
        sb.append(path.get(path.size() - 1).getObject().toString());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "DAG{" +
                "nodes=" + nodes.size() +
                '}';
    }
}
