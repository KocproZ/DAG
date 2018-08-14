package ovh.kocproz.dag;

import java.util.LinkedList;
import java.util.List;

/**
 * Parent --> Child
 *
 * @author KocproZ
 * Created 2018-08-14 at 10:35
 */
public class Node<T> {

    private List<Node> parents;
    private List<Node> children;
    private T object;

    protected Node(T object) {
        this.object = object;
        parents = new LinkedList<>();
        children = new LinkedList<>();
    }

    public T getObject() {
        return object;
    }

    void addParent(Node parent) {
        parent.addChild(this);
        parents.add(parent);
    }

    List<Node> getParents() {
        return parents;
    }

    List<Node> getChildren() {
        return children;
    }

    public void addParents(Node... dep) {
        for (Node n : dep) {
            n.addChild(this);
            parents.add(n);
        }
    }

    void addChild(Node dep) {
        if (dep == this) throw new CycleFoundException(this.toString() + "->" + this.toString());
        children.add(dep);
    }

    @Override
    public String toString() {
        return "Node{" +
                "parents=" + parents.size() +
                ", children=" + children.size() +
                ", object=" + object.toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node)
            return object.equals(((Node) obj).getObject());
        else return false;
    }
}
