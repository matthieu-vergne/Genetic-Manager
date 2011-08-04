package geneticmanager.mutation;

//TODO tests
//TODO javadoc
public interface GeneMutation<Gene> extends Mutation<Gene> {
	public void mutates(Gene[] genes, int index);
}
