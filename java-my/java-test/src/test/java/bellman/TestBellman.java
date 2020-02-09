package bellman;

import algorithm.graph.core.IComputer;
import algorithm.graph.core.impl.BellmanComputer;
import algorithm.graph.core.impl.DijkstraComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.link.GraphOfLink;

public class TestBellman {
    public static void main(String[] args){
        String content = "2,3,2\n"+
                "1,2,-3\n"+
                "1,5,5\n"+
                "4,5,2\n"+
                "3,4,3\n";
        IGraph graph = new GraphOfLink(true).buildGraph(content);
        System.out.println(graph.toString());
        System.out.println(graph.getRelations());
        IComputer computer = new BellmanComputer(graph);
        IResult result = (IResult) computer.compute(graph.getVertex('1'), graph.getVertex('5'));
        System.out.println(result.getMsg());
    }
}
