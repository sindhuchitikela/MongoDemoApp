package weight.tracker.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

import weight.tracker.dao.AlertsDAO;
import weight.tracker.model.Alerts;

/*
 * Defines REST APIs for read operations on weight alerts of the personal weight tracker application
 */
@RestController
public class AlertsController {
	private AlertsDAO alertDao;
	private Datastore datastore;

	public AlertsController() throws UnknownHostException {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			final Morphia morphia = new Morphia();
			morphia.map(Alerts.class);

			// initialize datastore and alerts DAO
			datastore = morphia.createDatastore(mongoClient, WeightTrackerApplication.DATABASE_NAME);
			alertDao = new AlertsDAO(mongoClient, morphia, WeightTrackerApplication.DATABASE_NAME);
		} catch (UnknownHostException e) {
			System.out.println("Exception occurred while establishing connection to MongoDB or creating the database");
		}
	}

	/**
	 * Reads all weight alerts from the database. Sample URI to use:
	 * http://localhost:8080/readAlerts
	 * 
	 * @return a list of Alerts objects in JSON format
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "readAlerts", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Alerts> readAlerts() throws UnknownHostException {
		Query<Alerts> query = datastore.createQuery(Alerts.class);
		return prepareResponse(alertDao.find(query));
	}

	/**
	 * Reads weight alerts that are with in the given time range. Sample URI to
	 * use:
	 * http://localhost:8080/readAlertsByTimeRange?min=1788997656&max=29097665
	 * 
	 * @param min
	 *            begin timestamp value of the range
	 * @param max
	 *            end timestamp value of the range
	 * @return list of Alerts objects
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/readAlertsByTimeRange", method = RequestMethod.GET, produces = "application/json")
	public List<Alerts> readAlertsByTimeStamp(@RequestParam(value = "min", required = true) String min,
			@RequestParam(value = "max", required = true) String max) throws UnknownHostException {

		Query<Alerts> query = datastore.createQuery(Alerts.class);
		query.and(query.criteria("timeStamp").greaterThan(new Date(Long.parseLong(min))));
		query.and(query.criteria("timeStamp").lessThan(new Date(Long.parseLong(max))));
		return prepareResponse(alertDao.find(query));
	}

	/**
	 * A helper method to prepare response objects from the mongo DB query
	 * result set
	 * 
	 * @param allRetrievedAlerts
	 *            mongoDB query results
	 * @return a list of Alerts objects
	 */
	private List<Alerts> prepareResponse(QueryResults<Alerts> allRetrievedAlerts) {
		List<Alerts> response = new ArrayList<Alerts>();
		for (Alerts retrievedAlerts : allRetrievedAlerts) {
			response.add(retrievedAlerts);
		}
		return response;
	}
}