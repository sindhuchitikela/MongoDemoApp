package weight.tracker.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MetricsTest {

	@Test
	public void testMetricsBuild(){		
		//prepare test data
		String id = "jonathan_test_id";
		String value = "123";
		String timeStamp = Long.toString(System.currentTimeMillis());
		
		//invoke the method to test
		Metrics metrics = new Metrics(id, value, timeStamp);
		
		//assert the values
		assertEquals(id, metrics.getId());
		assertEquals(value, metrics.getValue());
		assertEquals(timeStamp, metrics.getTimeStamp());		
	}
}
