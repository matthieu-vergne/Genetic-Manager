package sample.TSP;

import geneticmanager.individual.IIndividualReproducer;

import java.util.Set;
import java.util.TreeSet;

public class Incubator extends
		geneticmanager.incubator.DefaultIncubator<Individual> {
	private long generationCounter = 0;
	private final Set<Individual> population = new TreeSet<Individual>();
	private int stationaryTime = 0;
	private IndividualFactory factory;
	private IIndividualReproducer<Individual> reproducer = new IIndividualReproducer<Individual>() {
		@Override
		public Individual reproduce(Individual i1, Individual i2) {
			return factory.createIndividualFrom(i1, i2);
		}
	};

	public IIndividualReproducer<Individual> getReproducer() {
		return reproducer;
	}

	public IndividualFactory getFactory() {
		return factory;
	}

	public void setFactory(IndividualFactory factory) {
		this.factory = factory;
	}

	public void crossPopulation() {
		if (reproducer == null) {
			throw new IllegalStateException(
					"No reproducer as been defined, you cannot cross the population.");
		}

		double previousBest = getBestIndividual().getLength();

		Individual father = makeSelection();
		Individual mother = makeSelection();
		population.add(reproducer.reproduce(father, mother));

		double newBest = getBestIndividual().getLength();
		if (newBest != previousBest) {
			stationaryTime = 0;
		} else {
			stationaryTime++;
		}

		generationCounter++;
	}

	public void reducePopulationTo(int size) {
		if (size < population.size()) {
			Individual[] array = population.toArray(new Individual[0]);
			for (int index = size; index < array.length; index++) {
				population.remove(array[index]);
			}
		}
	}

	private Individual makeSelection() {
		return population.toArray(new Individual[0])[Util
				.randomIndex(population.size())];
	}

	public long getGenerationCounter() {
		return generationCounter;
	}

	public int getGenerationSize() {
		return population.size();
	}

	@Override
	public Individual getBestIndividual() {
		if (population.isEmpty()) {
			return null;
		} else {
			return population.iterator().next();
		}
	}

	@Override
	public void addIndividual(Individual individual) {
		population.add(individual);
	}

	public int getStationaryTime() {
		return stationaryTime;
	}

	public void clean() {
		population.clear();
		stationaryTime = 0;
		generationCounter = 0;
	}

}
