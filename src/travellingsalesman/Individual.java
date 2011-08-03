package travellingsalesman;

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
		return factory.getPathFor(genes.toArray(new Integer[0]));
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
		return factory.createIndividualFrom(this, individual);
	}
}
