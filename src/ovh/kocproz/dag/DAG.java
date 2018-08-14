package ovh.kocproz.dag;

import java.util.*;

/**
 * @author KocproZ
 * Created 2018-08-14 at 10:35
 */
public class DAG {

    private Map<Object, Node> nodes;
    private List<Node> roots;
    Queue<Node> output;

    public DAG() {
        nodes = new LinkedHashMap<>();
        roots = new ArrayList<>();
        output = new ArrayDeque<>();
    }

    void insertNode(Node node) {
        nodes.put(node.getObject(), node);
    }

    void generateGraph() {
        List<Node> cycleCrawlerPath = new ArrayList<>();
        for (Node n : nodes.values()) {
            checkForCycles(n, cycleCrawlerPath);
        }

        List<Node> tmpNodes = new ArrayList<Node>(nodes.values());
        List<Node> toRemove = new ArrayList<Node>();

        while (nodes.size() != output.size()) {
            for (Node n : tmpNodes) {
                if (output.containsAll(n.getParents()) || n.getParents().size() == 0) {
                    output.add(n);
                    toRemove.add(n);
                }
                if (n.getParents().size() == 0)
                    roots.add(n);
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
        n.getParents().forEach(node -> {
            checkForCycles(node, path);
        });
        path.remove(path.size() - 1);
    }

    /**
     * n1-->n2
     *
     * @param n1
     * @param n2
     */
    public void addEdge(Object n1, Object n2) {
        nodes.get(n2).addParent(nodes.get(n1));
        nodes.get(n1).addChild(nodes.get(n2));
    }

    public Node getNode(Object key) {
        return nodes.get(key);
    }

    public Collection<Node> getNodes() {
        return nodes.values();
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
