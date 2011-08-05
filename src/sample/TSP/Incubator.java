package sample.TSP;

import geneticmanager.individual.IIndividualReproducer;

public class Incubator extends
		geneticmanager.incubator.DefaultIncubator<Individual, Integer> {
	private IndividualFactory factory;
	private IIndividualReproducer<Individual> reproducer = new IIndividualReproducer<Individual>() {

		@Override
		public Individual reproduce(Individual i1, Individual i2) {
			return factory.createIndividualFrom(i1, i2);
		}
	};

	@Override
	public IIndividualReproducer<Individual> getReproducer() {
		return reproducer;
	}

	public IndividualFactory getFactory() {
		return factory;
	}

	public void setFactory(IndividualFactory factory) {
		this.factory = factory;
	}

}
