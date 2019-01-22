package algorithm.graph;

/**
 * Town
 */
public class Vertex {
	private Character name;

	public Vertex(){}

	public Vertex(Character name) {
		this.name = name;
	}

	public Character getName() {
		return name;
	}

	public void setName(Character name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(!obj.getClass().equals(Vertex.class)){
			return false;
		}
		return name.toString().equals(obj.toString());
	}
}
