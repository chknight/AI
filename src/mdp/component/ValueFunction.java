package mdp.component;

import java.util.ArrayList;

import mdp.util.MDPContext;

/**
 * The value function for the MDP solution
 * Created by ch_knight on 10/22/2016.
 */
public class ValueFunction {
	public static void setFirstIterationValue(){
		
		for(State state : MDPContext.stateList){
			state.value = Reward.calculateTotalReward(state);
		}
	}
	public static void getOneIterationValue(State state){
		for(Action action : ActionGenerator.getPossibleActions(state)){
			double immediateReward = Reward.calculateTotalReward(state, action);
			double futureReward = 0;
			
			State temp = action.generateNewState(state);
			
			for(State nextState : Transaction.getAllPossibleState(state, action)){
				
				double probability = Transaction.getTransactionValue(temp, nextState);
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
