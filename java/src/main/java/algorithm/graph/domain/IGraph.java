package algorithm.graph.domain;

public interface IGraph {
    int getVertexNum();
    int getPosition(IVertex t);
    int getWeight(int t1, int t2);
    IVertex getVertex(int index);
    IVertex getVertex(char c);
    IVertex[] getReachable(IVertex vertex);
    String getRelations();
    int[][] copyArray();
}
