package ovh.kocproz.dag;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        DAG dag = new DAG();

        Random r = new Random();
        Node n = new Node("Node0");
        for (int i = 1; i < 10001; i++) {
            dag.insertNode(n);
            n = new Node("Node" + i);
            n.addDependency(dag.nodes.get(r.nextInt(i)));
        }


        long startTime = System.currentTimeMillis();
        dag.generateGraph();
        long endTime = System.currentTimeMillis();

        System.out.println("Graph with " + dag.nodes.size() + " nodes generated in " + (endTime - startTime) + " miliseconds.");

//        printNodes(dag);
//        printQueue(dag);
    }

    static void printQueue(DAG dag) {
        System.out.println("=== Output Queue ===");
        for (Node n : dag.output)
            System.out.println(n.toString());
    }

    static void printNodes(DAG dag) {
        System.out.println("=== List on Nodes ===");
        for (Node n : dag.nodes)
            System.out.println(n.toString());
    }

}
