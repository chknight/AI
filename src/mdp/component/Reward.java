package mdp.component;

import mdp.MDP;
import mdp.util.MDPContext;
import problem.Matrix;

import java.util.List;

/**
 * The reward function for the MDP
 * Get the reword according to the transaction
 * Created by ch_knight on 10/18/2016.
 */
public class Reward {

    public Reward(State currentState, Action action) {
        currentState = currentState;
        State newState = action.generateNewState(currentState);
    }

    //calculate the reward according to the current state and action will be applied
    public double calculateTotalReward(State previousState, Action action) {
        State newState = action.generateNewState(previousState);
        List<Integer> items = newState.getItems();
        double result = 0;
        for(int i = 0;i < items.size(); ++i) {
            result += calculateRewardOfOneType(i, items.get(i));
            result += action.returnList.get(i) * MDPContext.prices.get(i);
        }
        result += newState.getCutPenalty();
        return result;
    }

    public double calculateRewardOfOneType(int index, int item) {
        double result = 0;
        Matrix probability = MDPContext.probabilities.get(index);
        double price = MDPContext.prices.get(index);
        for(int i = 0; i < item; ++i) {
            result += probability.get(item, i) * (0.75 * i *  - (item - i) * 0.25 * i);
        }
        return result;
    }

    // get the immediate reward from current state
//    public double immediateReward(State previousState, State currentState) {
//        double result = 0;
//        List<Integer> previousItems = previousState.getItems();
//        List<Integer> currentItems = currentState.getItems();
//        for(int i = 0; i < previousItems.size(); ++i) {
//            result += calculateImmediateRewardPerItem(previousItems.get(i), currentItems.get(i), i);
//        }
//        return result;
//    }
//
//    public double calculateImmediateRewardPerItem(int previousItem, int currentItem, int index) {
//        double price = MDPContext.prices.get(index);
//        double result = (currentItem - previousItem) * ()
//    }

}
