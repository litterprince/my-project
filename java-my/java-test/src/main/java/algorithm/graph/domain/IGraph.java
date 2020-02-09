package algorithm.graph.domain;

import algorithm.graph.common.GraphType;
import algorithm.graph.domain.link.VertexOfLink;

public interface IGraph {
    int getVertexNum();
    int getPosition(IVertex t);
    int getWeight(int t1, int t2);
    IVertex getVertex(int index);
    IVertex getVertex(char c);
    IVertex[] getReachable(IVertex vertex);
    String getRelations();
    int[][] copyArrayMap();
    VertexOfLink[] getLinkedVertexList();
    boolean isDirected();
    GraphType graphType();
}
