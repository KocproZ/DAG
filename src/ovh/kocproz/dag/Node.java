package ovh.kocproz.dag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KocproZ
 * Created 2018-08-14 at 10:35
 */
public class Node {

    List<Node> dependencies;
    List<Node> dependants;
    Object object;

    public Node(Object object) {
        this.object = object;
        dependencies = new ArrayList<Node>();
        dependants = new ArrayList<Node>();
    }

    public Object getObject() {
        return object;
    }

    void addDependency(Node dep) {
        dep.addDependant(this);
        dependencies.add(dep);
    }

    List<Node> getDependencies() {
        return dependencies;
    }

    public void addDependencies(Node... dep) {
        for (Node n : dep) {
            n.addDependant(this);
            dependencies.add(n);
        }
    }

    private void addDependant(Node dep) {
        if (dep == this) throw new CycleFoundException(this.toString() + "->" + this.toString());
        dependants.add(dep);
    }

    @Override
    public String toString() {
        return "Node{" +
                "dependencies=" + dependencies.size() +
                ", dependants=" + dependants.size() +
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
