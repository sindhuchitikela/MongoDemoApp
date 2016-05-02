package weight.tracker.model;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Metrics {

	@Id
	private String id; // auto-generated ID for each metrics entry
	private String value; // weight
	private String baseWeight; //base weight of this person
	private Date timeStamp; // timeStamp read from the sensor
	
	@Embedded
	Alerts alert;

	public Alerts getAlert() {
		return alert;
	}

	public void setAlert(Alerts alert) {
		this.alert = alert;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setBaseWeight(String baseWeight) {
		this.baseWeight = baseWeight;
	}
	
	public void setTimeStamp(String timeStamp) {
		if (timeStamp instanceof String) {
			this.timeStamp = new Date(Long.parseLong(timeStamp));
		}
	}

	public String getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}

	public String getBaseWeight() {
		return baseWeight;
	}

	public String getTimeStamp() {
		return String.valueOf(timeStamp.getTime());
	}	
	
}