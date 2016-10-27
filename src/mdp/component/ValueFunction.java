package mdp.component;

import java.util.ArrayList;

import mdp.util.MDPContext;

/**
 * The value function for the MDP solution
 * Created by ch_knight on 10/22/2016.
 */
public class ValueFunction {
	
	//private ArrayList<State> states;

	
	public ValueFunction(){
		
	}
	
	public static void setFirstIterationValue(State currentState){
		StateGenerator.generateAllState();
		for(State state : MDPContext.stateList){
			double currentValue = Reward.calculateTotalReward(state);
			state.value = currentValue;
		}
	}
	
	public static void getValue(){
		for(State state : MDPContext.stateList){
			while(state.value - state.lastValue > 0.0000001){
				for(Action action : ActionGenerator.getPossibleActions(state)){
					double immediateReward = Reward.calculateTotalReward(state, action);
					double futureReward = 0;
					
					for(State nextState : Transaction.getAllPossibleState(state, action)){
						
						Transaction transaction = new Transaction(state, nextState, action);
						double probability = transaction.getTransactionValue();
						//System.out.println(nextState.value);
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
		
	}
	
	public static void getOneIterationValue(State state){
		for(Action action : ActionGenerator.getPossibleActions(state)){
			double immediateReward = Reward.calculateTotalReward(state, action);
			double futureReward = 0;
			
			for(State nextState : Transaction.getAllPossibleState(state, action)){
				Transaction transaction = new Transaction(state, nextState, action);
				double probability = transaction.getTransactionValue();
				System.out.println(nextState.value);
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
	
	/*public void getValueForAllStates(){
		for(State state : states){
			getValue(state);
		}
	}*/
}
