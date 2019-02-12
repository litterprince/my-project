package algorithm.graph.core.impl;

import algorithm.graph.core.AbstractComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;

public class FloydComputer extends AbstractComputer {
    private final static int NO_ROUT = Integer.MAX_VALUE;
    private int[][] map;

    public FloydComputer(IGraph graph) {
        super(graph);
        map = graph.copyArray();
    }

    @Override
    public Object compute(IVertex start) {
        floyd();
        return new Result(result());
    }

    private void floyd(){
        for (int i = 0; i < graph.getVertexNum(); i++) {
            for (int j = 0; j < graph.getVertexNum(); j++) {
                for (int k = 0; k < graph.getVertexNum(); k++) {
                    if(j == k){
                        map[j][k] = NO_ROUT;
                        continue;
                    }

                    int w1 = map[j][k] == 0 ? NO_ROUT : map[j][k]; // j -> k
                    int w2 = map[j][i] == 0 ? NO_ROUT : map[j][i]; // j -> i
                    int w3 = map[i][k] == 0 ? NO_ROUT : map[i][k]; // i -> k
                    map[j][k] = w1;

                    if(w2 != NO_ROUT && w3 != NO_ROUT && w2 + w3 < w1){
                        map[j][k] = w2 + w3;
                    }
                }
            }
        }
    }

    private String result(){
        StringBuilder msg = new StringBuilder();

        for (int i = 0; i < graph.getVertexNum(); i++) {
            msg.append("\t").append(graph.getVertex(i).getValue());
        }
        msg.append("\n");

        for (int i = 0; i < graph.getVertexNum(); i++) {
            msg.append(graph.getVertex(i).getValue());
            for (int j = 0; j < graph.getVertexNum(); j++) {
                msg.append("\t").append(map[i][j] == NO_ROUT ? 0 : (map[i][j]));
            }
            msg.append("\n");
        }
        return msg.toString();
    }
}
