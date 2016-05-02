package weight.tracker.controller;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.easyrules.api.RulesEngine;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

import weight.tracker.dao.MetricsDAO;
import weight.tracker.model.Alerts;
import weight.tracker.model.Metrics;
import weight.tracker.rules.OverWeightRule;
import weight.tracker.rules.UnderWeightRule;

/*
 * Defines REST APIs for create and read operations on weight metrics of the personal weight tracker application
 */
@RestController
public class MetricsController {

	private MetricsDAO metricsDao;
	private Datastore datastore;

	public MetricsController() throws UnknownHostException {
		try{
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		final Morphia morphia = new Morphia();
		morphia.map(Metrics.class, Alerts.class);

		// initialize datastore and metrics DAO
		datastore = morphia.createDatastore(mongoClient, WeightTrackerApplication.DATABASE_NAME);
		metricsDao = new MetricsDAO(mongoClient, morphia, WeightTrackerApplication.DATABASE_NAME);
		} catch (UnknownHostException e) {
			System.out.println("Exception occurred while establishing connection to MongoDB or creating the database");
		}
	}

	/**
	 * Reads the input request body, creates and saves metrics and alerts in the
	 * database.
	 * Sample request body:{"value":"1","baseWeight":"100","timeStamp":"12345566777"}
	 * @param metrics
	 *            Metrics request body
	 * @return response object which indicates if the save is successful
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "createMetrics", method = RequestMethod.POST)
	public Map<String, Object> createMetrics(@RequestBody Metrics metrics) throws UnknownHostException {

		// ============To generate alerts, fire rules on the metrics
		// received======
		// create a rules engine
		RulesEngine rulesEngine = aNewRulesEngine().build();

		// register the rule
		OverWeightRule overWtRule = new OverWeightRule(metrics);
		UnderWeightRule underWtRule = new UnderWeightRule(metrics);
		rulesEngine.registerRule(overWtRule);
		rulesEngine.registerRule(underWtRule);

		// fire rules
		rulesEngine.fireRules();

		// read and create the alerts
		Alerts alert = new Alerts();
		if (overWtRule.isExecuted()) {
			alert.setAlert(overWtRule.getResult());
		} else if (underWtRule.isExecuted()) {
			alert.setAlert(underWtRule.getResult());
		}
		alert.setTimeStamp(metrics.getTimeStamp());

		// ================= Save Alerts and Metrics===================

		datastore.save(alert);
		metrics.setAlert(alert);
		datastore.save(metrics);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Metrics stored successfully");
		response.put("Metrics", metrics);

		return response;
	}

	/**
	 * Reads all weight metrics from the database Sample URI to use:
	 * http://localhost:8080/readMetrics
	 * 
	 * @return a list of Metrics objects in JSON format
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "readMetrics", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Metrics> readMetrics() throws UnknownHostException {
		Query<Metrics> query = datastore.createQuery(Metrics.class);
		return prepareResponse(metricsDao.find(query));
	}

	/**
	 * Reads weight metrics that are with in the given time range. Sample URI to
	 * use:
	 * http://localhost:8080/readMetricsByTimeRange?min=1788997656&max=29097665
	 * 
	 * @param min
	 *            begin timestamp value of the range
	 * @param max
	 *            end timestamp value of the range
	 * @return a list of Metrics objects
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/readMetricsByTimeRange", method = RequestMethod.GET, produces = "application/json")
	public List<Metrics> readMetricsByTimeStamp(@RequestParam(value = "min", required = true) String min,
			@RequestParam(value = "max", required = true) String max) throws UnknownHostException {

		Query<Metrics> query = datastore.createQuery(Metrics.class);
		query.and(query.criteria("timeStamp").greaterThan(new Date(Long.parseLong(min))));
		query.and(query.criteria("timeStamp").lessThan(new Date(Long.parseLong(max))));
		return prepareResponse(metricsDao.find(query));
	}

	/**
	 * A helper method to prepare response objects from the mongo DB query
	 * result set
	 * 
	 * @param allRetrievedMetrics
	 *            mongoDB query results
	 * @return a list of Metrics objects
	 */
	private List<Metrics> prepareResponse(QueryResults<Metrics> allRetrievedMetrics) {
		List<Metrics> response = new ArrayList<Metrics>();
		for (Metrics retrievedMetrics : allRetrievedMetrics) {
			response.add(retrievedMetrics);
		}
		return response;
	}

}