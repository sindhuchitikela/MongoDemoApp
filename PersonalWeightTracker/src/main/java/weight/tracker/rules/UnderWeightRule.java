package weight.tracker.rules;

import java.net.UnknownHostException;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import weight.tracker.model.Metrics;

@Rule(name = "under weight rule")
public class UnderWeightRule {
	final static String UNDER_WEIGHT_INDICATOR = "UnderWeight";
	private Metrics metrics;
	private boolean executed;

	public UnderWeightRule(Metrics metrics) {
		this.metrics = metrics;
	}

	@Condition
	public boolean when() {
		double baseWt = Double.parseDouble(metrics.getBaseWeight());
		if (Double.parseDouble(metrics.getValue()) < (0.1 * baseWt + baseWt)) {// If
																				// weight
																				// drop
																				// below
																				// 10%
																				// of
																				// the
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
		return UNDER_WEIGHT_INDICATOR;
	}
}
