package travellingsalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CultureRoom {
	private final List<Incubator> incubators = new ArrayList<Incubator>();

	public void add(Incubator incubator){
		incubators.add(incubator);
	}
	
	public Incubator getIncubator(int index){
		return incubators.get(index);
	}
	
	public int getIncubatorCounter(){
		return incubators.size();
	}
	
	public Incubator[] getIncubators(){
		return incubators.toArray(new Incubator[0]);
	}
	
	public Incubator getBestIncubator(){
		List<Incubator> temp = new ArrayList<Incubator>(incubators);
		Collections.sort(temp, new Comparator<Incubator>() {
			IndividualComparator comparator = new IndividualComparator();
			@Override
			public int compare(Incubator o1, Incubator o2) {
				return comparator.compare(o1.getBestIndividual(), o2.getBestIndividual());
			}
		});
		return temp.get(0);
	}
}
