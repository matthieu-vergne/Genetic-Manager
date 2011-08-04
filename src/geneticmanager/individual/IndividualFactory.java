package geneticmanager.individual;

import geneticmanager.Location;
import geneticmanager.Util;
import geneticmanager.mutation.GeneMutation;
import geneticmanager.mutation.IndividualMutation;
import geneticmanager.mutation.Mutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IndividualFactory {
	private List<Location> locations = new ArrayList<Location>();
	private Mutation mutation = null;
	private long factoredCounter = 0;

	public long getFactoredCounter() {
		return factoredCounter;
	}

	public void addLocation(Location location) {
		locations.add(location);
	}

	public Location[] getLocations() {
		return locations.toArray(new Location[0]);
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
		Integer[] genes = createRandomGenes();
		return createIndividual(genes);
	}

	public Individual createIndividual(Integer[] genes) {
		Individual individual = new Individual(this, genes);
		factoredCounter++;
		return individual;
	}

	public Individual createIndividualFrom(Individual i1, Individual i2) {
		// crossing
		List<Integer> geneContainer = new ArrayList<Integer>(Arrays.asList(i1
				.getGenes()));
		Integer[] genes2 = i2.getGenes();
		int limit = Util.randomIndex(geneContainer.size());
		while (geneContainer.size() > limit) {
			geneContainer.remove(limit);
		}
		for (int index = 0; index < locations.size(); index++) {
			Integer gene = genes2[index];
			if (!geneContainer.contains(gene)) {
				geneContainer.add(gene);
			}
		}
		Integer[] genes = geneContainer.toArray(new Integer[0]);

		// mutation
		if (mutation instanceof IndividualMutation) {
			if (Math.random() < mutation.getRate()) {
				((IndividualMutation) mutation).mutates(genes);
			}
		} else if (mutation instanceof GeneMutation) {
			for (int index = 0; index < geneContainer.size(); index++) {
				if (Math.random() < mutation.getRate()) {
					((GeneMutation) mutation).mutates(genes, index);
				}
			}
		}

		return createIndividual(genes);
	}

	public Mutation getMutation() {
		return mutation;
	}

	public void setMutation(Mutation mutation) {
		this.mutation = mutation;
	}

}
