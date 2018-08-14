package ovh.kocproz.dag;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author KocproZ
 * Created 2018-08-14 at 10:35
 */
public class DAG<T> {

    private Map<T, Node<T>> nodes;
    private List<Node<T>> roots;

    public DAG() {
        nodes = new LinkedHashMap<>();
        roots = new ArrayList<>();
    }

    public void visit(Consumer<Node<T>> consumer) {
        Set<Node<T>> visited = new HashSet<>();
        for (Node<T> node : roots) {
            consumer.accept(node);
            node.getChildren().forEach(n -> n.visit(consumer, visited));
        }
    }

    /**
     * Creates node with given object
     *
     * @param object to create node with
     * @return Node with given object
     */
    public Node<T> createNode(T object) {
        Node<T> node = new Node<>(object);
        nodes.put(object, node);
        return node;
    }

    void findRoots() {
        for (Node<T> n : nodes.values()) {
            if (n.getParents().size() == 0)
                roots.add(n);
        }
    }

    public void checkForCycles() {
        List<Node<T>> cycleCrawlerPath = new ArrayList<>();
        for (Node<T> n : roots) {
            checkForCycles(n, cycleCrawlerPath);
        }
    }

    private void checkForCycles(Node<T> n, List<Node<T>> path) {
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
     * n1 --> n2
     *
     * @param n1 Parent
     * @param n2 child
     */
    public void addEdge(T n1, T n2) {
        nodes.get(n2).addParent(nodes.get(n1));
        nodes.get(n1).addChild(nodes.get(n2));
    }

    public Node<T> getNode(T key) {
        return nodes.get(key);
    }

    public Collection<Node<T>> getNodes() {
        return nodes.values();
    }

    String getPath(List<Node<T>> path) {
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
