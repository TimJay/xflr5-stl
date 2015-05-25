package info.jagenberg.tim.xflr5_stl.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.jagenberg.tim.xflr5_stl.model.Airfoil;
import info.jagenberg.tim.xflr5_stl.model.Coordinate;
import info.jagenberg.tim.xflr5_stl.model.Rib;
import info.jagenberg.tim.xflr5_stl.model.Wing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XFLR5ImporterTest {

	private static final double DELTA = 1e-6;
	private Path correctInputFile;

	@Before
	public void setUp() throws URISyntaxException {
		URL resource = XFLR5ImporterTest.class.getResource("/xflr5wing.xwimp");
		correctInputFile = Paths.get(resource.toURI());
	}

	@Test
	public void testImportCorrectFile() throws IOException {
		Wing wing = XFLR5Importer.importFile(correctInputFile);

		assertNotNull(wing);
		assertEquals("TestPlane_Wing", wing.getName());
		assertNotNull(wing.getRibs());
		assertEquals(11, wing.getRibs().size());

		Rib rib0 = wing.getRibs().get(0);
		assertNotNull(rib0);
		assertEquals(0.0, rib0.getYPos(), DELTA);
		assertEquals(0.2, rib0.getChord(), DELTA);
		assertEquals(0.0, rib0.getOffset(), DELTA);
		assertEquals(0.0, rib0.getDihedral(), DELTA);
		assertEquals(0.0, rib0.getTwist(), DELTA);
		Rib rib3 = wing.getRibs().get(3);
		assertNotNull(rib3);
		assertEquals(0.3, rib3.getYPos(), DELTA);
		assertEquals(0.192572, rib3.getChord(), DELTA);
		assertEquals(0.001857, rib3.getOffset(), DELTA);
		assertEquals(0.0, rib3.getDihedral(), DELTA);
		assertEquals(0.0, rib3.getTwist(), DELTA);

		Airfoil airfoil = rib0.getAirfoil();
		assertNotNull(airfoil);
		assertEquals("SG6043", airfoil.getName());
		List<Coordinate> coords = airfoil.getCoords();
		assertNotNull(coords);
		assertEquals(128, coords.size());
		Coordinate c0 = coords.get(0);
		assertNotNull(c0);
		assertEquals(1.0, c0.getX(), DELTA);
		assertEquals(0.0, c0.getZ(), DELTA);
		Coordinate c32 = coords.get(32);
		assertNotNull(c32);
		assertEquals(0.40497, c32.getX(), DELTA);
		assertEquals(0.10273, c32.getZ(), DELTA);
		Coordinate c80 = coords.get(80);
		assertNotNull(c80);
		assertEquals(0.06510, c80.getX(), DELTA);
		assertEquals(-0.01436, c80.getZ(), DELTA);

		assertEquals(1, wing.getReferencedAirfoils().size());
		assertEquals("SG6043.dat", wing.getReferencedAirfoils().get(0).getFileName());
	}

}
