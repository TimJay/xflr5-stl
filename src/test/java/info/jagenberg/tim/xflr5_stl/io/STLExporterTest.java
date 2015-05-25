package info.jagenberg.tim.xflr5_stl.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.jagenberg.tim.xflr5_stl.model.Airfoil;
import info.jagenberg.tim.xflr5_stl.model.Coordinate;
import info.jagenberg.tim.xflr5_stl.model.Facet;
import info.jagenberg.tim.xflr5_stl.model.Rib;
import info.jagenberg.tim.xflr5_stl.model.Solid;
import info.jagenberg.tim.xflr5_stl.model.Vertex;
import info.jagenberg.tim.xflr5_stl.model.Wing;

import org.junit.Before;
import org.junit.Test;

public class STLExporterTest {

	private static final double DELTA = 1e-6;
	private Wing wing;

	@Before
	public void setUp() throws Exception {
		wing = new Wing();
		wing.setName("testwing");
		Coordinate a = coordinate(0.0, 0.0);
		Coordinate b = coordinate(1.0, 0.0);
		Rib rib1 = rib(0.0, a, b);
		wing.getRibs().add(rib1);
		Coordinate c = coordinate(1.0, 1.0);
		Coordinate d = coordinate(0.0, 0.0);
		Rib rib2 = rib(1.0, d, c);
		wing.getRibs().add(rib2);
	}

	private Coordinate coordinate(double x, double z) {
		Coordinate coordinate = new Coordinate();
		coordinate.setX(x);
		coordinate.setZ(z);
		return coordinate;
	}

	private Rib rib(double y, Coordinate c1, Coordinate c2) {
		Rib rib = new Rib();
		rib.setYPos(y);
		rib.setChord(1.0);
		rib.setOffset(0.0);
		rib.setDihedral(0.0);
		rib.setTwist(0.0);
		Airfoil airfoil = new Airfoil();
		airfoil.getCoords().add(c1);
		airfoil.getCoords().add(c2);
		rib.setAirfoil(airfoil);
		return rib;
	}

	@Test
	public void testConvertWingToSolid() {
		Solid solid = STLExporter.convertWingToSolid(wing, 1.0);
		assertNotNull(solid);
		assertEquals("testwing", solid.getName());
		assertEquals(6, solid.getFacets().size());
		Facet f1 = solid.getFacets().get(0);
		Facet f2 = solid.getFacets().get(1);
		Facet f3 = solid.getFacets().get(3);
		Facet f4 = solid.getFacets().get(4);
		assertEquals(vertex(0.0, 0.0, 0.0), f1.getV1());
		assertEquals(vertex(0.0, 1.0, 0.0), f1.getV2());
		assertEquals(vertex(1.0, 1.0, 1.0), f1.getV3());

		assertEquals(vertex(0.0, -0.0, 0.0), f3.getV1());
		assertEquals(vertex(1.0, -1.0, 1.0), f3.getV2());
		assertEquals(vertex(0.0, -1.0, 0.0), f3.getV3());

		assertEquals(vertex(0.0, 0.0, 0.0), f2.getV1());
		assertEquals(vertex(1.0, 1.0, 1.0), f2.getV2());
		assertEquals(vertex(1.0, 0.0, 0.0), f2.getV3());

		assertEquals(vertex(0.0, -0.0, 0.0), f4.getV1());
		assertEquals(vertex(1.0, -0.0, 0.0), f4.getV2());
		assertEquals(vertex(1.0, -1.0, 1.0), f4.getV3());

		assertEquals(1.0 / Math.sqrt(2.0), f1.getN().getX(), DELTA);
		assertEquals(0.0, f1.getN().getY(), DELTA);
		assertEquals(-1.0 / Math.sqrt(2.0), f1.getN().getZ(), DELTA);

		assertEquals(0.0, f2.getN().getX(), DELTA);
		assertEquals(1.0 / Math.sqrt(2.0), f2.getN().getY(), DELTA);
		assertEquals(-1.0 / Math.sqrt(2.0), f2.getN().getZ(), DELTA);

		assertEquals(1.0 / Math.sqrt(2.0), f3.getN().getX(), DELTA);
		assertEquals(0.0, f3.getN().getY(), DELTA);
		assertEquals(-1.0 / Math.sqrt(2.0), f3.getN().getZ(), DELTA);

		assertEquals(0.0, f4.getN().getX(), DELTA);
		assertEquals(-1.0 / Math.sqrt(2.0), f4.getN().getY(), DELTA);
		assertEquals(-1.0 / Math.sqrt(2.0), f4.getN().getZ(), DELTA);
	}

	private Vertex vertex(double x, double y, double z) {
		Vertex vertex = new Vertex();
		vertex.setX(x);
		vertex.setY(y);
		vertex.setZ(z);
		return vertex;
	}

}
