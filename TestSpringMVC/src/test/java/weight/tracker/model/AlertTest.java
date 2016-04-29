package weight.tracker.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AlertTest{

	@Test
	public void testAlertsBuild(){		
		//prepare test data
		String id = "jonathan_test_id";
		String alert = "overWeight";
		
		//invoke the method to test
		Alerts alerts = new Alerts(id, alert);
		
		//assert the values
		assertEquals(id, alerts.id);
		assertEquals(alert, alerts.getAlert());
	}
}
