package ovh.kocproz.dag;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        DAG dag = new DAG();

        Node n1 = new Node("Node1");
        Node n2 = new Node("Node2");
        Node n3 = new Node("Node3");
        n3.addDependency(n2);
        Node n4 = new Node("Node4");
        Node n5 = new Node("Node5");
        n5.addDependencies(n3, n4);

        dag.insertNode(n1);
        dag.insertNode(n2);
        dag.insertNode(n3);
        dag.insertNode(n4);
        dag.insertNode(n5);

    }
}
