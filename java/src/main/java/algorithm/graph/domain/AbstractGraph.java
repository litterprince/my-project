package algorithm.graph.domain;

import algorithm.graph.common.GraphType;
import algorithm.graph.domain.link.VertexOfLink;

import java.util.List;

public abstract class AbstractGraph implements IGraph {
    // false mean this graph is undirected graph and it is default value
    private boolean directed;

    public AbstractGraph(boolean directed) {
        this.directed = directed;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    abstract public int getVertexNum();

    @Override
    abstract public int getPosition(IVertex t);

    @Override
    abstract public int getWeight(int t1, int t2);

    @Override
    abstract public IVertex getVertex(int index);

    @Override
    abstract public IVertex getVertex(char c);

    @Override
    abstract public IVertex[] getReachable(IVertex vertex);

    @Override
    public int[][] copyArrayMap() {
        return null;
    }

    @Override
    public VertexOfLink[] getLinkedVertexList() {
        return null;
    }

    @Override
    abstract public GraphType graphType();

    public static class GraphData {
        private char start;// the start vertex
        private char end;// the end vertex
        private int weight;// weight

        public GraphData(char start, char end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public char getStart() {
            return start;
        }

        public char getEnd() {
            return end;
        }

        public int getWeight() {
            return weight;
        }
    }
}
