package geneticmanager.mutation;

public interface IndividualMutation<Gene> extends Mutation<Gene> {
	public void mutates(Gene[] genes);
}
