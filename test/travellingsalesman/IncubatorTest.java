package travellingsalesman;

import static org.junit.Assert.*;

import org.junit.Test;

public class IncubatorTest {

	@Test
	public void testGenerator() {
		Incubator incubator = new Incubator();

		{
			IndividualGenerator generator = new IndividualGenerator();
			incubator.setGenerator(generator);
			assertEquals(generator, incubator.getGenerator());
		}

		{
			IndividualGenerator generator = new IndividualGenerator();
			incubator.setGenerator(generator);
			assertEquals(generator, incubator.getGenerator());
		}
	}

}
