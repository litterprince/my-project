package algorithm.graph.domain.array;

import algorithm.graph.domain.IVertex;

/**
 * Town
 */
public class VertexOfArray implements IVertex {
	private Character value;

	public VertexOfArray(Character value) {
	    if(value == null){
	        throw new NullPointerException();
        }
		this.value = value;
	}

	@Override
	public Character getValue() {
		return value;
	}

	public void setValue(Character value) {
		this.value = value;
	}

    @Override
    public String toString(){
        return value.toString();
    }

	@Override
	public boolean equals(Object obj) {
		if(!obj.getClass().equals(VertexOfArray.class)){
			return false;
		}
		return value.toString().equals(obj.toString());
	}
}
