package travellingsalesman;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testRandomIndex() {
		int[] found = new int[20];
		int minRun = found.length * 10;
		while (product(found) == 0 && minRun > 0) {
			int index = Util.randomIndex(found.length);
			assertTrue(index < 20);
			assertTrue(index >= 0);
			found[index]++;
			minRun--;
		}
	}

	private long product(int[] integers) {
		long result = 1;
		for (int i : integers) {
			result *= i;
		}
		return result;
	}

	@Test
	public void testPathLength() {
		assertEquals(
				Math.sqrt(2),
				Util.getPathLength(new Location[] { new Location(0, 0),
						new Location(1, 1) }), 0);

		assertEquals(
				2,
				Util.getPathLength(new Location[] { new Location(0, 0),
						new Location(1, 0), new Location(1, 1) }), 0);

		assertEquals(
				2 * Math.sqrt(2),
				Util.getPathLength(new Location[] { new Location(0, 0),
						new Location(1, 1), new Location(2, 0) }), 0);
	}
}
