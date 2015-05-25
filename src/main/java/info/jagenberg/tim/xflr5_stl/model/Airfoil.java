package info.jagenberg.tim.xflr5_stl.model;

import java.util.ArrayList;
import java.util.List;

public class Airfoil {

	private String fileName;
	private String name;
	private List<Coordinate> coords;

	public Airfoil() {
		coords = new ArrayList<Coordinate>();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Coordinate> getCoords() {
		return coords;
	}

	public void setCoords(List<Coordinate> coords) {
		this.coords = coords;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Airfoil [name=" + name + ", fileName=" + fileName + "]\n");
		sb.append("coords:\n");
		for (Coordinate coordinate : coords) {
			sb.append(coordinate.getX() + " " + coordinate.getZ() + "\n");
		}
		return sb.toString();
	}

}
