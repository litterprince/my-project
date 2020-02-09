package algorithm.graph.core.impl;

import algorithm.graph.core.SearchComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;
import algorithm.structure.Queue;

public class BfsComputer extends SearchComputer<IResult> {
    public BfsComputer(IGraph graph) {
        super(graph);
    }

    @Override
    public IResult compute(IVertex vertex){
        System.out.print("bfs: ");
        initVisit();
        bfs(vertex);
        System.out.println();
        return new Result();
    }

    /**
     * Broad First Search
     * 1 从图的某一结点出发，首先依次访问该结点的所有邻接顶点 Vi1, Vi2, …, Vin
     * 2 再按这些顶点被访问的先后次序依次访问与它们相邻接的所有未被访问的顶点，重复此过程，直至所有顶点均被访问为止
     */
    private void bfs(IVertex start){
        if(graph.getPosition(start) == -1)
            return;

        Queue<IVertex> queue = new Queue<>();
        queue.add(start);
        setIsVisit(graph.getPosition(start));
        System.out.print(start.getValue() + " ");
        while(queue.size() > 0) {
            IVertex v1 = queue.poll();
            for(int i = 0; i< graph.getVertexNum(); i++){
                if(graph.getWeight(graph.getPosition(v1), i) > 0){
                    IVertex v2 = graph.getVertex(i);
                    if(!getIsVisit(graph.getPosition(v2))) {
                        queue.add(v2);
                        setIsVisit(graph.getPosition(v2));
                        System.out.print(v2.getValue() + " ");
                    }
                }
            }
        }
    }
}
