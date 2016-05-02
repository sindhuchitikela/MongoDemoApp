package weight.tracker.rules;

import java.net.UnknownHostException;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import weight.tracker.model.Metrics;

@Rule(name = "over weight rule")
public class OverWeightRule {
	final static String OVER_WEIGHT_INDICATOR = "OverWeight";
	private Metrics metrics;
	private boolean executed;

	public OverWeightRule(Metrics metrics) {
		this.metrics = metrics;
	}

	@Condition
	public boolean when() {
		double baseWt = Double.parseDouble(metrics.getBaseWeight());
		if (Double.parseDouble(metrics.getValue()) > (baseWt + 0.1 * baseWt)) { // If
																				// weight
																				// shoot
																				// 10%
																				// over
																				// his
																				// base
																				// weight
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
		return OVER_WEIGHT_INDICATOR;
	}
}
