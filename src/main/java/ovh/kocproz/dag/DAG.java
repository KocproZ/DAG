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

    /**
     * Finds root nodes and checks for cycles
     */
    public void update() {
        findRoots();
        checkForCycles();
    }

    private void findRoots() {
        for (Node<T> n : nodes.values()) {
            if (n.getParents().size() == 0)
                roots.add(n);
        }
    }

    private void checkForCycles() {
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
     * @param parent Parent
     * @param child Child
     */
    public void addEdge(T parent, T child) {
        Node<T> parentNode = getNode(parent);
        Node<T> childNode = getNode(child);
        if (parentNode == null) parentNode = createNode(parent);
        if (childNode == null) childNode = createNode(child);
        parentNode.addChild(childNode);
    }

    public Node<T> getNode(T key) {
        return nodes.get(key);
    }

    public Collection<Node<T>> getNodes() {
        return nodes.values();
    }

    private String getPath(List<Node<T>> path) {
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
