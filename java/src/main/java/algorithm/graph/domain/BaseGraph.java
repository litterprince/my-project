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
}
