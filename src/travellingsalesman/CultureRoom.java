package travellingsalesman;

import java.util.ArrayList;
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
}
