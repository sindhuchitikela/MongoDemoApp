package weight.tracker.controller;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;

import weight.tracker.model.Metrics;

@RestController
public class AlertsController {
	
	private static String dbName = "WeightTracker";	

    @RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createMetrics(@RequestBody Metrics metrics) throws UnknownHostException {
    	//fire rules on the metrics received
    	// create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine().build();
        
        //register the rule
        rulesEngine.registerRule(new OverWeightRule(metrics));
        rulesEngine.registerRule(new UnderWeightRule(metrics));
        //fire rules
        rulesEngine.fireRules();
                       
        
    	//save metrics
    	MongoClient mongoClient = new MongoClient("localhost", 27017);
 		final Morphia morphia = new Morphia();
 		morphia.map(Metrics.class); 	        
 	    final Datastore datastore = morphia.createDatastore(mongoClient, dbName);
		datastore.save(metrics);	
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		
		response.put("message", "Metrics stored successfully");
		response.put("Metrics", metrics);
		return response;
	}

    @RequestMapping(
    		  value = "readmetrics", 
    		  method = RequestMethod.GET, 
    		  produces = "application/json"
    		)
    @ResponseBody    	    	
	public List<Metrics> readMetrics() throws UnknownHostException {	
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
//	public Metrics readMetricsByTimeStamp(@PathVariable("timeStamp") String timeStampValue) throws UnknownHostException {
//    	MongoClient mongoClient = new MongoClient("localhost", 27017);
// 		final Morphia morphia = new Morphia();
// 		morphia.map(Metrics.class);
// 		 		
// 	    final Datastore datastore = morphia.createDatastore(mongoClient, "WeightTracker");
//		Query<Metrics> query = datastore.createQuery(Metrics.class);
//		query.and(query.criteria("timeStamp").equal(timeStampValue));
//		
//		return new Metrics("123","34","56");
//    	
//	}   

}