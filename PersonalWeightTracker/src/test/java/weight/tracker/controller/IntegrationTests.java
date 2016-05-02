package weight.tracker.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import weight.tracker.model.Metrics;

/**
 * To run these tests, Springboot application should be running.
 * To run the springboot app, mongodb should be running
 * 
 *
 */
public class IntegrationTests {
	final String BASE_URL = "http://localhost:8080/";

	@Test
	public void testCreateMetrics() {
		String testBaseWeight = "120";

		Calendar cal = Calendar.getInstance(); // current date and time
		cal.add(Calendar.DAY_OF_MONTH, 2);
		String testTimeStamp1 = String.valueOf(cal.getTimeInMillis());

		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		String testTimeStamp2 = String.valueOf(cal.getTimeInMillis());

		Metrics metrics1 = new Metrics();
		metrics1.setValue("119");// weight
		metrics1.setBaseWeight(testBaseWeight);// base weight of this person
		metrics1.setTimeStamp(testTimeStamp1);

		Metrics metrics2 = new Metrics();
		metrics2.setValue("150");// weight
		metrics2.setBaseWeight(testBaseWeight);// base weight of this person
		metrics2.setTimeStamp(testTimeStamp2);

		RestTemplate rest = new TestRestTemplate();

		// ===========Test Metrics POST call=============		
		ResponseEntity<Metrics> postResponse = rest.postForEntity(BASE_URL + "createMetrics", metrics1, Metrics.class,
				Collections.<String, Object> emptyMap());
		assertEquals(HttpStatus.OK, postResponse.getStatusCode());

		postResponse = rest.postForEntity(BASE_URL + "createMetrics", metrics2, Metrics.class,
				Collections.<String, Object> emptyMap());
		assertEquals(HttpStatus.OK, postResponse.getStatusCode());

		// ===========Test Metrics GET call===============
		List getResponse = rest.getForObject(BASE_URL + "readMetrics", List.class);

		// check if the metrics saved in previous call are returned
		assertMetricsResults(getResponse, metrics1, true, metrics2, true);
		
		// =============Test Metrics GET with in time range call==================
		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String min = String.valueOf(cal.getTimeInMillis());

		cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 3);
		String max = String.valueOf(cal.getTimeInMillis());
		getResponse = rest.getForObject(BASE_URL + "readMetricsByTimeRange?min=" + min + "&max=" + max, List.class);
		// check if the metrics saved in previous call are returned
		assertMetricsResults(getResponse, metrics1, true, metrics2, false);	
		
		//==============Test Alerts GET call==========================
		ResponseEntity<List> getAlertsResponse = rest.getForEntity(BASE_URL + "readAlerts", List.class);
		assertEquals(HttpStatus.OK, getAlertsResponse.getStatusCode());
		assertFalse(getAlertsResponse.getBody().isEmpty());
		assertTrue(getAlertsResponse.getBody().size()>2);
		
		//==============Test Alerts by time range GET call===
		getAlertsResponse = rest.getForEntity(BASE_URL + "readAlertsByTimeRange?min=" + min + "&max=" + max, List.class);
		assertEquals(HttpStatus.OK, getAlertsResponse.getStatusCode());
		assertFalse(getAlertsResponse.getBody().isEmpty());
		assertTrue(getAlertsResponse.getBody().size()>2);
	}
	
	//==================Helper methods================
	public void assertMetricsResults(List getResponse, Metrics metrics1, boolean metrics1Exist, Metrics metrics2, boolean metrics2Exist){
		boolean expectedValue1Found = false, expectedValue2Found = false;
		for (Object getResponseBody : getResponse) {
			GsonBuilder gson = new GsonBuilder().setDateFormat(DateFormat.FULL).registerTypeAdapter(Date.class,
					new DateDeserializer());
			Metrics getResponseMetrics = (gson.create().fromJson(getResponseBody.toString(), Metrics.class));
			if (getResponseMetrics.getTimeStamp().equals(metrics1.getTimeStamp())
					&& getResponseMetrics.getValue().equals(metrics1.getValue())
					&& getResponseMetrics.getBaseWeight().equals(metrics1.getBaseWeight())) {
				expectedValue1Found = true;
			}
			if (getResponseMetrics.getTimeStamp().equals(metrics2.getTimeStamp())
					&& getResponseMetrics.getValue().equals(metrics2.getValue())
					&& getResponseMetrics.getBaseWeight().equals(metrics2.getBaseWeight())) {
				expectedValue2Found = true;
			}
		}
		assertEquals(metrics1Exist, expectedValue1Found);
		assertEquals(metrics2Exist, expectedValue2Found);
	}
	//===============Helper Class=======================
	public class DateDeserializer implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2)
				throws JsonParseException {
			String date = element.getAsString();
			return new Date(Long.valueOf(date));
		}
	}

}