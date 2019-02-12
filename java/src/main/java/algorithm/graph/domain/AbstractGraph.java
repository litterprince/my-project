package algorithm.graph.domain;

public abstract class AbstractGraph implements IGraph {
    // false mean this graph is undirected graph and it is default value
    private boolean directed;

    public AbstractGraph(boolean directed) {
        this.directed = directed;
    }

    protected boolean isDirected() {
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
    public int[][] copyArray() {
        return null;
    }

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
