package weight.tracker.controller;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.BSONTimestamp;
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
import weight.tracker.model.Metrics;
import weight.tracker.rules.OverWeightRule;
import weight.tracker.rules.UnderWeightRule;
import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

@RestController
public class MetricsController {
	
	private static String dbName = "WeightTracker";	
	private static MongoClient mongoClient = new MongoClient("localhost", 27017);


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
    		  value = "readMetrics", 
    		  method = RequestMethod.GET, 
    		  produces = "application/json"
    		)
    @ResponseBody    	    	
	public List<Metrics> readMetrics() throws UnknownHostException {	
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

    //http://localhost:8080/readMetricsByTimeRange?min=1&max=2
    @RequestMapping(method = RequestMethod.GET, value="/readMetricsByTimeRange")
	public List<Metrics> readMetricsByTimeStamp(@RequestParam(value = "min", required = true) String min, @RequestParam(value = "max", required = true) String max) throws UnknownHostException {
    	MongoClient mongoClient = new MongoClient("localhost", 27017);
 		final Morphia morphia = new Morphia();
 		morphia.map(Metrics.class);
 		 		
 	    final Datastore datastore = morphia.createDatastore(mongoClient, "WeightTracker");
		Query<Metrics> query = datastore.createQuery(Metrics.class);
		query.and(query.criteria("timeStamp").greaterThan(new Date(Long.parseLong(min))));
		query.and(query.criteria("timeStamp").lessThan(new Date(Long.parseLong(max))));
		MetricsDAO metricsDao = new MetricsDAO(mongoClient, morphia, dbName);

		
		QueryResults<Metrics> allRetrievedMetrics = metricsDao.find(query);
		List<Metrics> response = new ArrayList<Metrics>();
		for (Metrics retrievedMetrics : allRetrievedMetrics) {
			response.add(retrievedMetrics);			
		}
		return response;
    	
	}   

}