package algorithm.dijkstra;

public class Main {
    public static void main(String[] args){
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        EData[] edges = {
                new EData('A', 'B', 12),
                new EData('A', 'F', 16),
                new EData('A', 'G', 14),
                new EData('B', 'C', 10),
                new EData('B', 'F',  7),
                new EData('C', 'D',  3),
                new EData('C', 'E',  5),
                new EData('C', 'F',  6),
                new EData('D', 'E',  4),
                new EData('E', 'F',  2),
                new EData('E', 'G',  8),
                new EData('F', 'G',  9),
        };
        Graph graph = new Graph().buildGraph(vexs, edges);
        System.out.println(graph.getShortestRout(3, 0));
    }
}
