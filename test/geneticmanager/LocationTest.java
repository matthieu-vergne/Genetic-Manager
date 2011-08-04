package geneticmanager;

import static org.junit.Assert.*;
import geneticmanager.Location;

import org.junit.Test;

public class LocationTest {

	@Test
	public void testCoords() {
		Location location = new Location(5, 1);
		assertEquals(5, location.getX(), 0);
		assertEquals(1, location.getY(), 0);
	}

	@Test
	public void testToString() {
		Location location = new Location(5, 1);
		assertEquals("(5,1)", location.toString());
	}

}
