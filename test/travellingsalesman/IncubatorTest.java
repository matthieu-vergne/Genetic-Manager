package travellingsalesman;

import static org.junit.Assert.*;

import org.junit.Test;

public class IncubatorTest {

	@Test
	public void testGenerator() {
		Incubator incubator = new Incubator();

		{
			IndividualFactory generator = new IndividualFactory();
			incubator.setGenerator(generator);
			assertEquals(generator, incubator.getGenerator());
		}

		{
			IndividualFactory generator = new IndividualFactory();
			incubator.setGenerator(generator);
			assertEquals(generator, incubator.getGenerator());
		}
	}

}
