package weight.tracker.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AlertTest {

	@Test
	public void testAlertsBuild() {
		// prepare test data
		String timeStamp = Long.toString(System.currentTimeMillis());
		String alert = "overWeight";

		// invoke the method to test
		Alerts alerts = new Alerts();
		alerts.setAlert(alert);
		alerts.setTimeStamp(timeStamp);

		// assert the values
		assertEquals(alert, alerts.getAlert());
		assertEquals(timeStamp, alerts.getTimeStamp());
	}
}
