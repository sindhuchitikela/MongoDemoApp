package weight.tracker.rules;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easyrules.api.RulesEngine;
import org.junit.Test;

import weight.tracker.model.Metrics;

public class UnderWeightRuleTest {
	@Test
	public void testOverWeightFalseCase() {
		Metrics metrics = new Metrics();
		metrics.setValue("150");// weight
		metrics.setBaseWeight("120");// base weight of this person
		metrics.setTimeStamp(String.valueOf(System.currentTimeMillis()));

		// fire OverWeightRule on this data
		UnderWeightRule underWtRule = fireRules(metrics);
		assertFalse(underWtRule.isExecuted());	
	}
	
	@Test
	public void testOverWeightTrueCase() {
		Metrics metrics = new Metrics();
		metrics.setValue("90");// weight 
		metrics.setBaseWeight("120");// base weight of this person
		metrics.setTimeStamp(String.valueOf(System.currentTimeMillis()));

		// fire OverWeightRule on this data
		UnderWeightRule underWtRule = fireRules(metrics);
		assertTrue(underWtRule.isExecuted());
		assertEquals(UnderWeightRule.UNDER_WEIGHT_INDICATOR, underWtRule.getResult());
	}

	//=================Test Helper Methods=============
	private UnderWeightRule fireRules(Metrics metrics) {
		// create a rules engine
		RulesEngine rulesEngine = aNewRulesEngine().build();

		// register the rule
		UnderWeightRule underWtRule= new UnderWeightRule(metrics);
		rulesEngine.registerRule(underWtRule);

		// fire rules
		rulesEngine.fireRules();
		
		return underWtRule;
	}
}
