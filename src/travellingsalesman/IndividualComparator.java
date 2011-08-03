package travellingsalesman;

import java.util.Comparator;

public class IndividualComparator implements Comparator<Individual> {
	@Override
	public int compare(Individual i1, Individual i2) {
		return Double.valueOf(Util.getPathLength(i2.getPath())).compareTo(
				Double.valueOf(Util.getPathLength(i1.getPath())));
	}
}
