package algorithm.graph.domain.array;

import algorithm.graph.domain.AbstractVertex;
import algorithm.graph.domain.IVertex;

/**
 * Town
 */
public class VertexOfArray extends AbstractVertex {
	private Character value;
	private int inDegree;
	private int outDegree;

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

	public void InDegreeIncrement(){
	     inDegree ++;
    }

    @Override
    public int getInDegree() {
        return inDegree;
    }

    public void OutDegreeIncrement(){
        outDegree ++;
    }

    @Override
    public int getOutDegree() {
        return outDegree;
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
