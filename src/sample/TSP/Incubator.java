package sample.TSP;

import geneticmanager.individual.IndividualReproducer;

public class Incubator extends
		geneticmanager.incubator.Incubator<Individual, Integer> {
	private IndividualFactory factory;
	private IndividualReproducer<Individual> reproducer = new IndividualReproducer<Individual>() {

		@Override
		public Individual reproduce(Individual i1, Individual i2) {
			return factory.createIndividualFrom(i1, i2);
		}
	};

	@Override
	public IndividualReproducer<Individual> getReproducer() {
		return reproducer;
	}

	public IndividualFactory getFactory() {
		return factory;
	}

	public void setFactory(IndividualFactory factory) {
		this.factory = factory;
	}

}