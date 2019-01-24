package algorithm.dijkstra;

public class VNode {
    private char data;
    private ENode firstEdge;// point first ENode from this VNode

    public void setData(char data) {
        this.data = data;
    }

    public char getData() {
        return data;
    }

    public void setFirstEdge(ENode firstEdge) {
        this.firstEdge = firstEdge;
    }

    public ENode getFirstEdge() {
        return firstEdge;
    }
}
