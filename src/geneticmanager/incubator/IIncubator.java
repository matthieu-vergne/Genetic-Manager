package geneticmanager.incubator;

import geneticmanager.individual.IIndividual;
import geneticmanager.individual.IIndividualReproducer;

//TODO tests
//TODO javadoc
//TODO simplify the crossPopulation+getReproducer feature
public interface IIncubator<Indiv extends IIndividual<Gene>, Gene> {
	public void addIndividual(Indiv individual);

	public Indiv getBestIndividual();

	public void crossPopulation();

	public void makeSelection();

	public long getGenerationCounter();

	public int getStationaryTime();

	public int getGenerationSize();

	public IIndividualReproducer<Indiv> getReproducer();
}
