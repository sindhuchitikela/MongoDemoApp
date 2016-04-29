package weight.tracker.dao;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

import weight.tracker.model.Metrics;
 
public class AlertsDAO extends BasicDAO<Metrics, String> {   
 
    public AlertsDAO(MongoClient mongoClient, Morphia morphia, String dbName) {       
    	super(mongoClient, morphia, dbName);
    }     
}