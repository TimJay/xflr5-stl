package info.jagenberg.tim.xflr5_stl.model;

public class Rib {

	private double yPos;
	private double chord;
	private double offset;
	private double dihedral;
	private double twist;
	private Airfoil foil;

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double yPos) {
		this.yPos = yPos;
	}

	public double getChord() {
		return chord;
	}

	public void setChord(double chord) {
		this.chord = chord;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public double getDihedral() {
		return dihedral;
	}

	public void setDihedral(double dihedral) {
		this.dihedral = dihedral;
	}

	public double getTwist() {
		return twist;
	}

	public void setTwist(double twist) {
		this.twist = twist;
	}

	public Airfoil getAirfoil() {
		return foil;
	}

	public void setAirfoil(Airfoil foil) {
		this.foil = foil;
	}

	@Override
	public String toString() {
		return "Rib [yPos=" + yPos + ", chord=" + chord + ", offset=" + offset + ", dihedral=" + dihedral + ", twist=" + twist + ", foil=" + foil + "]";
	}

}
