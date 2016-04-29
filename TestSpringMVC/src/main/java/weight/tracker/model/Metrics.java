package weight.tracker.model;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Metrics {
	@Id
	private String id;
	private Date timeStamp; 
	private String value; 

	public Metrics() {
	}

	public Metrics(String id, String value, String timeStamp) {
		this.id = id;
		this.value = value;
		
		if (timeStamp instanceof String) {
			this.timeStamp  = new Date(Long.parseLong(timeStamp));
	    }
	}	

	public String getTimeStamp() {		
		return Long.toString(this.timeStamp.getTime());
	}

	public String getValue() {		
		return value;
	}

	public String getId() {
		return id;
	}

}