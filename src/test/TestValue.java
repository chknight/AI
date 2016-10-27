package test;

import java.util.ArrayList;

import org.junit.Test;

import mdp.component.Action;
import mdp.component.ActionGenerator;
import mdp.component.State;
import mdp.component.ValueFunction;
import mdp.util.MDPContext;
import problem.Matrix;

public class TestValue {
	
	private ArrayList<Integer> initialStock;
	private int maxPurchase = 1;
	private int maxReturn = 0;
	
	
	@Test
	 public void testValue() {
		initialStock = new ArrayList<Integer>();
		initialStock.add(1);
		MDPContext.MaxType = 1;
		MDPContext.maxStore = 1;
        Action.maxReturn = 1;
        Action.maxOrder = 1;
        double[][] possabilities1 = {{0.3, 0.4, 0.3}, {0.4, 0.4, 0.2}, {0.4, 0.4, 0.2}};
        MDPContext.probabilities = new ArrayList<Matrix>();
        MDPContext.probabilities.add(new Matrix(possabilities1));
        MDPContext.cutoffPenalytPerItem = 2;
        MDPContext.prices = new ArrayList<>(1);
        MDPContext.prices.add(1.0);
        MDPContext.discountFactor = 0.9;

        
		
		State state = new State(initialStock);
		System.out.println(state.value);
		
		ActionGenerator.generateAllActions(maxPurchase, maxReturn);
		
		ValueFunction.setFirstIterationValue(state);
		System.out.println(state.value);
		//ValueFunction.getOneIterationValue(state);
		//System.out.println(state.value);
		
	}

}
