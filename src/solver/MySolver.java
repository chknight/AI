package solver;

import java.awt.datatransfer.SystemFlavorMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mdp.component.*;
import mdp.util.MDPContext;
import mdp.util.UtilFunctions;
import problem.Store;
import problem.Matrix;
import problem.ProblemSpec;

public class MySolver implements OrderingAgent {
	
	private ProblemSpec spec = new ProblemSpec();
	private Store store;
    private List<Matrix> probabilities;
	
	public MySolver(ProblemSpec spec) throws IOException {
	    this.spec = spec;
		store = spec.getStore();
        probabilities = spec.getProbabilities();
		UtilFunctions.initializeMDPContext(spec);
	}
	
	public void doOfflineComputation() {
		long startTime = System.currentTimeMillis();
		ValueFunction.setFirstIterationValue();
		for(State state : MDPContext.stateList) {
			MDPContext.allStates.put(state.getItems(), state);
		}
		while(!isAllSolved()) {
			for(State state : MDPContext.stateList){
				ValueFunction.getOneIterationValue(state);
			}
		}
		System.out.println(System.currentTimeMillis() - startTime);
	}

	public List<Integer> generateStockOrder(List<Integer> stockInventory,
											int numWeeksLeft) {
		System.out.println(MDPContext.allStates.get(stockInventory).toString());
		return MDPContext.allStates.get(stockInventory).bestAction.getOrderList();
	}

	public boolean isAllSolved() {
		for(State state : MDPContext.stateList) {
			if(state.value - state.lastValue > 1e-7) {
				return false;
			}
		}
		return true;
	}

}
