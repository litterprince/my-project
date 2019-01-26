package algorithm.graph.domain.link;

import algorithm.graph.domain.IVertex;

public class VertexOfLink implements IVertex<EdgeOfLink> {
    private Character value;
    private EdgeOfLink firstEdge;// point first EdgeOfLink from this VertexOfLink
    private int inDegree;
    private int outDegree;

    public VertexOfLink(){}

    public VertexOfLink(char value) {
        this.value = value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    @Override
    public Character getValue() {
        return value;
    }

    public void inDegreeIncrement(){
        inDegree ++;
    }

    @Override
    public int getInDegree() {
        return inDegree;
    }

    public void outDegreeIncrement(){
        outDegree ++;
    }

    @Override
    public int getOutDegree() {
        return outDegree;
    }

    @Override
    public EdgeOfLink getT() {
        return firstEdge;
    }

    public void setFirstEdge(EdgeOfLink firstEdge) {
        this.firstEdge = firstEdge;
    }

    public EdgeOfLink getFirstEdge() {
        return firstEdge;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(VertexOfLink.class)){
            return false;
        }
        return value.toString().equals(obj.toString());
    }
}
