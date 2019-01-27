package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;

public class PrimComputer extends AbstractComputer<IResult> {
    private final static int MAX_VALUE = Integer.MAX_VALUE;

    public PrimComputer(IGraph graph) {
        super(graph);
    }

    @Override
    public IResult compute(IVertex t) {
        prim(t);
        return new Result();
    }

    /**
     * prim
     * first, let a vertex be the first vertex of min spanning tree and put it in min-set
     * second, form min-set to other vertex not in if,choose the shortest vertex and put it in min-set
     * repeat second step till there's no unvisited vertex
     * @param vertex vertex
     */
    private void prim(IVertex vertex){
        int startIndex = graph.getPosition(vertex);
        int[] prev = new int[graph.getVertexNum()];
        int[] dist = new int[graph.getVertexNum()];
        boolean[] isVisited = new boolean[graph.getVertexNum()];

        // init variables
        for (int i = 0; i < graph.getVertexNum(); i++) {
            prev[i] = startIndex;
            dist[i] = graph.getWeight(startIndex, i) == 0 ? MAX_VALUE : graph.getWeight(startIndex, i);
        }

        // init start vertex's variable
        isVisited[startIndex] = true;

        int index = startIndex;
        for(int i=0;i<graph.getVertexNum();i++){
            if(startIndex == i) continue;

            // find the shortest route from unvisited list to visited list
            int min = MAX_VALUE;
            for (int j = 0; j < graph.getVertexNum(); j++) { // visited list
                if(!isVisited[j] && dist[j] < min){
                    min = dist[j];
                    index = j;
                }
            }

            // set visited
            isVisited[index] = true;

            for (int j = 0; j < graph.getVertexNum(); j++) { // unvisited list
                if(!isVisited[j]){
                    int weight = graph.getWeight(index, j) == 0 ? MAX_VALUE : graph.getWeight(index, j);
                    if(weight < dist[j]){
                        prev[j] = index;
                        dist[j] = weight;
                    }
                }
            }
        }

        // display the result of compute
        System.out.printf("prim(%c): \n", graph.getVertex(startIndex).getValue());
        for (int i = 0; i < graph.getVertexNum(); i++)
            System.out.printf("  shortest(%c, %c)=%d\n", graph.getVertex(prev[i]).getValue(), graph.getVertex(i).getValue(), dist[i]);
    }
}
