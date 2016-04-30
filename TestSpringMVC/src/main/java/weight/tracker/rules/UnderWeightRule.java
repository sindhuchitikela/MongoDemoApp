package weight.tracker.rules;

import java.net.UnknownHostException;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import weight.tracker.model.Metrics;

@Rule(name = "under weight rule")
public class UnderWeightRule {

	Metrics metrics;
	private boolean executed;

	public UnderWeightRule(Metrics metrics) {
		this.metrics = metrics;
	}

	@Condition
	public boolean when() {
		if (Double.parseDouble(metrics.getValue()) <= 18.5) {
			return true;
		}
		return false;
	}

	@Action
	public void then() throws UnknownHostException {
		executed = true;
	}

	public boolean isExecuted() {
		return executed;
	}

	public String getResult() {
		return "UnderWeight";
	}
}
