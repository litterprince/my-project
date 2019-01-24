package algorithm.dijkstra;

public class ENode {
    private int ivex;// point to the end VNode
    private int weight;
    private ENode nextEdge;// point next ENode

    public void setIvex(int ivex) {
        this.ivex = ivex;
    }

    public int getIvex() {
        return ivex;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public ENode getNextEdge() {
        return nextEdge;
    }

    public void setNextEdge(ENode nextEdge) {
        this.nextEdge = nextEdge;
    }
}
