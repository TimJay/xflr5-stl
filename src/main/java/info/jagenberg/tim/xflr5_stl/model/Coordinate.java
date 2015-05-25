package info.jagenberg.tim.xflr5_stl.model;

public class Coordinate {

	private double x;
	private double z;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", z=" + z + "]";
	}

}
