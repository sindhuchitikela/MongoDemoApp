package weight.tracker.controller;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

import weight.tracker.dao.MetricsDAO;
import weight.tracker.model.Metrics;

@RestController
public class AlertsController {
	
	private static String dbName = "WeightTracker";	

   
    @RequestMapping(
    		  value = "readalerts", 
    		  method = RequestMethod.GET, 
    		  produces = "application/json"
    		)
    @ResponseBody    	    	
	public List<Metrics> readAlerts() throws UnknownHostException {	
    	MongoClient mongoClient = new MongoClient("localhost", 27017);
 		final Morphia morphia = new Morphia();
 		morphia.map(Metrics.class); 		 		
 	    final Datastore datastore = morphia.createDatastore(mongoClient, dbName);
		Query<Metrics> query = datastore.createQuery(Metrics.class);
		MetricsDAO metricsDao = new MetricsDAO(mongoClient, morphia, dbName);
		QueryResults<Metrics> allRetrievedMetrics = metricsDao.find(query);
		List<Metrics> response = new ArrayList<Metrics>();
		for (Metrics retrievedMetrics : allRetrievedMetrics) {
			response.add(retrievedMetrics);			
		}
		return response;
	}

//    @RequestMapping(method = RequestMethod.GET, value="/{timestamp}")
//	public Metrics readAlertsByTimeRange(@PathVariable("timeStamp") String timeStampValue) throws UnknownHostException {
//    	MongoClient mongoClient = new MongoClient("localhost", 27017);
// 		final Morphia morphia = new Morphia();
// 		morphia.map(Metrics.class);
// 		 		
// 	    final Datastore datastore = morphia.createDatastore(mongoClient, "WeightTracker");
//		Query<Metrics> query = datastore.createQuery(Metrics.class);
//		query.and(query.criteria("timeStamp").greaterThan()(timeStampValue));
//		
//		return new Metrics("123","34","56");
//    	
//	}   

}