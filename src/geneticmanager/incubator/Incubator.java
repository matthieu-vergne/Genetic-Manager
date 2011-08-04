package geneticmanager.incubator;

// TODO tests
// TODO javadoc
import geneticmanager.individual.Individual;
import geneticmanager.individual.IndividualReproducer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import sample.TSP.Util;

abstract public class Incubator<Indiv extends Individual<Gene>, Gene> {
	private final ArrayList<Indiv> population = new ArrayList<Indiv>();
	private long generationCounter = 0;
	private int generationSize = 10;
	private int stationaryTime = 0;
	private boolean isDoubleKept = true;

	public Indiv getBestIndividual() {
		return population.isEmpty() ? null : population.get(0);
	}

	public void addIndividual(Indiv individual) {
		population.add(individual);
	}

	public void crossPopulation() {
		IndividualReproducer<Indiv> reproducer = getReproducer();
		if (reproducer == null) {
			throw new IllegalStateException(
					"No reproducer as been defined, you cannot cross the population.");
		}
		ArrayList<Indiv> availableIndividuals = new ArrayList<Indiv>(population);
		while (availableIndividuals.size() > 1) {
			Indiv i1 = availableIndividuals.remove(Util
					.randomIndex(availableIndividuals.size()));
			Indiv i2 = availableIndividuals.remove(Util
					.randomIndex(availableIndividuals.size()));
			population.add(reproducer.reproduce(i1, i2));
		}

		generationCounter++;
	}

	public long getGenerationCounter() {
		return generationCounter;
	}

	public void makeSelection() {
		Indiv previousResult = getBestIndividual();

		if (!isDoubleKept()) {
			removeDoubles();
		}
		reducePopulation();

		Indiv newResult = getBestIndividual();
		if (newResult.compareTo(previousResult) != 0) {
			stationaryTime = 0;
		} else {
			stationaryTime++;
		}
	}

	public int getStationaryTime() {
		return stationaryTime;
	}

	private void reducePopulation() {
		Collections.sort(population);
		while (population.size() > getGenerationSize()) {
			population.remove(getGenerationSize());
		}
	}

	private void removeDoubles() {
		ArrayList<Indiv> temp = new ArrayList<Indiv>(population);
		for (Indiv i1 : temp) {
			for (Indiv i2 : temp) {
				if (i1 != i2 && Arrays.deepEquals(i1.getGenes(), i2.getGenes())) {
					population.remove(i2);
				}
			}
		}
	}

	public int getGenerationSize() {
		return generationSize;
	}

	public void setGenerationSize(int generationSize) {
		this.generationSize = generationSize;
	}

	public boolean isDoubleKept() {
		return isDoubleKept;
	}

	public void setDoubleKept(boolean isDoubleKept) {
		this.isDoubleKept = isDoubleKept;
	}

	public void clean() {
		population.clear();
		stationaryTime = 0;
		generationCounter = 0;
	}

	abstract public IndividualReproducer<Indiv> getReproducer();
}
