package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;

public class FloydComputer extends AbstractComputer {
    private final static int NO_ROUTE = Integer.MAX_VALUE;
    private int[][] map;

    public FloydComputer(IGraph graph) {
        super(graph);
    }

    /**
     * zero means there are same vertex
     * no_route means there's no route between two different vertexes
     */
    private void init(){
        map = graph.copyArrayMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(i != j && map[i][j] == 0){
                    map[i][j] = NO_ROUTE;
                }
            }
        }
    }

    @Override
    public Object compute(IVertex start) {
        init();
        floydWarshall();
        return new Result(getResult());
    }

    private void floydWarshall(){
        for (int i = 0; i < graph.getVertexNum(); i++)
            for (int j = 0; j < graph.getVertexNum(); j++)
                for (int k = 0; k < graph.getVertexNum(); k++)
                    //map[j][k]:j -> k      map[j][i]:j -> i     map[i][k]: i -> k
                    if(map[j][i] != NO_ROUTE && map[i][k] != NO_ROUTE && map[j][i] + map[i][k] < map[j][k])
                        map[j][k] = map[j][i] + map[i][k];
    }

    private String getResult(){
        StringBuilder msg = new StringBuilder();

        for (int i = 0; i < graph.getVertexNum(); i++) {
            msg.append("\t").append(graph.getVertex(i).getValue());
        }
        msg.append("\n");

        for (int i = 0; i < graph.getVertexNum(); i++) {
            msg.append(graph.getVertex(i).getValue());
            for (int j = 0; j < graph.getVertexNum(); j++) {
                msg.append("\t").append(map[i][j] == NO_ROUTE ? 0 : (map[i][j]));
            }
            msg.append("\n");
        }
        return msg.toString();
    }
}
