package info.jagenberg.tim.xflr5_stl.model;

public class Facet {

	private Vertex n;
	private Vertex v1;
	private Vertex v2;
	private Vertex v3;

	public Facet() {
		n = new Vertex();
		n.setX(0.0);
		n.setY(0.0);
		n.setZ(0.0);
	}

	public Vertex getN() {
		return n;
	}

	public void setN(Vertex n) {
		this.n = n;
	}

	public Vertex getV1() {
		return v1;
	}

	public void setV1(Vertex v1) {
		this.v1 = v1;
	}

	public Vertex getV2() {
		return v2;
	}

	public void setV2(Vertex v2) {
		this.v2 = v2;
	}

	public Vertex getV3() {
		return v3;
	}

	public void setV3(Vertex v3) {
		this.v3 = v3;
	}

	@Override
	public String toString() {
		return "Facet [n=" + n + ", v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + "]";
	}

}
