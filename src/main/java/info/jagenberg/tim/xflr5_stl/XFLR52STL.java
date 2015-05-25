package info.jagenberg.tim.xflr5_stl;

import static java.util.Arrays.asList;
import info.jagenberg.tim.xflr5_stl.io.STLExporter;
import info.jagenberg.tim.xflr5_stl.io.XFLR5Importer;
import info.jagenberg.tim.xflr5_stl.model.Wing;

import java.io.File;
import java.io.IOException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class XFLR52STL {

	private static OptionSet options;
	private static OptionSpec<Double> multiplier;
	private static OptionSpec<File> input;
	private static OptionSpec<File> output;

	public static void main(String[] args) throws IOException {
		setupOptionsAndParse(args);
		Wing wing = XFLR5Importer.importFile(input.value(options).toPath());
		STLExporter.exportFile(wing, output.value(options).toPath(), getMultiplier());
	}

	static void setupOptionsAndParse(String[] args) throws IOException {
		OptionParser opt = new OptionParser();
		OptionSpec<Void> help = opt.acceptsAll(asList("h", "?", "help")).forHelp();
		multiplier = opt.accepts("multiplier", "Scale the resulting STL with this multiplier.").withRequiredArg().ofType(Double.class);
		input = opt.accepts("input", "Wing file exported from XFLR5.").withRequiredArg().ofType(File.class).required();
		output = opt.accepts("output", "STL file generated from wing file.").withRequiredArg().ofType(File.class).required();
		if ((args == null) || (args.length == 0)) {
			opt.printHelpOn(System.out);
			System.exit(-1);
		}
		options = opt.parse(args);
		if (options.has(help)) {
			opt.printHelpOn(System.out);
		}
	}

	private static double getMultiplier() {
		double mult = 1.0;
		if (options.has(multiplier)) {
			mult = multiplier.value(options);
		}
		return mult;
	}

}
