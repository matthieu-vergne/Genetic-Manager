package geneticmanager.individual;

//TODO tests
//TODO javadoc
public interface IndividualReproducer<Indiv extends Individual<?>> {
	abstract public Indiv reproduce(Indiv i1, Indiv i2);
}
