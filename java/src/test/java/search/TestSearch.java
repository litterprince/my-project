package search;

import algorithm.graph.core.IComputer;
import algorithm.graph.compute.impl.BfsCompute;
import algorithm.graph.compute.impl.DfsCompute;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.array.GraphOfArray;

public class TestSearch {
    public static void main(String[] args){
        String content = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        IGraph graph = new GraphOfArray().buildGraph(content);

        IComputer computer = new DfsCompute(graph);
        computer.compute(graph.getVertex('E'));

        IComputer bfsCompute = new BfsCompute(graph);
        bfsCompute.compute(graph.getVertex('E'));
    }
}
