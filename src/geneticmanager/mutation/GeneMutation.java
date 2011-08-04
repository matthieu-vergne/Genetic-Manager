package geneticmanager.mutation;

public interface GeneMutation extends Mutation {
	public void mutates(Integer[] genes, int index);
}
