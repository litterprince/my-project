package algorithm.graph.domain.link;

public class EdgeOfLink {
    private int to;// point to the end VertexOfLink
    private int weight;
    private EdgeOfLink nextEdge;// point next EdgeOfLink

    public void setIvex(int ivex) {
        this.to = ivex;
    }

    public int getIvex() {
        return to;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public EdgeOfLink getNextEdge() {
        return nextEdge;
    }

    public void setNextEdge(EdgeOfLink nextEdge) {
        this.nextEdge = nextEdge;
    }
}
