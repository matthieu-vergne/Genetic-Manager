package travellingsalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Individual {
	private final List<Integer> genes;
	private final IndividualFactory factory;

	public Individual(IndividualFactory generator, Integer... genes) {
		this.factory = generator;
		this.genes = Arrays.asList(genes);
	}

	public Location[] getPath() {
		return getFactory().getPathFor(genes.toArray(new Integer[0]));
	}

	public double getLength() {
		ArrayList<Location> path = new ArrayList<Location>(
				Arrays.asList(getPath()));
		path.add(path.get(0));
		return Util.getPathLength(path.toArray(new Location[0]));
	}

	public Integer[] getGenes() {
		return genes.toArray(new Integer[0]);
	}

	@Override
	public String toString() {
		String pathString = "";
		for (Location location : getPath()) {
			pathString += location;
		}
		return Util.getPathLength(getPath()) + "=[" + pathString + "]";
	}

	public Individual reproduceWith(Individual individual) {
		return getFactory().createIndividualFrom(this, individual);
	}

	public IndividualFactory getFactory() {
		return factory;
	}
}
