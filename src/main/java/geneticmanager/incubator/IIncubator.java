package geneticmanager.incubator;

import geneticmanager.individual.IIndividual;

//TODO tests
//TODO javadoc
//TODO see what can be considered as generic enough to be asked here
public interface IIncubator<Indiv extends IIndividual<?>> {
	public void addIndividual(Indiv individual);

	public int getGenerationSize();

	public Indiv getBestIndividual();

}
