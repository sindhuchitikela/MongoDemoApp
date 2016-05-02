package weight.tracker.dao;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

import weight.tracker.model.Alerts;
 
public class AlertsDAO extends BasicDAO<Alerts, String> {   
 
    public AlertsDAO(MongoClient mongoClient, Morphia morphia, String dbName) {       
    	super(mongoClient, morphia, dbName);
    }     
}