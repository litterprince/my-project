package algorithm.graph.core;

import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;

public abstract class AbstractComputer<T> implements IComputer<T> {
    protected IGraph graph;

    public AbstractComputer(IGraph graph){
        this.graph = graph;
    }

    @Override
    abstract public T compute(IVertex t);

    @Override
    public T compute(IVertex start, IVertex end){
        return null;
    }
}
