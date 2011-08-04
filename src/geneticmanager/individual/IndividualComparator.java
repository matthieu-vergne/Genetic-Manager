package geneticmanager.individual;

import java.util.Comparator;

public class IndividualComparator implements Comparator<Individual> {
	@Override
	public int compare(Individual i1, Individual i2) {
		return Double.valueOf(i1.getLength()).compareTo(
				Double.valueOf(i2.getLength()));
	}
}
