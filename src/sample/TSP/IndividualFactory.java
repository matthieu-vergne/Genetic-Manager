package sample.TSP;

import geneticmanager.mutation.IGeneMutation;
import geneticmanager.mutation.IIndividualMutation;
import geneticmanager.mutation.IMutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndividualFactory {
	private List<Location> translation = new ArrayList<Location>();
	private IMutation<Integer> mutation = null;
	private long factoredCounter = 0;

	public long getFactoredCounter() {
		return factoredCounter;
	}

	public void addLocation(Location location) {
		translation.add(location);
	}

	public Location[] getLocations() {
		return translation.toArray(new Location[0]);
	}

	private Integer[] createRandomGenes() {
		List<Integer> genes = new ArrayList<Integer>();
		for (int index = 0; index < translation.size(); index++) {
			Integer gene;
			do {
				gene = createRandomGeneFor(index);
			} while (genes.contains(gene));
			genes.add(gene);
		}
		return genes.toArray(new Integer[0]);
	}

	private Integer createRandomGeneFor(int index) {
		return Util.randomIndex(translation.size());
	}

	public Location[] getPathFor(Integer[] genes) {
		List<Location> path = new ArrayList<Location>();
		for (Integer gene : genes) {
			path.add(translation.get(gene));
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
		for (int index = 0; index < translation.size(); index++) {
			Integer gene = genes2[index];
			if (!geneContainer.contains(gene)) {
				geneContainer.add(gene);
			}
		}
		Integer[] genes = geneContainer.toArray(new Integer[0]);

		// mutation
		if (mutation instanceof IIndividualMutation) {
			if (Math.random() < mutation.getRate()) {
				((IIndividualMutation<Integer>) mutation).mutates(genes);
			}
		} else if (mutation instanceof IGeneMutation) {
			for (int index = 0; index < geneContainer.size(); index++) {
				if (Math.random() < mutation.getRate()) {
					((IGeneMutation<Integer>) mutation).mutates(genes, index);
				}
			}
		}

		return createIndividual(genes);
	}

	public IMutation<Integer> getMutation() {
		return mutation;
	}

	public void setMutation(IMutation<Integer> mutation) {
		this.mutation = mutation;
	}

}
