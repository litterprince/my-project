package algorithm.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class GraphMap {
	private final static int SCALE_INCREMENT = 5; // scale increment step
	private int scale = 0;// init scale
	private int[][] map;// endTown map
	private LinkedList<Vertex> vertexes;// endTown list
	private boolean[] isVisit;

	// init method
	public GraphMap() {
		vertexes = new LinkedList<Vertex>();
		map = new int[scale][scale];
		isVisit = new boolean[scale];
	}

	public GraphMap createMapFromFile(File file) {
		Edge edge = new Edge();
		try {
			String line = "";
			Scanner sca = new Scanner(file);
			while (sca.hasNext()) {
				line = sca.next();
				edge.createByString(line);
				add(edge);
			}
			sca.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	public GraphMap createMapFromStr(String str, String splitStr) {
		Edge edge = new Edge();
		String[] trainLineStr = str.split(splitStr);

		for (String string : trainLineStr) {
			edge.createByString(string);
			add(edge);
		}
		return this;
	}

    private void expansion() {
        int[][] newMap = new int[scale + SCALE_INCREMENT][scale + SCALE_INCREMENT];
        System.arraycopy(map, 0, newMap, 0, map.length);
        map = newMap;
        scale = map.length;
        boolean[] newVisit = new boolean[scale + SCALE_INCREMENT];
        System.arraycopy(isVisit, 0, newVisit, 0, isVisit.length);
        isVisit = newVisit;
    }

	public boolean add(Edge edge) {
		if (edge.isRightLine()) {
			// add train line
			int fromIndex = addVertex(edge.getV1());
			int toIndex = addVertex(edge.getV2());

			// if scale is enough
			if (scale < vertexes.size()) {
				expansion();
			}

			map[fromIndex][toIndex] = edge.getLength();
			return true;
		}

		return false;
	}

	private int addVertex(Vertex vertex) {
		// if exist
		if (vertexes.contains(vertex)) {
			return vertexes.indexOf(vertex);
		}
		// if not exist
		vertexes.add(vertex);
		return vertexes.indexOf(vertex);
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public int[][] getMap() {
		return map;
	}

	public void setVertexes(LinkedList<Vertex> vertexes) {
		this.vertexes = vertexes;
	}

	public LinkedList<Vertex> getVertexes() {
		return vertexes;
	}

	public int vertexNum() {
		return vertexes.size();
	}

    public int getIndex(Vertex vertex){
        return vertexes.indexOf(vertex);
    }

    public Vertex getVertex(int index){
        return vertexes.get(index);
    }

    public boolean getIsVisit(Vertex vertex) {
        if(vertexes.contains(vertex)){
            return isVisit[vertexes.indexOf(vertex)];
        }
        return true;
    }

    public void setIsVisit(Vertex vertex) {
        if(vertexes.contains(vertex)){
            isVisit[vertexes.indexOf(vertex)] = true;
        }
    }

    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (vertexNum() == 0) {
			return "No endTown !";
		}

		for (Vertex vertex : vertexes) {
			sb.append("\t").append(vertex.toString());
		}
		sb.append("\n");

		for (int i = 0; i < vertexNum(); i++) {
			sb.append(vertexes.get(i));
			for (int j = 0; j < vertexNum(); j++) {
				sb.append("\t").append(map[i][j]);
			}
			sb.append("\n");
		}

		return sb.toString();
	}
}
