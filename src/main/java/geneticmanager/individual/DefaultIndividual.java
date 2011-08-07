package geneticmanager.individual;

//TODO tests
//TODO javadoc
abstract public class DefaultIndividual<Gene> implements IIndividual<Gene> {
	private final Gene[] genes;

	public DefaultIndividual(Gene... genes) {
		this.genes = genes;
	}

	public Gene[] getGenes() {
		return genes;
	}
}
