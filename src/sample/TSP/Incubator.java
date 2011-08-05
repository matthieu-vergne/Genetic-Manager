package sample.TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import geneticmanager.individual.IIndividualReproducer;

public class Incubator extends
		geneticmanager.incubator.DefaultIncubator<Individual> {
	private long generationCounter = 0;
	private final ArrayList<Individual> population = new ArrayList<Individual>();
	private int stationaryTime = 0;
	private boolean isDoubleKept = true;
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
		ArrayList<Individual> availableIndividuals = new ArrayList<Individual>(
				population);
		while (availableIndividuals.size() > 1) {
			Individual i1 = availableIndividuals.remove(Util
					.randomIndex(availableIndividuals.size()));
			Individual i2 = availableIndividuals.remove(Util
					.randomIndex(availableIndividuals.size()));
			population.add(reproducer.reproduce(i1, i2));
		}

		generationCounter++;
	}

	public long getGenerationCounter() {
		return generationCounter;
	}

	private void removeDoubles() {
		ArrayList<Individual> temp = new ArrayList<Individual>(population);
		for (Individual i1 : temp) {
			for (Individual i2 : temp) {
				if (i1 != i2 && Arrays.deepEquals(i1.getGenes(), i2.getGenes())) {
					population.remove(i2);
				}
			}
		}
	}

	private void reducePopulation() {
		Collections.sort(population);
		while (population.size() > getGenerationSize()) {
			population.remove(getGenerationSize());
		}
	}

	public void makeSelection() {
		Individual previousResult = getBestIndividual();

		if (!isDoubleKept()) {
			removeDoubles();
		}
		reducePopulation();

		Individual newResult = getBestIndividual();
		if (newResult.compareTo(previousResult) != 0) {
			stationaryTime = 0;
		} else {
			stationaryTime++;
		}
	}

	private int generationSize = 10;

	public int getGenerationSize() {
		return generationSize;
	}

	public void setGenerationSize(int generationSize) {
		this.generationSize = generationSize;
	}

	@Override
	public Individual getBestIndividual() {
		return population.isEmpty() ? null : population.get(0);
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

	public boolean isDoubleKept() {
		return isDoubleKept;
	}

	public void setDoubleKept(boolean isDoubleKept) {
		this.isDoubleKept = isDoubleKept;
	}

}
