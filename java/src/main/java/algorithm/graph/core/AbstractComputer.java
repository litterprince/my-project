package algorithm.graph.core;

import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;

public abstract class AbstractComputer implements IComputer {
    protected IGraph graph;

    public AbstractComputer(IGraph graph){
        this.graph = graph;
    }

    @Override
    abstract public void compute(IVertex t);
}
