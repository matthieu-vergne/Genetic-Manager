package geneticmanager.mutation;

//TODO tests
//TODO javadoc
public interface IIndividualMutation<Gene> extends IMutation<Gene> {
	public void mutates(Gene[] genes);
}
