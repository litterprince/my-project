package algorithm.graph.domain;

public interface IVertex<T> {
    Character getValue();
    int getInDegree();
    int getOutDegree();
    T getT();
}
