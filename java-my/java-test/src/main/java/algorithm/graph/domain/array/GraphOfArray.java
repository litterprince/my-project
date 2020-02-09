package algorithm.graph.domain.array;

import algorithm.graph.common.GraphType;
import algorithm.graph.domain.AbstractGraph;
import algorithm.graph.domain.IVertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphOfArray extends AbstractGraph {
	private final static int SCALE_INCREMENT = 10; // scale increment
	private int scale = 5;// initial value of scale
	private int[][] map;// adjacency matrix
	private VertexOfArray[] vertexList;// vertex list
	private int vertexNum;// the number of vertex

    // init method
    public GraphOfArray() {
        super(false);
        this.vertexList = new VertexOfArray[scale];
        this.map = new int[scale][scale];
    }

    public GraphOfArray(boolean directed){
        super(directed);
        this.vertexList = new VertexOfArray[scale];
        this.map = new int[scale][scale];
    }

    public GraphOfArray buildGraph(File file) {
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

    public GraphOfArray buildGraph(String content){
	    return buildGraph(content, "\n");
    }

    public GraphOfArray buildGraph(String str, String splitStr) {
        String[] trainLineStr = str.split(splitStr);
        for (String string : trainLineStr) {
            add(string);
        }
        return this;
    }

    private void add(String string) {
        EdgeOfArray edgeOfArray = addEdge(string);

        if (edgeOfArray != null && edgeOfArray.isRightLine()) {
            // if scale is enough
            if (scale < getVertexNum() + 2) {
                expansion();
            }

            // add from vertex
            VertexOfArray from = edgeOfArray.getFrom();
            from.OutDegreeIncrement();
            int fromIndex = addVertex(from);

            // add in vertex
            VertexOfArray to = edgeOfArray.geTo();
            to.InDegreeIncrement();
            int toIndex = addVertex(to);

            map[fromIndex][toIndex] = edgeOfArray.getLength();
            // undirected graph
            if(!isDirected()){
                map[toIndex][fromIndex] = edgeOfArray.getLength();
            }
        }
    }

    private EdgeOfArray addEdge(String string) {
        String[] c = string.split(",");
        if(c.length < 3) return null;

        VertexOfArray from = new VertexOfArray(c[0].charAt(0));
        VertexOfArray to = new VertexOfArray(c[1].charAt(0));
        Integer length = Integer.valueOf(c[2]);
        return new EdgeOfArray(from, to, length);
    }

	private int addVertex(VertexOfArray vertex) {
		// if exist
        int index = getPosition(vertex);
        if (index > -1) {
			return index;
		}
		// if not exist
        vertexList[getVertexNum()] = vertex;
		return getAndAddNum();
	}

    @Override
    public VertexOfArray getVertex(int index){
        if(index < getVertexNum() && vertexList[index] != null){
            return vertexList[index];
        }
        return null;
    }

    @Override
    public IVertex getVertex(char c){
        for (int i = 0; i < getVertexNum(); i++) {
            if(vertexList[i].getValue() == c) {
                return vertexList[i];
            }
        }
        return null;
    }

    @Override
    public int getPosition(IVertex vertex){
        for (int i = 0; i < getVertexNum(); i++) {
            if(vertex.equals(vertexList[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getVertexNum() {
        return vertexNum;
    }

    @Override
    public int getWeight(int t1, int t2) {
        return map[t1][t2];
    }

    @Override
    public IVertex[] getReachable(IVertex vertex) {
        int outDegree = vertex.getOutDegree();
        IVertex[] vertices = new IVertex[outDegree];
        int index = getPosition(vertex);
        int j = 0;
        for (int i = 0; i < getVertexNum() && j < outDegree; i++) {
            if(getWeight(index, i) > 0 || index != i) {
                vertices[j] = getVertex(i);
                j ++;
            }
        }
        return  vertices;
    }

    @Override
    public String getRelations() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getVertexNum(); i++) {
            sb.append("index ").append(i).append(" point to ").append(vertexList[i].getValue()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int[][] copyArrayMap(){
        // expand map array
        int[][] newMap = new int[vertexNum][vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, vertexNum);
        }
        return newMap;
    }

    @Override
    public GraphType graphType() {
        return GraphType.Array;
    }

    private int getAndAddNum(){
        return vertexNum++;
    }

    private void expansion() {
        scale += SCALE_INCREMENT;

        // expand map array
        int[][] newMap = new int[scale][scale];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, map.length);
        }
        map = newMap;

        // expand vertex array
        VertexOfArray[] newVertexOfArrays = new VertexOfArray[scale];
        System.arraycopy(vertexList, 0, newVertexOfArrays, 0, vertexList.length);
        vertexList = newVertexOfArrays;
    }

    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getVertexNum() == 0) {
			return "No Vertex !";
		}

        for (int i = 0; i < getVertexNum(); i++) {
            sb.append("\t").append(vertexList[i].toString());
        }
		sb.append("\n");

		for (int i = 0; i < getVertexNum(); i++) {
			sb.append(getVertex(i));
			for (int j = 0; j < getVertexNum(); j++) {
				sb.append("\t").append(map[i][j]);
			}
			sb.append("\n");
		}

		return sb.toString();
	}
}
