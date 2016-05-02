package weight.tracker.rules;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easyrules.api.RulesEngine;
import org.junit.Test;

import weight.tracker.model.Metrics;

public class OverWeightRuleTest {
	@Test
	public void testOverWeightTrueCase() {
		Metrics metrics = new Metrics();
		metrics.setValue("150");// weight
		metrics.setBaseWeight("120");// base weight of this person
		metrics.setTimeStamp(String.valueOf(System.currentTimeMillis()));

		// fire OverWeightRule on this data
		OverWeightRule overWtRule = fireRules(metrics);

		// read and create the alerts
		assertTrue(overWtRule.isExecuted());
		assertEquals(OverWeightRule.OVER_WEIGHT_INDICATOR, overWtRule.getResult());
	}

	@Test
	public void testOverWeightFalseCase() {
		Metrics metrics = new Metrics();
		metrics.setValue("90");// weight
		metrics.setBaseWeight("120");// base weight of this person
		metrics.setTimeStamp(String.valueOf(System.currentTimeMillis()));

		// fire OverWeightRule on this data
		OverWeightRule overWtRule = fireRules(metrics);

		// read and create the alerts
		assertFalse(overWtRule.isExecuted());
	}

	// =================Test Helper Methods=============
	private OverWeightRule fireRules(Metrics metrics) {
		// create a rules engine
		RulesEngine rulesEngine = aNewRulesEngine().build();

		// register the rule
		OverWeightRule overWtRule = new OverWeightRule(metrics);
		rulesEngine.registerRule(overWtRule);

		// fire rules
		rulesEngine.fireRules();

		return overWtRule;
	}
}
