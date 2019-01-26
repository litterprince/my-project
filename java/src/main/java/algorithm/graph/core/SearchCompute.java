package algorithm.graph.core;

import algorithm.graph.domain.IGraph;

public abstract class SearchCompute extends AbstractComputer {
    // if the vertex current index point to is visited, then set true
    private boolean[] isVisit;

    public SearchCompute(IGraph graph) {
        super(graph);
        isVisit = new boolean[graph.getVertexNum()];
    }

    public void initVisit(){
        for (int i = 0; i < isVisit.length; i++) {
            isVisit[i] = false;
        }
    }

    public boolean getIsVisit(int index) {
        return isVisit[index];
    }

    public void setIsVisit(int index) {
        isVisit[index] = true;
    }
}
