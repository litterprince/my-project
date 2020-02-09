package algorithm.graph.domain.array;

/**
 * A->B的一条线路
 */
public class EdgeOfArray {
	private VertexOfArray from;
	private VertexOfArray to;
	private Integer length;

    public EdgeOfArray() { }

    public EdgeOfArray(VertexOfArray from, VertexOfArray to, Integer length) {
		this.from = from;
		this.to = to;
		this.length = length;
	}

	public VertexOfArray getFrom() {
		return from;
	}

	public void setFrom(VertexOfArray v1) {
		this.from = v1;
	}

	public VertexOfArray geTo() {
		return to;
	}

	public void setTo(VertexOfArray v2) {
		this.to = v2;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * 判断当前线路是否正确
	 * @return boolean
	 */
	public boolean isRightLine() {
        return !(from == null || to == null || length == null || from.equals(to));
	}

	@Override
	public String toString() {
		return "EdgeOfArray [from=" + from + ", to=" + to + ", length=" + length
				+ "]";
	}
}
