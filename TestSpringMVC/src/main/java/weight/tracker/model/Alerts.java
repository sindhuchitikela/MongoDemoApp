package weight.tracker.model;
import java.util.Date;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Alerts {	
	private String alert; //UnderWeight or OverWeight
	private Date timeStamp;
	
	public String getTimeStamp() {
		return Long.toString(this.timeStamp.getTime());
	}

	public void setTimeStamp(String timeStamp) {
		if (timeStamp instanceof String) {
			this.timeStamp = new Date(Long.parseLong(timeStamp));
		}
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	public String getAlert() {
		return alert;
	}	
}
