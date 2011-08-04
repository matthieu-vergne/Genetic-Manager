package sample.TSP;

import java.util.ArrayList;
import java.util.Arrays;

public class Individual extends geneticmanager.individual.Individual<Integer> {
	private final IndividualFactory factory;

	public Individual(IndividualFactory generator, Integer... genes) {
		super(genes);
		this.factory = generator;
	}

	public Location[] getLocations() {
		return factory.getPathFor(getGenes());
	}

	public IndividualFactory getFactory() {
		return factory;
	}

	public double getLength() {
		ArrayList<Location> path = new ArrayList<Location>(
				Arrays.asList(getLocations()));
		path.add(path.get(0));
		return Util.getPathLength(path.toArray(new Location[0]));
	}

	@Override
	public int compareTo(geneticmanager.individual.Individual<Integer> indiv) {
		return Double.valueOf(this.getLength()).compareTo(
				Double.valueOf(((Individual) indiv).getLength()));
	}

	@Override
	public String toString() {
		String pathString = "";
		for (Location location : getLocations()) {
			pathString += location;
		}
		return Util.getPathLength(getLocations()) + "=[" + pathString + "]";
	}

}
