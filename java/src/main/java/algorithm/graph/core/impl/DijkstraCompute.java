package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;

public class DijkstraCompute extends AbstractComputer {

    public DijkstraCompute(IGraph graph) {
        super(graph);
    }

    public void compute(IVertex start){
        dijkstra(graph.getPosition(start));
    }

    /**
     * dijkstra: compute the shortest distance between two vertex
     * @param start the start vertex
     * prev previous vertex array list, the value of index is the previous vertex of the vertex which current index point to
     * dist distance array list, the value of index is the distance start from the previous vertex to the vertex which current index point to
     */
    private void dijkstra(int start){
        // set true if find shortest rout
        int[] prev = new int[graph.getVertexNum()];
        int[] dist = new int[graph.getVertexNum()];
        boolean[] flag = new boolean[graph.getVertexNum()];

        // init prev and dist array list
        for (int i = 0; i < graph.getVertexNum(); i++) {
            prev[i] = start;
            dist[i] = graph.getWeight(start, i);
            flag[i] = false;
        }

        // int vs's data in prev and dist
        dist[start] = 0;
        flag[start] = true;

        // start to compute
        int k = start;
        for (int i = 1; i < graph.getVertexNum(); i++) {
            // find the shortest rout form temp to j and record the index
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < graph.getVertexNum(); j++) {
                if(!flag[j] && dist[j] < min && prev[j] == k){
                    min = dist[j];
                    k = j;
                }
            }

            // set flag's value of k with true
            flag[k] = true;

            // init pre and dist with the new beginning vertex of k
            for (int j = 0; j < graph.getVertexNum(); j++) {
                int weight = graph.getWeight(k, j);
                if(!flag[j]){
                    prev[j] = k;
                    dist[j] = weight;
                }
            }
        }

        // display the result of compute
        System.out.printf("dijkstra(%c): \n", graph.getVertex(start).getValue());
        for (int i = 0; i < graph.getVertexNum(); i++)
            System.out.printf("  shortest(%c, %c)=%d\n", graph.getVertex(prev[i]).getValue(), graph.getVertex(i).getValue(), dist[i]);

    }
}
