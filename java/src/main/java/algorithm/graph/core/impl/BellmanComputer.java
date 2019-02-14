package algorithm.graph.core.impl;

import algorithm.graph.common.GraphType;
import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.link.EdgeOfLink;
import algorithm.graph.domain.link.VertexOfLink;
import algorithm.graph.domain.result.Result;
import algorithm.graph.domain.result.ShortestResult;

public class BellmanComputer extends AbstractComputer {
    private final static int NO_ROUTE = Integer.MAX_VALUE;
    private int[] u;// from
    private int[] v;// to
    private int[] w;// weight
    private int edgeNum = 0;
    private int[] dist;// distance
    private int[] prev;// previous vertex

    private int count;
    private boolean isExist;
    private int shortestCost;

    public BellmanComputer(IGraph graph) {
        super(graph);
    }

    private void init(){
        if(!graph.isDirected()){
            throw new RuntimeException("graph must be directed!");
        }
        if(graph.graphType() != GraphType.Link){
            throw new RuntimeException("graph type must be link!");
        }

        u = new int[graph.getVertexNum() * (graph.getVertexNum()-1)];
        v = new int[graph.getVertexNum() * (graph.getVertexNum()-1)];
        w = new int[graph.getVertexNum() * (graph.getVertexNum()-1)];
        dist = new int[graph.getVertexNum()];
        prev = new int[graph.getVertexNum()];

        VertexOfLink[] mVexes = graph.getLinkedVertexList();
        edgeNum = 0;
        for (VertexOfLink vertex : mVexes) {
            EdgeOfLink edge = vertex.getFirstEdge();
            while (edge != null){
                u[edgeNum] = graph.getPosition(vertex);
                v[edgeNum] = edge.getIvex();
                w[edgeNum] = edge.getWeight();
                edge = edge.getNextEdge();
                edgeNum++;
            }
        }
    }

    @Override
    public Object compute(IVertex t) {
        init();

        bellmanFort(graph.getPosition(t));
        println(graph.getPosition(t));

        return new Result();
    }

    @Override
    public Object compute(IVertex start, IVertex end) {
        init();

        bellmanFort(graph.getPosition(start));
        println(graph.getPosition(start));

        StringBuilder shortest = new StringBuilder();
        getShortestRoute(shortest, graph.getPosition(start), graph.getPosition(end));

        if(isExist){
            shortest.append(start.getValue());
        }
        return new ShortestResult(shortest.reverse().toString(), shortestCost);
    }

    private void bellmanFort(int startIndex) {
        // init dist
        for (int i = 0; i < edgeNum; i++) {
            dist[i] = NO_ROUTE;
        }
        dist[startIndex] = 0;

        // compute
        for (int i = 0; i < graph.getVertexNum() - 1; i++) {
            for (int j = 0; j < edgeNum; j++) {
                if(dist[u[j]] != NO_ROUTE && dist[u[j]] + w[j] < dist[v[j]] ){
                    prev[v[j]] = u[j];
                    dist[v[j]] = dist[u[j]] + w[j];
                }
            }
        }
    }

    private void println(int startIndex){
        // display the result of compute
        System.out.printf("bellmanFort(%c): \n", graph.getVertex(startIndex).getValue());
        for (int i = 0; i < graph.getVertexNum(); i++)
            System.out.printf("  shortest(%c, %c)=%d\n", graph.getVertex(prev[i]).getValue(), graph.getVertex(i).getValue(), dist[i]);

        System.out.println();
    }

    private void getShortestRoute(StringBuilder sb, int start, int end) {
        if (prev[end] == start) {
            isExist = true;
            return;
        }

        if (count >= graph.getVertexNum()) {
            sb.append("this's no route between ").append(graph.getVertex(start).getValue())
                    .append(" and ").append(graph.getVertex(end).getValue());
            return;
        }

        shortestCost += dist[end];
        count++;
        sb.append(graph.getVertex(end));
        getShortestRoute(sb, start, prev[end]);
    }
}
