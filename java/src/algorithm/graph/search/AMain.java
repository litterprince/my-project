package algorithm.graph.search;

import algorithm.graph.IComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.array.GraphOfArray;
import algorithm.graph.domain.array.VertexOfArray;

public class AMain {
    public static void main(String[] args){
        String content = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        IGraph graph = new GraphOfArray().buildGraph(content);

        IComputer computer = new DfsCompute(graph);
        computer.compute(graph.getVertex('E'));

        IComputer bfsCompute = new BfsCompute(graph);
        bfsCompute.compute(graph.getVertex('E'));
    }
}
