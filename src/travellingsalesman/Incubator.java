package travellingsalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Incubator {
	private final ArrayList<Individual> population = new ArrayList<Individual>();
	private long generationCounter = 0;
	private int generationSize = 10;
	private boolean isDoubleKept = true;

	public Individual getBestIndividual() {
		return population.isEmpty() ? null : population.get(0);
	}

	public void addIndividual(Individual individual){
		population.add(individual);
	}

	public void crossPopulation() {
		ArrayList<Individual> availableIndividuals = new ArrayList<Individual>(
				population);
		while (availableIndividuals.size() > 1) {
			Individual i1 = availableIndividuals.remove(Util.randomIndex(availableIndividuals.size()));
			Individual i2 = availableIndividuals.remove(Util.randomIndex(availableIndividuals.size()));
			population.add(i1.reproduceWith(i2));
		}

		generationCounter++;
	}

	public long getGenerationCounter() {
		return generationCounter;
	}

	public void makeSelection() {
		if (!isDoubleKept() ) {
			removeDoubles();
		}
		reducePopulation();
	}

	private void reducePopulation() {
		Collections.sort(population, new IndividualComparator());
		while (population.size() > getGenerationSize()) {
			population.remove(0);
		}
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
}
