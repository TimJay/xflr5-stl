package info.jagenberg.tim.xflr5_stl.model;

import java.util.ArrayList;
import java.util.List;

public class Solid {

	private String name;
	private List<Facet> facets;

	public Solid() {
		facets = new ArrayList<Facet>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Facet> getFacets() {
		return facets;
	}

	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}

	@Override
	public String toString() {
		return "Solid [name=" + name + ", facets=" + facets.size() + "]";
	}

}
