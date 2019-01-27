package algorithm.graph.core;

import algorithm.graph.domain.IGraph;

public abstract class SearchComputer<T> extends AbstractComputer<T> {
    // if the vertex current index point to is visited, then set true
    private boolean[] isVisit;

    public SearchComputer(IGraph graph) {
        super(graph);
        isVisit = new boolean[graph.getVertexNum()];
    }

    protected void initVisit(){
        for (int i = 0; i < isVisit.length; i++) {
            isVisit[i] = false;
        }
    }

    protected boolean getIsVisit(int index) {
        return isVisit[index];
    }

    protected void setIsVisit(int index) {
        isVisit[index] = true;
    }
}
