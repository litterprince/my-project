package algorithm.dijkstra;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * undirected graph
 */
public class Graph {
    private VNode[] mVexs; // VNode array list

    public Graph buildGraph(String content){
        String[] c = content.split("\n");
        char[] vertexes = new char[c.length];
        int count = 0;
        EData[] edges = new EData[c.length];
        for(int i=0;i<c.length;i++){
            String[] list = c[i].split(",");
            char start = list[0].charAt(0);
            count = addVertex(vertexes, start, count);
            char end = list[1].charAt(0);
            count = addVertex(vertexes, end, count);
            EData data = new EData(start, end, Integer.parseInt(list[2]));
            edges[i] = data;
        }
        return buildGraph(vertexes, edges);
    }

    public Graph buildGraph(char[] vertexes, EData[] edges){
        // init variables
        int vLen = vertexes.length;
        int eLen = edges.length;

        // init VNode
        mVexs = new VNode[vLen];
        for(int i = 0; i< mVexs.length; i++){
            mVexs[i] = new VNode();
            mVexs[i].setData(vertexes[i]);
        }

        // init ENode
        for(EData data: edges){
            // read from EData
            char start = data.getStart();
            char end = data.getEnd();
            int weight = data.getWeight();

            // read start and end of edge
            int startIndex = getPosition(start);
            int entIndex = getPosition(end);

            // init node1
            ENode node1 = new ENode();
            node1.setIvex(entIndex);
            node1.setWeight(weight);
            // put node1 to the end of p1
            if(mVexs[startIndex].getFirstEdge() == null){
                mVexs[startIndex].setFirstEdge(node1);
            }else{
                linkLast(mVexs[startIndex].getFirstEdge(), node1);
            }

            // init node2
            ENode node2 = new ENode();
            node2.setIvex(startIndex);
            node2.setWeight(weight);
            // put node2 to the end of p2
            if(mVexs[entIndex].getFirstEdge() == null){
                mVexs[entIndex].setFirstEdge(node2);
            }else{
                linkLast(mVexs[entIndex].getFirstEdge(), node2);
            }
        }
        return this;
    }

    private int addVertex(char[] v, char c, int len){
        boolean find = false;
        for (int i = 0; i < len; i++) {
            if(v[i] == c){
                find = true;
            }
        }

        if(!find){
            v[len] = c;
            len++;
        }

        return len;
    }

    /**
     * put node to the end of list
     * @param list node list
     * @param node node
     */
    private void linkLast(ENode list, ENode node){
        if(list == null || node == null) {
            return;
        }
        ENode end = list;
        while(end.getNextEdge() != null){
            end = end.getNextEdge();
        }
        end.setNextEdge(node);
    }

    private int getPosition(char ch){
        for (int i = 0; i < mVexs.length; i++) {
            if(mVexs[i].getData() == ch){
                return i;
            }
        }
        return -1;
    }

    /**
     * if there's no route then return max value
     * @param start start vertex index
     * @param end end vertex index
     * @return weight
     */
    private int getWeight(int start, int end){
        VNode node = mVexs[start];
        ENode eNode = node.getFirstEdge();
        while (eNode != null){
            if(eNode.getIvex() == end){
                return eNode.getWeight();
            }
            eNode = eNode.getNextEdge();
        }
        return Integer.MAX_VALUE;
    }

    public String getShortestRout(int start, int end){
        AtomicInteger length = new AtomicInteger(0);
        StringBuilder result = new StringBuilder();
        int[] prev = new int[mVexs.length];
        int[] dist = new int[mVexs.length];
        dijkstra(start, prev, dist);
        StringBuilder route = new StringBuilder();
        dfs(start, end, prev, dist, route, length);
        String routeStr = route.append(String.valueOf(mVexs[start].getData())).reverse().toString();
        return result.append("shortest:").append(routeStr).append("; cost:").append(length).toString();
    }

    /**
     * dijkstra: compute the shortest distance between two vertex
     * @param start the start vertex
     * @param prev previous vertex array list, the value of index is the previous vertex of the vertex which current index point to
     * @param dist distance array list, the value of index is the distance start from the previous vertex to the vertex which current index point to
     */
    private void dijkstra(int start, int[] prev, int[] dist){
        // set true if find shortest rout
        boolean[] flag = new boolean[mVexs.length];

        // init prev and dist array list
        for (int i = 0; i < mVexs.length; i++) {
            prev[i] = start;
            dist[i] = getWeight(start, i);
            flag[i] = false;
        }

        // int vs's data in prev and dist
        dist[start] = 0;
        flag[start] = true;

        // start to compute
        int k = start;
        for (int i = 1; i < mVexs.length; i++) {
            // find the shortest rout form temp to j and record the index
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < mVexs.length; j++) {
                if(!flag[j] && dist[j] < min && prev[j] == k){
                    min = dist[j];
                    k = j;
                }
            }

            // set flag's value of k with true
            flag[k] = true;

            // init pre and dist with the new beginning vertex of k
            for (int j = 0; j < mVexs.length; j++) {
                int weight = getWeight(k, j);
                if(!flag[j]){
                    prev[j] = k;
                    dist[j] = weight;
                }
            }
        }

        // display the result of compute
        /*System.out.printf("dijkstra(%c): \n", mVexs[start].getData());
        for (int i = 0; i < mVexs.length; i++)
            System.out.printf("  shortest(%c, %c)=%d\n", mVexs[prev[i]].getData(), mVexs[i].getData(), dist[i]);*/

    }

    private void dfs(int start, int end, int[] prev, int[] dist, StringBuilder route, AtomicInteger length){
        if(start == prev[end]){
            length.addAndGet(dist[end]);
            route.append(mVexs[end].getData());
            return;
        }

        length.addAndGet(dist[end]);
        route.append(mVexs[end].getData());

        dfs(start, prev[end], prev, dist, route, length);
    }
}