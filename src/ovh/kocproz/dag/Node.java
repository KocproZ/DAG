package ovh.kocproz.dag;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Parent --> Child
 *
 * @author KocproZ
 * Created 2018-08-14 at 10:35
 */
public class Node {

    List<Node> parents;
    List<Node> children;
    Object object;

    public Node(Object object) {
        this.object = object;
        parents = new ArrayList<Node>();
        children = new ArrayList<Node>();
    }

    public Object getObject() {
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
