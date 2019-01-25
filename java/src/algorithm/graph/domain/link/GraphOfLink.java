package algorithm.graph.domain.link;

import algorithm.graph.domain.BaseGraph;
import algorithm.graph.domain.IVertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * undirected graph
 */
public class GraphOfLink extends BaseGraph {
    private VertexOfLink[] mVexes;

    public GraphOfLink buildGraph(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            String line = "";
            Scanner sca = new Scanner(file);
            while (sca.hasNext()) {
                line = sca.next();
                sb.append(line).append("\n");
            }
            sca.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return buildGraph(sb.toString());
    }

    public GraphOfLink buildGraph(String content){
        return buildGraph(content, "\n");
    }

    public GraphOfLink buildGraph(String content, String splitStr){
        String[] c = content.split(splitStr);
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

    public GraphOfLink buildGraph(char[] vertexes, EData[] edges){
        // init variables
        int vLen = vertexes.length;

        // init VertexOfLink
        mVexes = new VertexOfLink[vLen];
        for(int i = 0; i< mVexes.length; i++){
            mVexes[i] = new VertexOfLink();
            mVexes[i].setValue(vertexes[i]);
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
            if(mVexes[startIndex].getFirstEdge() == null){
                mVexes[startIndex].setFirstEdge(node1);
            }else{
                linkLast(mVexes[startIndex].getFirstEdge(), node1);
            }

            // init node2
            ENode node2 = new ENode();
            node2.setIvex(startIndex);
            node2.setWeight(weight);
            // put node2 to the end of p2
            if(mVexes[entIndex].getFirstEdge() == null){
                mVexes[entIndex].setFirstEdge(node2);
            }else{
                linkLast(mVexes[entIndex].getFirstEdge(), node2);
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

    @Override
    public IVertex getVertex(int index){
        return mVexes[index];
    }

    @Override
    public IVertex getVertex(char c) {
        for (int i = 0; i < mVexes.length; i++) {
            if(mVexes[i].getValue() == c){
                return mVexes[i];
            }
        }
        return null;
    }

    @Override
    public int getPosition(IVertex node){
        return getPosition(node.getValue());
    }

    @Override
    public int getWeight(int start, int end){
        VertexOfLink node = mVexes[start];
        ENode eNode = node.getFirstEdge();
        while (eNode != null){
            if(eNode.getIvex() == end){
                return eNode.getWeight();
            }
            eNode = eNode.getNextEdge();
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public int getVertexNum(){
        return mVexes.length;
    }

    private int getPosition(char ch){
        VertexOfLink node = new VertexOfLink(ch);
        for (int i = 0; i < mVexes.length; i++) {
            if(mVexes[i].equals(node)){
                return i;
            }
        }
        return -1;
    }

    public static class EData {
        private char start;// the start vertex
        private char end;// the end vertex
        private int weight;// weight

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
}