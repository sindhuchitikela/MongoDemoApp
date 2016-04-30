package weight.tracker.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MetricsTest {

	@Test
	public void testMetricsBuild(){		
		//prepare test data
		String value = "123";
		String baseWeight = "140";
		String timeStamp = Long.toString(System.currentTimeMillis());
		
		//invoke the method to test
		Metrics metrics = new Metrics();
		metrics.setValue(value);
		metrics.setBaseWeight(baseWeight);
		metrics.setTimeStamp(timeStamp);
				
		//assert the values
		assertEquals(value, metrics.getValue());
		assertEquals(baseWeight, metrics.getBaseWeight());
		assertEquals(timeStamp, metrics.getTimeStamp());		
	}
}
