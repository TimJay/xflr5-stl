package info.jagenberg.tim.xflr5_stl.io;

import info.jagenberg.tim.xflr5_stl.model.Airfoil;
import info.jagenberg.tim.xflr5_stl.model.Coordinate;
import info.jagenberg.tim.xflr5_stl.model.Rib;
import info.jagenberg.tim.xflr5_stl.model.Wing;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class XFLR5Importer {

	public static Wing importFile(Path inputFile) throws IOException {
		Wing newWing = new Wing();
		try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
			String line = reader.readLine();
			if (line != null) {
				parseWingName(line, newWing);
			} else {
				throw new RuntimeException("Could not parse wing input file, the file is empty.");
			}
			while ((line = reader.readLine()) != null) {
				parseRib(line, newWing, inputFile);
			}
		}
		return newWing;
	}

	private static void parseWingName(String line, Wing wing) {
		wing.setName(line.trim());
	}

	private static void parseRib(String line, Wing wing, Path inputFile) throws IOException {
		String[] segments = line.trim().split(" ");
		if (segments.length != 11) {
			throw new RuntimeException("Could not parse wing input file, line does not specify a valid rib:\n" + line);
		}
		Rib newRib = new Rib();
		newRib.setYPos(Double.parseDouble(segments[0]));
		newRib.setChord(Double.parseDouble(segments[1]));
		newRib.setOffset(Double.parseDouble(segments[2]));
		newRib.setDihedral(Double.parseDouble(segments[3]));
		newRib.setTwist(Double.parseDouble(segments[4]));
		newRib.setAirfoil(findAndParseAirfoil(segments[9], wing, inputFile));
		wing.getRibs().add(newRib);
	}

	private static Airfoil findAndParseAirfoil(String airfoilFileName, Wing wing, Path inputFile) throws IOException {
		List<Airfoil> refdFoils = wing.getReferencedAirfoils();
		Optional<Airfoil> foundAirfoil = refdFoils.stream().filter(a -> a.getFileName().equals(airfoilFileName + ".dat")).findFirst();
		if (foundAirfoil.isPresent()) {
			return foundAirfoil.get();
		}
		Airfoil newAirfoil = new Airfoil();
		newAirfoil.setFileName(airfoilFileName + ".dat");
		Path parent = inputFile.toRealPath().getParent();
		try (BufferedReader reader = Files.newBufferedReader(parent.resolve(airfoilFileName + ".dat"))) {
			String line = reader.readLine();
			if (line != null) {
				parseAirfoilName(line, newAirfoil);
			} else {
				throw new RuntimeException("Could not parse airfoil input file, the file is empty.");
			}
			while ((line = reader.readLine()) != null) {
				parseCoords(line, newAirfoil);
			}
		}
		wing.getReferencedAirfoils().add(newAirfoil);
		return newAirfoil;
	}

	private static void parseAirfoilName(String line, Airfoil newAirfoil) {
		newAirfoil.setName(line.trim());
	}

	private static void parseCoords(String line, Airfoil newAirfoil) {
		String[] segments = line.trim().split("[\\s]+");
		if (segments.length != 2) {
			throw new RuntimeException("Could not parse airfoil input file, line does not contain two coordinates:\n" + line);
		}
		Coordinate newCoordinate = new Coordinate();
		newCoordinate.setX(Double.parseDouble(segments[0]));
		newCoordinate.setZ(Double.parseDouble(segments[1]));
		newAirfoil.getCoords().add(newCoordinate);
	}

}
