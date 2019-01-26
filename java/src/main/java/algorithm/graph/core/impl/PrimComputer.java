package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;

public class PrimComputer extends AbstractComputer {
    public PrimComputer(IGraph graph) {
        super(graph);
    }

    @Override
    public void compute(IVertex t) {
        prim(t);
    }

    private void prim(IVertex vertex){
        //TODO: this is a error method, need to fixed it
        int startIndex = graph.getPosition(vertex);
        int[] prev = new int[graph.getVertexNum()];
        int[] dist = new int[graph.getVertexNum()];
        boolean[] isVisited = new boolean[graph.getVertexNum()];

        // init variables
        for (int i = 0; i < graph.getVertexNum(); i++) {
            prev[i] = startIndex;
            dist[i] = graph.getWeight(startIndex, i);
            isVisited[i] = false;
        }

        // init start vertex's variable
        isVisited[startIndex] = true;

        for (int i = 0; i < graph.getVertexNum(); i++) {
            int min = Integer.MAX_VALUE;
            int minFrom = -1;
            int minTo = -1;
            for (int j = 0; j < graph.getVertexNum(); j++) { // visited list
                if(!isVisited[j]) continue;

                for (int k = 0; k < graph.getVertexNum(); k++) { // not visited list
                    if(!isVisited[k]){
                        int weight = graph.getWeight(j, k);
                        if(weight!=0 && weight < min){
                            min = weight;
                            minFrom = j;
                            minTo = k;
                        }
                    }
                }
            }
            if(minFrom != -1 || minTo != -1) {
                prev[minTo] = minFrom;
                dist[minTo] = min;
                isVisited[minTo] = true;
            }
        }

        // display the result of compute
        System.out.printf("prim(%c): \n", graph.getVertex(startIndex).getValue());
        for (int i = 0; i < graph.getVertexNum(); i++)
            System.out.printf("  shortest(%c, %c)=%d\n", graph.getVertex(prev[i]).getValue(), graph.getVertex(i).getValue(), dist[i]);
    }
}
