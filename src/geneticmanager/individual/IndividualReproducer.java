package geneticmanager.individual;

public interface IndividualReproducer<Indiv extends Individual<?>> {
	abstract public Indiv reproduce(Indiv i1, Indiv i2);
}
