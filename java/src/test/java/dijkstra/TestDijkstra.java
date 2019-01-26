package dijkstra;

import algorithm.graph.core.IComputer;
import algorithm.graph.core.impl.DijkstraCompute;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.link.GraphOfLink;

public class TestDijkstra {
    public static void main(String[] args){
        String content  = "A,B,12\n"+
                "A,F,16\n"+
                "A,G,14\n"+
                "B,C,10\n"+
                "B,F,7\n"+
                "C,D,3\n"+
                "C,E,5\n"+
                "C,F,6\n"+
                "D,E,4\n"+
                "E,F,2\n"+
                "E,G,8\n"+
                "F,G,9\n";
        IGraph graph = new GraphOfLink().buildGraph(content);
        System.out.println(graph.toString());
        IComputer computer = new DijkstraCompute(graph);
        computer.compute(graph.getVertex('D'));
    }
}
