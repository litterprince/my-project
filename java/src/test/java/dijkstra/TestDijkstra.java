package dijkstra;

import algorithm.graph.core.IComputer;
import algorithm.graph.core.impl.DijkstraCompute;
import algorithm.graph.domain.BaseGraph;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.link.GraphOfLink;

public class TestDijkstra {
    public static void main(String[] args){
        char[] vertexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        BaseGraph.GraphData[] edges = {
                new BaseGraph.GraphData('A', 'B', 12),
                new BaseGraph.GraphData('A', 'F', 16),
                new BaseGraph.GraphData('A', 'G', 14),
                new BaseGraph.GraphData('B', 'C', 10),
                new BaseGraph.GraphData('B', 'F',  7),
                new BaseGraph.GraphData('C', 'D',  3),
                new BaseGraph.GraphData('C', 'E',  5),
                new BaseGraph.GraphData('C', 'F',  6),
                new BaseGraph.GraphData('D', 'E',  4),
                new BaseGraph.GraphData('E', 'F',  2),
                new BaseGraph.GraphData('E', 'G',  8),
                new BaseGraph.GraphData('F', 'G',  9),
        };
        IGraph graph = new GraphOfLink().buildGraph(vertexes, edges);
        IComputer computer = new DijkstraCompute(graph);
        computer.compute(graph.getVertex('D'));
    }
}
