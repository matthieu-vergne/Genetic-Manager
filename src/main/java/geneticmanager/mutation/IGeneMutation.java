package geneticmanager.mutation;

//TODO tests
//TODO javadoc
public interface IGeneMutation<Gene> extends IMutation<Gene> {
	public void mutates(Gene[] genes, int index);
}
