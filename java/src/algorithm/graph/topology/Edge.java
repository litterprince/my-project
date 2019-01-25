package algorithm.graph.topology;

public class Edge {
    private int weight;
    private Node fromVertex;// start node of edge
    private Node toVertex;// end node of edge

    public Edge(int weight, Node fromVertex, Node toVertex) {
        this.weight = weight;
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }
}
