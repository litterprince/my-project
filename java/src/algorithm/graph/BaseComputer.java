package algorithm.graph;

import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;

public class BaseComputer implements IComputer {
    protected IGraph graph;

    public BaseComputer(IGraph graph){
        this.graph = graph;
    }

    @Override
    public void compute(IVertex t) {

    }
}
