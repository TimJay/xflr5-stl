package info.jagenberg.tim.xflr5_stl.io;

import info.jagenberg.tim.xflr5_stl.model.Airfoil;
import info.jagenberg.tim.xflr5_stl.model.Coordinate;
import info.jagenberg.tim.xflr5_stl.model.Facet;
import info.jagenberg.tim.xflr5_stl.model.Rib;
import info.jagenberg.tim.xflr5_stl.model.Solid;
import info.jagenberg.tim.xflr5_stl.model.Vertex;
import info.jagenberg.tim.xflr5_stl.model.Wing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class STLExporter {

	public static void exportFile(Wing wing, Path outputFile, double multiplier) throws IOException {
		Solid newSolid = convertWingToSolid(wing, multiplier);
		StringBuilder newFileContent = new StringBuilder();
		appendSolidStart(newSolid, newFileContent);
		for (Facet facet : newSolid.getFacets()) {
			appendFacet(facet, newFileContent);
		}
		appendSolidEnd(newSolid, newFileContent);
		Files.write(outputFile, newFileContent.toString().getBytes(), StandardOpenOption.CREATE_NEW);
	}

	public static Solid convertWingToSolid(Wing wing, double multiplier) {
		checkAirfoils(wing);
		Solid newSolid = new Solid();
		newSolid.setName(wing.getName());
		for (int ribID = 0; ribID < (wing.getRibs().size() - 1); ribID++) {
			Rib innerRib = wing.getRibs().get(ribID);
			Airfoil innerFoil = innerRib.getAirfoil();
			double innerY = innerRib.getYPos();
			double innerChord = innerRib.getChord();
			double innerOffset = innerRib.getOffset();

			Rib outerRib = wing.getRibs().get(ribID + 1);
			Airfoil outerFoil = outerRib.getAirfoil();
			double outerY = outerRib.getYPos();
			double outerChord = outerRib.getChord();
			double outerOffset = outerRib.getOffset();
			for (int pt = 0; pt < (innerFoil.getCoords().size() - 1); pt++) {
				Vertex vA = createVertex(innerFoil, pt, innerY, innerChord, innerOffset, multiplier);
				Vertex vB = createVertex(innerFoil, pt + 1, innerY, innerChord, innerOffset, multiplier);
				Vertex vC = createVertex(outerFoil, pt + 1, outerY, outerChord, outerOffset, multiplier);
				Vertex vD = createVertex(outerFoil, pt, outerY, outerChord, outerOffset, multiplier);
				Facet f1 = new Facet();
				f1.setV1(vA);
				f1.setV2(vD);
				f1.setV3(vC);
				newSolid.getFacets().add(f1);
				Facet f2 = new Facet();
				f2.setV1(vA);
				f2.setV2(vC);
				f2.setV3(vB);
				newSolid.getFacets().add(f2);
			}
		}
		Rib lastRib = wing.getRibs().get(wing.getRibs().size() - 1);
		Airfoil lastAirfoil = lastRib.getAirfoil();
		List<Coordinate> coords = lastAirfoil.getCoords();
		int lastPt = coords.size() - 1;
		for (int pt = 0; pt < coords.size() / 2 - 1; pt++) {
			Vertex a = createVertex(lastAirfoil, pt, lastRib.getYPos(), lastRib.getChord(), lastRib.getOffset(), multiplier);
			Vertex b = createVertex(lastAirfoil, pt + 1, lastRib.getYPos(), lastRib.getChord(), lastRib.getOffset(), multiplier);
			Vertex c = createVertex(lastAirfoil, lastPt - pt - 1, lastRib.getYPos(), lastRib.getChord(), lastRib.getOffset(), multiplier);
			Vertex d = createVertex(lastAirfoil, lastPt - pt, lastRib.getYPos(), lastRib.getChord(), lastRib.getOffset(), multiplier);
			Facet f1 = new Facet();
			f1.setN(calculateNormal(a, d, b));
			f1.setV1(a);
			f1.setV2(b);
			f1.setV3(d);
			newSolid.getFacets().add(f1);
			Facet f2 = new Facet();
			f2.setN(calculateNormal(b, d, c));
			f2.setV1(b);
			f2.setV2(c);
			f2.setV3(d);
			newSolid.getFacets().add(f2);
		}
		if (coords.size() % 2 == 1) {
			int center = coords.size() /  2;
			Vertex a = createVertex(lastAirfoil, center - 1, lastRib.getYPos(), lastRib.getChord(), lastRib.getOffset(), multiplier);
			Vertex b = createVertex(lastAirfoil, center, lastRib.getYPos(), lastRib.getChord(), lastRib.getOffset(), multiplier);
			Vertex c = createVertex(lastAirfoil, center + 1, lastRib.getYPos(), lastRib.getChord(), lastRib.getOffset(), multiplier);
			Facet f = new Facet();
			f.setN(calculateNormal(a, c, b));
			f.setV1(a);
			f.setV2(b);
			f.setV3(c);
			newSolid.getFacets().add(f);
		}
		
		ArrayList<Facet> mirrorFacets = new ArrayList<Facet>();
		for (Facet facet : newSolid.getFacets()) {
			Facet mirrorFacet = new Facet();
			Vertex v1 = facet.getV1();
			Vertex v2 = facet.getV2();
			Vertex v3 = facet.getV3();
			Vertex n1 = new Vertex();
			Vertex n2 = new Vertex();
			Vertex n3 = new Vertex();
			n1.setX(v1.getX());
			n1.setY(v1.getY() * -1.0);
			n1.setZ(v1.getZ());
			n2.setX(v2.getX());
			n2.setY(v2.getY() * -1.0);
			n2.setZ(v2.getZ());
			n3.setX(v3.getX());
			n3.setY(v3.getY() * -1.0);
			n3.setZ(v3.getZ());
			mirrorFacet.setV1(n1);
			mirrorFacet.setV2(n3);
			mirrorFacet.setV3(n2);
			mirrorFacets.add(mirrorFacet);
		}
		newSolid.getFacets().addAll(mirrorFacets);
		for (Facet facet : newSolid.getFacets()) {
			facet.setN(calculateNormal(facet.getV1(), facet.getV2(), facet.getV3()));
		}
		return newSolid;
	}

	private static void checkAirfoils(Wing wing) {
		if (wing.getReferencedAirfoils().size() > 1) {
			int points = wing.getReferencedAirfoils().get(0).getCoords().size();
			for (Airfoil airfoil : wing.getReferencedAirfoils()) {
				if (airfoil.getCoords().size() != points) {
					throw new RuntimeException("Number of coordinates per airfoil does not match for all airfoils.");
				}
			}
		}
	}

	private static Vertex createVertex(Airfoil foil, int pt, double y, double chord, double offset, double multiplier) {
		Vertex newVertex = new Vertex();
		newVertex.setX((foil.getCoords().get(pt).getX() * chord) + offset);
		newVertex.setY(y);
		newVertex.setZ(foil.getCoords().get(pt).getZ() * chord);
		newVertex.setX(newVertex.getX() * multiplier);
		newVertex.setY(newVertex.getY() * multiplier);
		newVertex.setZ(newVertex.getZ() * multiplier);
		return newVertex;
	}

	private static Vertex calculateNormal(Vertex a, Vertex b, Vertex c) {
		double vX = (b.getX() - a.getX());
		double vY = (b.getY() - a.getY());
		double vZ = (b.getZ() - a.getZ());
		double wX = (c.getX() - a.getX());
		double wY = (c.getY() - a.getY());
		double wZ = (c.getZ() - a.getZ());
		Vertex n = new Vertex();
		n.setX((vY * wZ) - (vZ * wY));
		n.setY((vZ * wX) - (vX * wZ));
		n.setZ((vX * wY) - (vY * wX));
		double mag = Math.sqrt(Math.pow(n.getX(), 2) + Math.pow(n.getY(), 2) + Math.pow(n.getZ(), 2));
		n.setX(n.getX() / mag);
		n.setY(n.getY() / mag);
		n.setZ(n.getZ() / mag);
		return n;
	}

	private static void appendSolidStart(Solid solid, StringBuilder sb) {
		sb.append("solid " + solid.getName() + "\n");
	}

	private static void appendFacet(Facet facet, StringBuilder sb) {
		double ni = facet.getN().getX();
		double nj = facet.getN().getY();
		double nk = facet.getN().getZ();
		double v1x = facet.getV1().getX();
		double v1y = facet.getV1().getY();
		double v1z = facet.getV1().getZ();
		double v2x = facet.getV2().getX();
		double v2y = facet.getV2().getY();
		double v2z = facet.getV2().getZ();
		double v3x = facet.getV3().getX();
		double v3y = facet.getV3().getY();
		double v3z = facet.getV3().getZ();
		if ((ni != 0.0) && (nj != 0.0) && (nk != 0.0)) {
			sb.append("facet normal " + String.format("%12.6E", ni) + " " + String.format("%12.6E", nj) + " " + String.format("%12.6E", nk) + "\n");
		} else {
			sb.append("facet\n");
		}
		{
			sb.append(" outer loop\n");
			{
				sb.append("  vertex " + String.format("%12.6E", v1x) + " " + String.format("%12.6E", v1y) + " " + String.format("%12.6E", v1z) + "\n");
				sb.append("  vertex " + String.format("%12.6E", v2x) + " " + String.format("%12.6E", v2y) + " " + String.format("%12.6E", v2z) + "\n");
				sb.append("  vertex " + String.format("%12.6E", v3x) + " " + String.format("%12.6E", v3y) + " " + String.format("%12.6E", v3z) + "\n");
			}
			sb.append(" endloop\n");
		}
		sb.append("endfacet\n");
	}

	private static void appendSolidEnd(Solid solid, StringBuilder sb) {
		sb.append("endsolid " + solid.getName() + "\n");
	}

}
