package geneticmanager.mutation;

//TODO tests
//TODO javadoc
public interface IndividualMutation<Gene> extends Mutation<Gene> {
	public void mutates(Gene[] genes);
}
