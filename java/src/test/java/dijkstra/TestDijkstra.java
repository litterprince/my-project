package dijkstra;

import algorithm.graph.core.IComputer;
import algorithm.graph.core.impl.DijkstraCompute;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.link.GraphOfLink;

public class TestDijkstra {
    public static void main(String[] args){
        char[] vertexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        GraphOfLink.EData[] edges = {
                new GraphOfLink.EData('A', 'B', 12),
                new GraphOfLink.EData('A', 'F', 16),
                new GraphOfLink.EData('A', 'G', 14),
                new GraphOfLink.EData('B', 'C', 10),
                new GraphOfLink.EData('B', 'F',  7),
                new GraphOfLink.EData('C', 'D',  3),
                new GraphOfLink.EData('C', 'E',  5),
                new GraphOfLink.EData('C', 'F',  6),
                new GraphOfLink.EData('D', 'E',  4),
                new GraphOfLink.EData('E', 'F',  2),
                new GraphOfLink.EData('E', 'G',  8),
                new GraphOfLink.EData('F', 'G',  9),
        };
        IGraph graph = new GraphOfLink().buildGraph(vertexes, edges);
        IComputer computer = new DijkstraCompute(graph);
        computer.compute(graph.getVertex('D'));
    }
}
