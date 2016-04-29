package weight.tracker.rules;

import java.net.UnknownHostException;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import weight.tracker.model.Alerts;
import weight.tracker.model.Metrics;

@Rule(name = "over weight rule")
public class OverWeightRule {
	private static String dbName = "WeightTracker";	

	Metrics metrics;
	public OverWeightRule(Metrics metrics){
		this.metrics = metrics;
	}	
	@Condition
	public boolean when() {
		if (Double.parseDouble(metrics.getValue()) > 18.5) {
			return true;
		}
		return false;
	}

	@Action
	public void then() throws UnknownHostException {
		Alerts alert = new Alerts(metrics.getId(), "OverWeight");
    	//save alerts
    	MongoClient mongoClient = new MongoClient("localhost", 27017);
 		final Morphia morphia = new Morphia();
 		morphia.map(Alerts.class); 	        
 	    final Datastore datastore = morphia.createDatastore(mongoClient, dbName);
		datastore.save(alert);
	}
}
