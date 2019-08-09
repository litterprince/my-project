package algorithm.graph.core;

import algorithm.graph.domain.IVertex;

public interface IComputer<T> {
    T compute(IVertex i);

    T compute(IVertex start, IVertex end);
}
