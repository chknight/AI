package mdp.util;

import mdp.component.Action;
import mdp.component.ActionGenerator;
import mdp.component.StateGenerator;
import problem.ProblemSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by ch_knight on 10/27/2016.
 */
public class UtilFunctions {

    public static void initializeMDPContext(ProblemSpec problemSpec) {
        MDPContext.MaxType = problemSpec.getStore().getMaxTypes();
        MDPContext.maxStore = problemSpec.getStore().getCapacity();
        MDPContext.prices = problemSpec.getPrices();
        MDPContext.allStates = new HashMap<>();
        MDPContext.cutoffPenalytPerItem = problemSpec.getPenaltyFee();
        MDPContext.discountFactor = problemSpec.getDiscountFactor();
        MDPContext.problemSpec = problemSpec;
        MDPContext.probabilities = problemSpec.getProbabilities();
        StateGenerator.generateAllState();
        Action.maxOrder = problemSpec.getStore().getMaxPurchase();
        Action.maxReturn = problemSpec.getStore().getMaxReturns();
        ActionGenerator.generateAllActions(Action.maxOrder, Action.maxReturn);
    }

    public static void generateAllPossibleList(List<Integer> currentList, int total, int max, Set<List<Integer>> possibleList) {
        if(total <= max) {
            possibleList.add(currentList);
            for(int i = 0; i < currentList.size(); ++i) {
                List<Integer> temp = new ArrayList<>(currentList);
                temp.set(i, currentList.get(i) + 1);
                if(!possibleList.contains(temp)) {
                    generateAllPossibleList(temp, total + 1, max, possibleList);
                }
            }
        }
    }
}
