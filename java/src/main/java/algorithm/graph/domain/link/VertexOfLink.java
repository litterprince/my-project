package algorithm.graph.domain.link;

import algorithm.graph.domain.IVertex;

public class VertexOfLink implements IVertex {
    private Character value;
    private EdgeOfLink firstEdge;// point first EdgeOfLink from this VertexOfLink

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
