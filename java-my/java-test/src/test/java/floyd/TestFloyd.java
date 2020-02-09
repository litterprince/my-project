package floyd;

import algorithm.graph.core.IComputer;
import algorithm.graph.core.impl.FloydComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.IResult;
import algorithm.graph.domain.array.GraphOfArray;

public class TestFloyd {
    public static void main(String[] args){
        String content = "1,2,2\n"+
                "1,3,6\n"+
                "1,4,4\n"+
                "2,3,3\n"+
                "3,1,7\n"+
                "3,4,1\n"+
                "4,1,5\n"+
                "4,3,12\n";
        IGraph graph = new GraphOfArray(true).buildGraph(content);
        System.out.println(graph.toString());
        IComputer computer = new FloydComputer(graph);
        IResult result = (IResult) computer.compute(graph.getVertex('3'));
        System.out.println(result.getMsg());
    }
}
