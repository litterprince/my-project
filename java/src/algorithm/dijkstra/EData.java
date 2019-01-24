package algorithm.dijkstra;

public class EData {
    private char start;// the start vertex
    private char end;// the end vertex
    private int weight;

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public char getStart() {
        return start;
    }

    public char getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }
}
