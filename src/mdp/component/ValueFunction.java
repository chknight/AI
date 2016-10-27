package mdp.component;

import java.util.ArrayList;

import mdp.util.MDPContext;

/**
 * The value function for the MDP solution
 * Created by ch_knight on 10/22/2016.
 */
public class ValueFunction {
	
	private ArrayList<State> states;
	private static int maxPurchase;
	private static int maxReturn;
	
	public ValueFunction(ArrayList<State> states){
		this.states = states;
		for(State state : this.states){
			state.value = 0;
			state.lastValue = 0;
		}
		
	}
	
	public void setFirstIterationValue(State state){
		double currentValue = Integer.MIN_VALUE;
		
		for(Action action : ActionGenerator.allPossibleList){
			if(Reward.calculateTotalReward(state, action) > currentValue){
				currentValue = Reward.calculateTotalReward(state, action);
			}
		}
		state.value = currentValue;
	}
	
	public static void getValue(State state){
		
		while(state.value - state.lastValue > 0.0000001){
			Action bestAction;
			for(Action action : ActionGenerator.getPossibleActions(state)){
				double immediateReward = Reward.calculateTotalReward(state, action);
				double futureReward = 0;
				
				for(State nextState : Transaction.getAllPossibleState(state, action)){
					Transaction transaction = new Transaction(state, nextState, action);
					double probability = transaction.getTransactionValue();
					futureReward += probability * nextState.value;
				}
				double newValue = immediateReward + MDPContext.discountFactor * futureReward;
				if(newValue > state.value){
					state.lastValue = state.value;
					state.value = newValue;
					state.bestAction = action;
				}	
			}
		}
	}
	
	public void getValueForAllStates(){
		for(State state : states){
			getValue(state);
		}
	}
}
