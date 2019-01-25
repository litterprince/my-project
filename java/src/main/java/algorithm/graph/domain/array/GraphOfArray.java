package algorithm.graph.domain.array;

import algorithm.graph.domain.BaseGraph;
import algorithm.graph.domain.IVertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphOfArray extends BaseGraph {
	private final static int SCALE_INCREMENT = 10; // scale increment
	private int scale = 5;// initial value of scale
	private int[][] map;// adjacency matrix
	private VertexOfArray[] vertexList;// vertex list
	private int vertexNum;// the number of vertex

    // init method
    public GraphOfArray() {
        vertexList = new VertexOfArray[scale];
        map = new int[scale][scale];
    }

    public GraphOfArray buildGraph(File file) {
        EdgeOfArray edgeOfArray = new EdgeOfArray();
        try {
            String line = "";
            Scanner sca = new Scanner(file);
            while (sca.hasNext()) {
                line = sca.next();
                edgeOfArray.createByString(line);
                add(edgeOfArray);
            }
            sca.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    public GraphOfArray buildGraph(String content){
	    return buildGraph(content, ",");
    }

    public GraphOfArray buildGraph(String str, String splitStr) {
        EdgeOfArray edgeOfArray = new EdgeOfArray();
        String[] trainLineStr = str.split(splitStr);

        for (String string : trainLineStr) {
            edgeOfArray.createByString(string);
            add(edgeOfArray);
        }
        return this;
    }

    private void expansion() {
		scale += SCALE_INCREMENT;

		// expand map array
        int[][] newMap = new int[scale][scale];
        System.arraycopy(map, 0, newMap, 0, map.length);
        map = newMap;

        // expand vertex array
        VertexOfArray[] newVertexOfArrays = new VertexOfArray[scale];
        System.arraycopy(vertexList, 0, newVertexOfArrays, 0, vertexList.length);
        vertexList = newVertexOfArrays;
    }

    private boolean add(EdgeOfArray edgeOfArray) {
		if (edgeOfArray.isRightLine()) {
            // if scale is enough
            if (scale < getVertexNum() + 2) {
                expansion();
            }

			// add train line
			int fromIndex = addVertex(edgeOfArray.getV1());
			int toIndex = addVertex(edgeOfArray.getV2());
			map[fromIndex][toIndex] = edgeOfArray.getLength();
			return true;
		}

		return false;
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

	private int addAndGetNum(){
	    return ++vertexNum;
    }

    private int getAndAddNum(){
        return vertexNum++;
    }

    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (getVertexNum() == 0) {
			return "No endTown !";
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
