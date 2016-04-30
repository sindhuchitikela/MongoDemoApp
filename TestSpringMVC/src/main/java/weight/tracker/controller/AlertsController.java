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

@RestController
public class AlertsController {
	private AlertsDAO alertDao;
	private Datastore datastore;
	
	public AlertsController() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		final Morphia morphia = new Morphia();
		morphia.map(Alerts.class);		
		
		//initialize datastore and alerts DAO
		datastore = morphia.createDatastore(mongoClient, Application.DATABASE_NAME);
		alertDao = new AlertsDAO(mongoClient, morphia, Application.DATABASE_NAME);
	}
    @RequestMapping(
    		  value = "readAlerts", 
    		  method = RequestMethod.GET, 
    		  produces = "application/json"
    		)
    @ResponseBody    	    	
	public List<Alerts> readAlerts() throws UnknownHostException {	
    	Query<Alerts> query = datastore.createQuery(Alerts.class);	
		return prepareResponse(alertDao.find(query));
	}

    //http://localhost:8080/readAlertsByTimeRange?min=1&max=2
 	@RequestMapping( value = "/readAlertsByTimeRange", method = RequestMethod.GET, produces = "application/json")
 	public List<Alerts> readAlertsByTimeStamp(@RequestParam(value = "min", required = true) String min, @RequestParam(value = "max", required = true) String max) throws UnknownHostException {

 		Query<Alerts> query = datastore.createQuery(Alerts.class);
 		query.and(query.criteria("timeStamp").greaterThan(new Date(Long.parseLong(min))));
 		query.and(query.criteria("timeStamp").lessThan(new Date(Long.parseLong(max))));
 		return prepareResponse(alertDao.find(query));
 	}
 	
 	private List<Alerts> prepareResponse(QueryResults<Alerts> allRetrievedAlerts){
 		List<Alerts> response = new ArrayList<Alerts>();
 		for (Alerts retrievedAlerts : allRetrievedAlerts) {
 			response.add(retrievedAlerts);
 		}
 		return response;		
 	}  

}