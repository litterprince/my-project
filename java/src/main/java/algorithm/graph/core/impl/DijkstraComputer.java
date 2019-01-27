package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;
import algorithm.graph.domain.result.ShortestResult;

public class DijkstraComputer extends AbstractComputer<IResult> {
    private final static int MAX_VALUE = Integer.MAX_VALUE;
    private int shortestCost; //TODO: it is thread safe if install with AtomInteger
    private int count;
    private boolean isExist;

    public DijkstraComputer(IGraph graph) {
        super(graph);
    }

    @Override
    public IResult compute(IVertex start) {
        int[] prev = new int[graph.getVertexNum()];
        int[] dist = new int[graph.getVertexNum()];
        dijkstra(graph.getPosition(start), prev, dist);
        return new Result();
    }

    @Override
    public IResult compute(IVertex start, IVertex end) {
        initParams();
        int[] prev = new int[graph.getVertexNum()];
        int[] dist = new int[graph.getVertexNum()];
        dijkstra(graph.getPosition(start), prev, dist);
        StringBuilder sb = new StringBuilder();
        getShortestRoute(sb, graph.getPosition(start), graph.getPosition(end), prev, dist);
        if(isExist){
            sb.append(start.getValue());
        }
        return new ShortestResult(sb.reverse().toString(), shortestCost);
    }

    /**
     * dijkstra: compute the shortest distance between two vertex
     * @param startIndex the start vertex
     * prev previous vertex array list, the value of index is the previous vertex of the vertex which current index point to
     * dist distance array list, the value of index is the distance start from the previous vertex to the vertex which current index point to
     */
    private void dijkstra(int startIndex, int[] prev, int[] dist) {
        // set true if find shortest rout
        boolean[] isVisited = new boolean[graph.getVertexNum()];

        // init prev and dist array list
        for (int i = 0; i < graph.getVertexNum(); i++) {
            prev[i] = startIndex;
            dist[i] = graph.getWeight(startIndex, i) == 0 ? MAX_VALUE : graph.getWeight(startIndex, i);
            isVisited[i] = false;
        }

        // init vs's data in prev and dist
        isVisited[startIndex] = true;

        // start to compute
        int index = startIndex;
        for (int i = 0; i < graph.getVertexNum(); i++) {
            if (startIndex == i) continue;

            // find the shortest rout form temp to j and record the index
            int min = MAX_VALUE;
            for (int j = 0; j < graph.getVertexNum(); j++) {
                if (!isVisited[j] && dist[j] < min) {
                    min = dist[j];
                    index = j;
                }
            }

            // set flag's value of k with true
            isVisited[index] = true;

            // init pre and dist with the new beginning vertex of k
            for (int j = 0; j < graph.getVertexNum(); j++) {
                int weight = graph.getWeight(index, j);
                if (!isVisited[j]) {
                    prev[j] = index;
                    dist[j] = weight;
                }
            }
        }

        // display the result of compute
        System.out.printf("dijkstra(%c): \n", graph.getVertex(startIndex).getValue());
        for (int i = 0; i < graph.getVertexNum(); i++)
            System.out.printf("  shortest(%c, %c)=%d\n", graph.getVertex(prev[i]).getValue(), graph.getVertex(i).getValue(), dist[i]);

    }

    private void getShortestRoute(StringBuilder route, int start, int end, int[] prev, int[] dist) {
        if (prev[end] == start) {
            isExist = true;
            return;
        }

        if (count >= graph.getVertexNum()) {
            route.append("this is no route between ").append(graph.getVertex(start).getValue())
                    .append(" and ").append(graph.getVertex(end).getValue());
            return;
        }

        shortestCost += dist[end];
        count++;
        route.append(graph.getVertex(end));
        getShortestRoute(route, start, prev[end], prev, dist);
    }

    private void initParams(){
        shortestCost = 0;
        count = 0;
        isExist = false;
    }
}
