package search;

import algorithm.graph.core.IComputer;
import algorithm.graph.domain.IGraph;
import algorithm.graph.domain.array.GraphOfArray;

public class TestSearch {
    public static void main(String[] args){
        String content = "A,B,5\n" +
                "B,C,4\n" +
                "C,D,8\n" +
                "D,C,8\n" +
                "D,E,6\n" +
                "A,D,5\n" +
                "C,E,2\n" +
                "E,B,3\n" +
                "A,E,7";
        IGraph graph = new GraphOfArray(true).buildGraph(content);
        System.out.println(graph.toString());

        IComputer computer = new algorithm.graph.compute.impl.DfsComputer(graph);
        computer.compute(graph.getVertex('E'));

        IComputer bfsCompute = new algorithm.graph.compute.impl.BfsComputer(graph);
        bfsCompute.compute(graph.getVertex('E'));
    }
}
