package info.jagenberg.tim.xflr5_stl.model;

import java.util.ArrayList;
import java.util.List;

public class Wing {

	private String name;
	private List<Rib> ribs;
	private List<Airfoil> referencedAirfoils;

	public Wing() {
		ribs = new ArrayList<Rib>();
		referencedAirfoils = new ArrayList<Airfoil>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rib> getRibs() {
		return ribs;
	}

	public void setRibs(List<Rib> ribs) {
		this.ribs = ribs;
	}

	public List<Airfoil> getReferencedAirfoils() {
		return referencedAirfoils;
	}

	public void setReferencedAirfoils(List<Airfoil> referencedAirfoils) {
		this.referencedAirfoils = referencedAirfoils;
	}

	@Override
	public String toString() {
		return "Wing [name=" + name + ", ribs=" + ribs.size() + ", referencedAirfoils=" + referencedAirfoils.size() + "]";
	}

}
