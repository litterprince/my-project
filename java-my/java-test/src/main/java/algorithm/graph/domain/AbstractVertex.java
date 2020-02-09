package algorithm.graph.domain;

public abstract class AbstractVertex<T> implements IVertex<T> {
    @Override
    abstract public Character getValue();

    @Override
    abstract public int getInDegree();

    @Override
    abstract public int getOutDegree();

    @Override
    public T getT(){
        return null;
    }
}
