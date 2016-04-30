//package weight.tracker.controller;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.springframework.test.util.MatcherAssertionErrors.assertThat;
//
//import java.util.Collections;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.web.client.RestTemplate;
//
//import weight.tracker.model.Metrics;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = WeightTrackerApplication.class)
//public class MetricsControllerTest {
//	final String BASE_URL = "http://localhost:8080/";
//
//	@Test
//	public void testCreateMetrics() {
//
//		Metrics metrics = new Metrics();
//		metrics.setValue("150");// weight
//		metrics.setBaseWeight("120");// base weight of this person
//		metrics.setTimeStamp(String.valueOf(System.currentTimeMillis()));
//
//		RestTemplate rest = new TestRestTemplate();
//
//		ResponseEntity<Metrics> response = rest.postForEntity(BASE_URL, metrics, Metrics.class, Collections.EMPTY_MAP);
//		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
//
//		Metrics metricsCreated = response.getBody();
//		System.out.println(metricsCreated);
//	}
//
//}