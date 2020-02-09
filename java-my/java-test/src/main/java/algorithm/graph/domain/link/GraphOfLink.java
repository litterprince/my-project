package algorithm.graph.domain.link;

import algorithm.graph.common.GraphType;
import algorithm.graph.domain.AbstractGraph;
import algorithm.graph.domain.IVertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * undirected graph
 */
public class GraphOfLink extends AbstractGraph {
    private VertexOfLink[] mVexes;

    public GraphOfLink(){
        super(false);
    }

    public GraphOfLink(boolean directed){
        super(directed);
    }

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
        GraphData[] edges = new GraphData[c.length];
        for(int i=0;i<c.length;i++){
            String[] list = c[i].split(",");
            char start = list[0].charAt(0);
            count = addVertex(vertexes, start, count);
            char end = list[1].charAt(0);
            count = addVertex(vertexes, end, count);
            GraphData data = new GraphData(start, end, Integer.parseInt(list[2]));
            edges[i] = data;
        }
        return buildGraph(vertexes, edges, count);
    }

    private GraphOfLink buildGraph(char[] vertexes, GraphData[] edges, int vLen){
        // init VertexOfLink
        mVexes = new VertexOfLink[vLen];
        for(int i = 0; i< mVexes.length; i++){
            mVexes[i] = new VertexOfLink();
            mVexes[i].setValue(vertexes[i]);
        }

        for(GraphData data: edges) {
            add(data);
        }
        return this;
    }

    private void add(GraphData data){
        // read from GraphData
        char start = data.getStart();
        char end = data.getEnd();
        int weight = data.getWeight();
        if(start == end) return;

        // from vertex
        int startIndex = getPosition(start);
        VertexOfLink fromVertex = mVexes[startIndex];
        fromVertex.outDegreeIncrement();

        // end vertex
        int entIndex = getPosition(end);
        VertexOfLink toVertex = mVexes[entIndex];
        toVertex.inDegreeIncrement();

        // init from vertex and end vertex
        EdgeOfLink edge1 = new EdgeOfLink();
        edge1.setIvex(entIndex);
        edge1.setWeight(weight);
        if(fromVertex.getFirstEdge() == null){
            fromVertex.setFirstEdge(edge1);
        }else{
            addEdge(fromVertex.getFirstEdge(), edge1);
        }

        // undirected graph
        if(!isDirected()) {
            EdgeOfLink edge2 = new EdgeOfLink();
            edge2.setIvex(startIndex);
            edge2.setWeight(weight);
            // put node2 to the end of p2
            if (toVertex.getFirstEdge() == null) {
                toVertex.setFirstEdge(edge2);
            } else {
                addEdge(toVertex.getFirstEdge(), edge2);
            }
        }
    }

    private void addEdge(EdgeOfLink list, EdgeOfLink node){
        if(list == null || node == null) {
            return;
        }
        EdgeOfLink end = list;
        while(end.getNextEdge() != null){
            end = end.getNextEdge();
        }
        end.setNextEdge(node);
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
    private int getPosition(char ch){
        VertexOfLink node = new VertexOfLink(ch);
        for (int i = 0; i < mVexes.length; i++) {
            if(mVexes[i].equals(node)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getWeight(int start, int end){
        VertexOfLink node = mVexes[start];
        EdgeOfLink edgeOfLink = node.getFirstEdge();
        while (edgeOfLink != null){
            if(edgeOfLink.getIvex() == end){
                return edgeOfLink.getWeight();
            }
            edgeOfLink = edgeOfLink.getNextEdge();
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public int getVertexNum(){
        return mVexes.length;
    }

    @Override
    public IVertex[] getReachable(IVertex vertex) {
        int outDegree = vertex.getOutDegree();
        IVertex[] vertices = new IVertex[outDegree];

        int j = 0;
        EdgeOfLink edge = (EdgeOfLink) vertex.getT();
        while (edge != null && j < outDegree){
            vertices[j] = getVertex(edge.getIvex());
            edge = edge.getNextEdge();
            j ++ ;
        }
        return  vertices;
    }

    @Override
    public String getRelations() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getVertexNum(); i++) {
            sb.append("index ").append(i).append(" point to ").append(mVexes[i].getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public VertexOfLink[] getLinkedVertexList() {
        return mVexes;
    }

    @Override
    public GraphType graphType() {
        return GraphType.Link;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(IVertex vertex : mVexes){
            sb.append(vertex.getValue()).append(" -> ");
            for(IVertex v : getReachable(vertex)){
                sb.append(v.getValue()).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}