package algorithm.graph.core.impl;

import algorithm.graph.core.SearchComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.IVertex;
import algorithm.graph.domain.result.Result;

public class DfsComputer extends SearchComputer<IResult> {
    public DfsComputer(IGraph graph) {
        super(graph);
    }

    @Override
    public IResult compute(IVertex vertex){
        System.out.print("dfs: ");
        initVisit();
        dfs(vertex);
        System.out.println();
        return new Result();
    }

    /**
     * Depth First Search
     * 1 访问指定的起始顶点
     * 2 若当前访问的顶点的邻接顶点有未被访问的，则任选一个访问之；反之，退回到最近访问过的顶点；直到与起始顶点相通的全部顶点都访问完毕
     * 3 若此时图中尚有顶点未被访问，则再选其中一个顶点作为起始顶点并访问之，转 2； 反之，遍历结束
     */
    private void dfs(IVertex start){
        if(getIsVisit(graph.getPosition(start)))
            return;

        System.out.print(start.getValue()+" ");
        for(int i=0;i<graph.getVertexNum();i++){
            if(!getIsVisit(graph.getPosition(start)) && graph.getWeight(graph.getPosition(start), i) > 0){
                setIsVisit(graph.getPosition(start));
                dfs(graph.getVertex(i));
            }
        }
    }
}
