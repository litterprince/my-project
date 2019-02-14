package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;
import algorithm.graph.domain.result.ShortestResult;

public class DijkstraComputer extends AbstractComputer<IResult> {
    private final static int NO_ROUTE = Integer.MAX_VALUE;
    private int[] prev;
    private int[] dist;

    private int count;
    private boolean isExist;
    private int shortestCost;

    public DijkstraComputer(IGraph graph) {
        super(graph);
    }

    private void init(){
        shortestCost = 0;
        count = 0;
        isExist = false;
        prev = new int[graph.getVertexNum()];
        dist = new int[graph.getVertexNum()];
    }

    @Override
    public IResult compute(IVertex start) {
        init();

        dijkstra(graph.getPosition(start));
        println(graph.getPosition(start));

        return new Result();
    }

    @Override
    public IResult compute(IVertex start, IVertex end) {
        init();

        dijkstra(graph.getPosition(start));
        println(graph.getPosition(start));

        StringBuilder sb = new StringBuilder();
        getShortestRoute(sb, graph.getPosition(start), graph.getPosition(end));

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
    private void dijkstra(int startIndex) {
        // set true if find shortest rout
        boolean[] isVisited = new boolean[graph.getVertexNum()];

        // init prev and dist array list
        for (int i = 0; i < graph.getVertexNum(); i++) {
            prev[i] = startIndex;
            dist[i] = graph.getWeight(startIndex, i) == 0 ? NO_ROUTE : graph.getWeight(startIndex, i);
            isVisited[i] = false;
        }

        // init vs's data in prev and dist
        isVisited[startIndex] = true;

        // start to compute
        int index = startIndex;
        for (int i = 0; i < graph.getVertexNum(); i++) {
            if (startIndex == i) continue;

            // find the shortest rout form temp to j and record the index
            int min = NO_ROUTE;
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


    }

    private void println(int startIndex){
        // display the result of compute
        System.out.printf("dijkstra(%c): \n", graph.getVertex(startIndex).getValue());
        for (int i = 0; i < graph.getVertexNum(); i++)
            System.out.printf("  shortest(%c, %c)=%d\n", graph.getVertex(prev[i]).getValue(), graph.getVertex(i).getValue(), dist[i]);

        System.out.println();
    }

    private void getShortestRoute(StringBuilder route, int start, int end) {
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
        getShortestRoute(route, start, prev[end]);
    }
}
