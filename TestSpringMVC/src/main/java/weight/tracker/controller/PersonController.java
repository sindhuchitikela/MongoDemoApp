//package weight.tracker.controller;
//
//import java.net.UnknownHostException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import org.mongodb.morphia.Datastore;
//import org.mongodb.morphia.Morphia;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mongodb.MongoClient;
//
//import weight.tracker.model.Metrics;
//import weight.tracker.model.Person;
//
//@RestController
//public class PersonController {
//	private static String dbName = "WeightTracker";
//	private static MongoClient mongoClient = new MongoClient("localhost", 27017);
//	@RequestMapping(value = "createPerson", method = RequestMethod.POST)
//	public Map<String, Object> createPerson(@RequestBody Person person) throws UnknownHostException {
//		
//		//save person
//		final Morphia morphia = new Morphia();
//		morphia.map(Person.class);
//		final Datastore datastore = morphia.createDatastore(mongoClient, dbName);
//		
//		Metrics metrics = new Metrics();
//		person.setMetrics(metrics);
//		datastore.save(metrics);
//		datastore.save(person);
//
//		Map<String, Object> response = new LinkedHashMap<String, Object>();
//
//		response.put("message", "Person stored successfully");
//		response.put("Person", person);
//
//		return response;
//	}
//}
