package weight.tracker.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Metrics {
	@Id
	private String id;
	private String timeStamp; 
	private String value; 

	public Metrics() {
	}

	public Metrics(String id, String value, String timeStamp) {
		this.id = id;
		this.value = value;
		this.timeStamp = timeStamp;
	}	

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getValue() {		
		return value;
	}

	public String getId() {
		return id;
	}

}