package travellingsalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndividualGenerator {
	private List<Location> locations = new ArrayList<Location>();
	private double mutationRate = 0.01;

	public void addLocation(Location location) {
		locations.add(location);
	}

	public Location[] getLocations() {
		return locations.toArray(new Location[0]);
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	private Integer[] createRandomGenes() {
		List<Integer> genes = new ArrayList<Integer>();
		for (int index = 0; index < locations.size(); index++) {
			int gene;
			do {
				gene = createRandomGeneFor(index);
			} while (genes.contains(gene));
			genes.add(gene);
		}
		return genes.toArray(new Integer[0]);
	}

	private int createRandomGeneFor(int index) {
		return Util.randomIndex(locations.size());
	}

	public Location[] getPathFor(Integer[] genes) {
		ArrayList<Location> temp = new ArrayList<Location>(locations);
		List<Location> path = new ArrayList<Location>();
		for (int gene : genes) {
			path.add(temp.get(gene));
		}
		return path.toArray(new Location[0]);
	}

	public Individual createRandomIndividual() {
		return new Individual(this, createRandomGenes());
	}

	public Individual createIndividualFrom(Individual i1, Individual i2) {
		// crossing
		List<Integer> genes = new ArrayList<Integer>(Arrays.asList(i1
				.getGenes()));
		Integer[] genes2 = i2.getGenes();
		int limit = Util.randomIndex(genes.size());
		while (genes.size() > limit) {
			genes.remove(limit);
		}
		for (int index = 0; index < locations.size(); index++) {
			Integer gene = genes2[index];
			if (!genes.contains(gene)) {
				genes.add(gene);
			}
		}

		// mutation
		for(int index = 0 ; index < genes.size() ; index ++){
			if (Math.random() < getMutationRate()) {
				int newIndex = Util.randomIndex(genes.size());
				genes.add(newIndex, genes.remove(index));
			}
		}

		return new Individual(this, genes.toArray(new Integer[0]));
	}

}
