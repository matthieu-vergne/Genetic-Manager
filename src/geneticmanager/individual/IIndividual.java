package geneticmanager.individual;

//TODO tests
//TODO javadoc
public interface IIndividual<Gene> extends Comparable<IIndividual<Gene>> {
	public Gene[] getGenes();
}
