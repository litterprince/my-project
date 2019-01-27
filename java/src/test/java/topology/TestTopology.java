package topology;

import algorithm.graph.core.IComputer;
import algorithm.graph.core.impl.TopologyComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.link.GraphOfLink;
import algorithm.graph.domain.link.VertexOfLink;

public class TestTopology {
    public static void main(String[] args){
        String content = "A,C,1\n" +
                "B,C,1\n" +
                "C,D,1\n" +
                "C,E,1\n" +
                "D,E,1\n";
        IGraph graph = new GraphOfLink(true).buildGraph(content);
        System.out.println(graph.toString());
        IComputer computer = new TopologyComputer(graph);
        computer.compute(new VertexOfLink('A'));
    }
}
