package geneticmanager.individual;

//TODO tests
//TODO javadoc
public interface IIndividualReproducer<Indiv extends IIndividual<?>> {
	abstract public Indiv reproduce(Indiv i1, Indiv i2);
}
