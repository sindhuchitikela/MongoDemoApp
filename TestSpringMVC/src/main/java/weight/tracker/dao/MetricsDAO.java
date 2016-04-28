package weight.tracker.controller;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

import weight.tracker.model.Metrics;
 
public class MetricsDAO extends BasicDAO<Metrics, String> {   
 
    public MetricsDAO(MongoClient mongoClient, Morphia morphia, String dbName) {       
    	super(mongoClient, morphia, dbName);
    }     
}