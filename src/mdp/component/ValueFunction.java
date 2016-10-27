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
	
	public static void setFirstIterationValue(){
		
		for(State state : MDPContext.stateList){
			//System.out.println("states:" + state.toString());
			double currentValue = Reward.calculateTotalReward(state);
			state.value = currentValue;
		}
	}
	public static void getOneIterationValue(State state){
		for(Action action : ActionGenerator.getPossibleActions(state)){
			double immediateReward = Reward.calculateTotalReward(state, action);
			double futureReward = 0;
			
			State temp = action.generateNewState(state);
			
			for(State nextState : Transaction.getAllPossibleState(state, action)){
				
				double probability = Transaction.getTransactionValue(temp, nextState);
				//System.out.println("next state prob:" + probability);
				//System.out.println("next state value:" + nextState.value);
				futureReward += probability * nextState.value;
			}
			double newValue = immediateReward + MDPContext.discountFactor * futureReward;
			//System.out.println("immi:" + immediateReward);
			//System.out.println("future:" + MDPContext.discountFactor * futureReward);
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
