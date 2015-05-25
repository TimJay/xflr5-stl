package info.jagenberg.tim.xflr5_stl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import joptsimple.OptionException;

import org.junit.Test;

public class XFLR52STLTest {

	@Test
	public void testMainWithCorrectRequiredOptions() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--input", "infile.xwimp", "--output", "outfile.stl" });
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

	@Test
	public void testMainWithCorrectOptionsWithMultiplier() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--multiplier", "1000.0", "--input", "infile.xwimp", "--output", "outfile.stl" });
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

	@Test
	public void testMainWithCorrectOptionsWithMultiplierAsInt() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--multiplier", "1000", "--input", "infile.xwimp", "--output", "outfile.stl" });
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

	@Test
	public void testMainWithMissingRequiredOutputOption() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--input", "infile.xwimp" });
			fail("Call with missing required option did not throw an exception!");
		} catch (OptionException e) {
			assertEquals("Missing required option(s) [output]", e.getMessage());
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

	@Test
	public void testMainWithMissingRequiredOutputArgument() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--input", "infile.xwimp", "--output" });
			fail("Call with missing required option did not throw an exception!");
		} catch (OptionException e) {
			assertEquals("Option output requires an argument", e.getMessage());
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

	@Test
	public void testMainWithMissingRequiredInputOption() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--output", "outfile.stl" });
			fail("Call with missing required option did not throw an exception!");
		} catch (OptionException e) {
			assertEquals("Missing required option(s) [input]", e.getMessage());
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

	@Test
	public void testMainWithMissingRequiredInputArgument() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--output", "outfile.stl", "--input" });
			fail("Call with missing required option did not throw an exception!");
		} catch (OptionException e) {
			assertEquals("Option input requires an argument", e.getMessage());
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

	@Test
	public void testMainWithNotRecognizedOption() {
		try {
			XFLR52STL.setupOptionsAndParse(new String[] { "--blubber" });
			fail("Call with not recognized option did not throw an exception!");
		} catch (OptionException e) {
			assertEquals("blubber is not a recognized option", e.getMessage());
		} catch (Exception e) {
			fail("Unexpected exception thrown: " + e);
		}
	}

}
