package sample.travellingsalesman;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sample.travellingsalesman.IndividualFactory;
import sample.travellingsalesman.Location;

public class IndividualFactoryTest {

	@Test
	public void testLocation() {
		IndividualFactory generator = new IndividualFactory();
		assertEquals(0, generator.getLocations().length);

		Location l0 = new Location(0, 0);
		generator.addLocation(l0);
		assertEquals(1, generator.getLocations().length);
		assertArrayEquals(new Location[] { l0 }, generator.getLocations());

		Location l1 = new Location(0, 1);
		generator.addLocation(l1);
		assertEquals(2, generator.getLocations().length);
		assertArrayEquals(new Location[] { l0, l1 }, generator.getLocations());

		Location l2 = new Location(1, 0);
		generator.addLocation(l2);
		assertEquals(3, generator.getLocations().length);
		assertArrayEquals(new Location[] { l0, l1, l2 },
				generator.getLocations());
	}

	@Test
	public void testPathForGene() {
		Location l0 = new Location(0, 0);
		Location l1 = new Location(0, 1);
		Location l2 = new Location(1, 0);
		IndividualFactory generator = new IndividualFactory();
		generator.addLocation(l0);
		generator.addLocation(l1);
		generator.addLocation(l2);

		assertArrayEquals(new Location[] {},
				generator.getPathFor(new Integer[] {}));
		assertArrayEquals(new Location[] { l0 },
				generator.getPathFor(new Integer[] { 0 }));
		assertArrayEquals(new Location[] { l1 },
				generator.getPathFor(new Integer[] { 1 }));
		assertArrayEquals(new Location[] { l2 },
				generator.getPathFor(new Integer[] { 2 }));
		assertArrayEquals(new Location[] { l0, l1 },
				generator.getPathFor(new Integer[] { 0, 1 }));
		assertArrayEquals(new Location[] { l0, l2 },
				generator.getPathFor(new Integer[] { 0, 2 }));
		assertArrayEquals(new Location[] { l1, l0 },
				generator.getPathFor(new Integer[] { 1, 0 }));
		assertArrayEquals(new Location[] { l1, l2 },
				generator.getPathFor(new Integer[] { 1, 2 }));
		assertArrayEquals(new Location[] { l2, l0 },
				generator.getPathFor(new Integer[] { 2, 0 }));
		assertArrayEquals(new Location[] { l2, l1 },
				generator.getPathFor(new Integer[] { 2, 1 }));
		assertArrayEquals(new Location[] { l0, l1, l2 },
				generator.getPathFor(new Integer[] { 0, 1, 2 }));
		assertArrayEquals(new Location[] { l0, l2, l1 },
				generator.getPathFor(new Integer[] { 0, 2, 1 }));
		assertArrayEquals(new Location[] { l1, l0, l2 },
				generator.getPathFor(new Integer[] { 1, 0, 2 }));
		assertArrayEquals(new Location[] { l1, l2, l0 },
				generator.getPathFor(new Integer[] { 1, 2, 0 }));
		assertArrayEquals(new Location[] { l2, l0, l1 },
				generator.getPathFor(new Integer[] { 2, 0, 1 }));
		assertArrayEquals(new Location[] { l2, l1, l0 },
				generator.getPathFor(new Integer[] { 2, 1, 0 }));
	}

	@Test
	public void testRandomIndividual() {
		IndividualFactory generator = new IndividualFactory();
		generator.addLocation(new Location(0, 0));
		generator.addLocation(new Location(0, 1));
		generator.addLocation(new Location(1, 0));

		for (int loop = 0; loop < 200; loop++) {
			Integer[] genes = generator.createRandomIndividual().getGenes();
			assertEquals(generator.getLocations().length, genes.length);

			int length = genes.length;
			boolean[] used = new boolean[length];
			for (int index = 0; index < length; index++) {
				String s = " for index " + index + "/" + (length - 1);
				assertTrue(genes[index] + " is too low" + s, genes[index] >= 0);
				assertTrue(genes[index] + " is too high" + s,
						genes[index] < length);
				assertFalse(used[genes[index]]);
				used[genes[index]] = true;
			}
		}
	}

}
