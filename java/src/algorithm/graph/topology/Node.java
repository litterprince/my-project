package algorithm.graph.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Node {
    private int value;
    private AtomicInteger in;// in-degree
    private AtomicInteger out;// out-degree
    private List<Node> nexts;// the nodes that i can visit
    private List<Edge> edges;// from me the edges that i can visit the other nodes

    public Node(int value) {
        this.value = value;
        this.in = new AtomicInteger(0);
        this.out = new AtomicInteger(0);
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void incrementIn(){
        in.incrementAndGet();
    }

    public void decrementIn(){
        in.decrementAndGet();
    }

    public void incrementOut(){
        out.incrementAndGet();
    }

    public int decrementOut(){
        return out.decrementAndGet();
    }

    public void addNext(Node node){
        if(!nexts.contains(node)){
            nexts.add(node);
        }
    }

    public void addEdge(Edge edge){
        if(!edges.contains(edge)){
            edges.add(edge);
        }
    }

    public List<Node> getNexts() {
        return nexts;
    }

    public int getIn() {
        return in.get();
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj){
        // must return false if the explicit parameter is null or if the class don't match,they can't be equal
        if (obj == null || getClass() != obj.getClass())
            return false;
        // a quick test to see if the objects are identical
        if (this == obj)
            return true;
        return ((Node) obj).getValue() == this.getValue();
    }

    @Override
    public int hashCode(){
        return this.getValue();
    }
}
