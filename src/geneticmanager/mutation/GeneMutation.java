package geneticmanager.mutation;

public interface GeneMutation<Gene> extends Mutation<Gene> {
	public void mutates(Gene[] genes, int index);
}
