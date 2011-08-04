package geneticmanager.individual;

abstract public class Individual<Gene> implements Comparable<Individual<Gene>> {
	private final Gene[] genes;

	public Individual(Gene... genes) {
		this.genes = genes;
	}

	public Gene[] getGenes() {
		return genes;
	}
}
