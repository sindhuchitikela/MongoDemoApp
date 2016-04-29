package weight.tracker.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Alerts {
	@Id
	String id;
	String alert;

	public Alerts(String id, String alert) {
		this.id = id;
		this.alert = alert;
	}

	public String getAlert() {
		return alert;
	}

	
}
