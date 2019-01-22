package algorithm.graph;

import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
    private static GraphMap trainMap = new GraphMap();
    private static int[][] map;
    public static void main(String[] args){
        trainMap.createMapFromStr("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7", ",");
        map = trainMap.getMap();
        System.out.println(trainMap.toString());

        //dfs(new Vertex('E'));
        bfs(new Vertex('E'));
    }

    /**
     * Depth First Search
     * 1 访问指定的起始顶点
     * 2 若当前访问的顶点的邻接顶点有未被访问的，则任选一个访问之；反之，退回到最近访问过的顶点；直到与起始顶点相通的全部顶点都访问完毕
     * 3 若此时图中尚有顶点未被访问，则再选其中一个顶点作为起始顶点并访问之，转 2； 反之，遍历结束
     */
    public static void dfs(Vertex vertex){
        if(trainMap.getIsVisit(vertex))
            return;

        System.out.print(vertex.getName()+" ");
        for(int i=0;i<map.length;i++){
            if(!trainMap.getIsVisit(vertex) && map[trainMap.getIndex(vertex)][i] > 0){
                trainMap.setIsVisit(vertex);
                dfs(trainMap.getVertex(i));
            }
        }
    }

    /**
     * Broad First Search
     * 从图的某一结点出发，首先依次访问该结点的所有邻接顶点 Vi1, Vi2, …, Vin
     * 再按这些顶点被访问的先后次序依次访问与它们相邻接的所有未被访问的顶点，重复此过程，直至所有顶点均被访问为止
     */
    public static void bfs(Vertex vertex){
        if(trainMap.getIndex(vertex) == -1)
            return;

        Queue<Vertex> queue = new PriorityQueue<>();
        queue.add(vertex);
        trainMap.setIsVisit(vertex);
        System.out.print(vertex.getName() + " ");
        while(queue.size() > 0) {
            Vertex v1 = queue.poll();

            for(int i=0;i<trainMap.vertexNum();i++){
                if(map[trainMap.getIndex(v1)][i] > 0){
                    Vertex v2 = trainMap.getVertex(i);
                    if(!trainMap.getIsVisit(v2)) {
                        queue.add(v2);
                        trainMap.setIsVisit(v2);
                        System.out.print(v2.getName() + " ");
                    }
                }
            }
        }
    }
}
