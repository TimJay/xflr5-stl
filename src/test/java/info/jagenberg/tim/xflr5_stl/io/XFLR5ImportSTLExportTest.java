package info.jagenberg.tim.xflr5_stl.io;

import static org.junit.Assert.assertEquals;
import info.jagenberg.tim.xflr5_stl.model.Wing;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class XFLR5ImportSTLExportTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private Wing importedWing;

	@Before
	public void setUp() throws URISyntaxException, IOException {
		URL resource = XFLR5ImportSTLExportTest.class.getResource("/xflr5wing.xwimp");
		Path correctInputFile = Paths.get(resource.toURI());
		importedWing = XFLR5Importer.importFile(correctInputFile);
	}

	@Test
	public void testExportWing() throws IOException {
		Path tempFile = tempFolder.getRoot().toPath().resolve("test.stl");
		STLExporter.exportFile(importedWing, tempFile, 1.0);
		try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
			assertEquals("solid " + importedWing.getName(), reader.readLine());
			assertEquals("facet normal 3.485647E-01 2.122759E-03 9.372823E-01", reader.readLine());
			assertEquals(" outer loop", reader.readLine());
			assertEquals("  vertex 2.000000E-01 0.000000E+00 0.000000E+00", reader.readLine());
			assertEquals("  vertex 1.993910E-01 1.000000E-01 0.000000E+00", reader.readLine());
			assertEquals("  vertex 1.980305E-01 1.000000E-01 5.059375E-04", reader.readLine());
			assertEquals(" endloop", reader.readLine());
			assertEquals("endfacet", reader.readLine());
			for (int i = 0; i < (7 * 128); i++) {
				reader.readLine();
			}
			assertEquals("facet normal -9.858336E-01 2.002677E-03 1.677144E-01", reader.readLine());
			assertEquals(" outer loop", reader.readLine());
			assertEquals("  vertex 1.040000E-04 0.000000E+00 8.220000E-04", reader.readLine());
			assertEquals("  vertex 2.249107E-04 1.000000E-01 3.386196E-04", reader.readLine());
			assertEquals("  vertex 2.200000E-05 0.000000E+00 3.400000E-04", reader.readLine());
			assertEquals(" endloop", reader.readLine());
			assertEquals("endfacet", reader.readLine());
		}
	}

	@Test
	public void testExportWingMM() throws IOException {
		Path tempFile = tempFolder.getRoot().toPath().resolve("test.stl");
		STLExporter.exportFile(importedWing, tempFile, 1000.0);
		try (BufferedReader reader = Files.newBufferedReader(tempFile)) {
			assertEquals("solid " + importedWing.getName(), reader.readLine());
			assertEquals("facet normal 3.485647E-01 2.122759E-03 9.372823E-01", reader.readLine());
			assertEquals(" outer loop", reader.readLine());
			assertEquals("  vertex 2.000000E+02 0.000000E+00 0.000000E+00", reader.readLine());
			assertEquals("  vertex 1.993910E+02 1.000000E+02 0.000000E+00", reader.readLine());
			assertEquals("  vertex 1.980305E+02 1.000000E+02 5.059375E-01", reader.readLine());
			assertEquals(" endloop", reader.readLine());
			assertEquals("endfacet", reader.readLine());
			for (int i = 0; i < (7 * 128); i++) {
				reader.readLine();
			}
			assertEquals("facet normal -9.858336E-01 2.002677E-03 1.677144E-01", reader.readLine());
			assertEquals(" outer loop", reader.readLine());
			assertEquals("  vertex 1.040000E-01 0.000000E+00 8.220000E-01", reader.readLine());
			assertEquals("  vertex 2.249107E-01 1.000000E+02 3.386196E-01", reader.readLine());
			assertEquals("  vertex 2.200000E-02 0.000000E+00 3.400000E-01", reader.readLine());
			assertEquals(" endloop", reader.readLine());
			assertEquals("endfacet", reader.readLine());
		}
	}

}
