package algorithm.topology;

public class Main {
    public static void main(String[] args){
        String content = "1,3,1\n" +
                "2,3,1\n" +
                "3,4,1\n" +
                "3,5,1\n" +
                "4,5,1\n";
        Graph graph = new Graph().buildGraph(content);
        graph.topologySort(1);
    }
}
