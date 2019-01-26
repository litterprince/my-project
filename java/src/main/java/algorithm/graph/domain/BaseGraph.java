package algorithm.graph.domain;

public class BaseGraph implements IGraph {
    @Override
    public int getVertexNum() {
        return 0;
    }

    @Override
    public int getPosition(IVertex t) {
        return 0;
    }

    @Override
    public int getWeight(int t1, int t2) {
        return 0;
    }

    @Override
    public IVertex getVertex(int index) {
        return null;
    }

    @Override
    public IVertex getVertex(char c) {
        return null;
    }

    @Override
    public IVertex[] getReachable(IVertex vertex) {
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
