package geneticmanager.individual;

import static org.junit.Assert.*;
import geneticmanager.Location;
import geneticmanager.individual.Individual;
import geneticmanager.individual.IndividualFactory;

import org.junit.Test;

public class IndividualTest {

	@Test
	public void testPath() {
		Location l0 = new Location(0, 0);
		Location l1 = new Location(1, 0);
		Location l2 = new Location(0, 1);
		IndividualFactory generator = new IndividualFactory();
		generator.addLocation(l0);
		generator.addLocation(l1);
		generator.addLocation(l2);

		Individual individual = new Individual(generator, 0, 1, 2);
		assertEquals(3, individual.getPath().length);
		assertEquals(l0, individual.getPath()[0]);
		assertEquals(l1, individual.getPath()[1]);
		assertEquals(l2, individual.getPath()[2]);
	}

	@Test
	public void testGenes() {
		Individual individual = new Individual(null, 5, 2, 6, 3);
		assertArrayEquals(new Integer[] { 5, 2, 6, 3 }, individual.getGenes());
	}

}
