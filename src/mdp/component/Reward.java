package mdp.component;

import mdp.util.MDPContext;
import problem.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * The reward function for the MDP
 * Get the reword according to the transaction
 * Created by ch_knight on 10/18/2016.
 */
public class Reward {

    public static Action zeroAction = null;

    public static double calculateTotalReward(State initialState) {
        if(zeroAction == null) {
            zeroAction = new Action();
            List<Integer> orderList = new ArrayList<>();
            List<Integer> returnList = new ArrayList<>();
            for(int i = 0; i < MDPContext.MaxType; ++i) {
                orderList.add(0);
                returnList.add(0);
            }
            zeroAction.setOrderList(orderList);
            zeroAction.setReturnList(returnList);
        }
        return calculateTotalReward(initialState, zeroAction);
    }

    //calculate the reward according to the current state and action will be applied
    public static double calculateTotalReward(State previousState, Action action) {
        State newState = action.generateNewState(previousState);
        List<Integer> items = newState.getItems();
        double result = 0;
        for(int i = 0;i < items.size(); ++i) {
            result += calculateRewardOfOneType(i, items.get(i));
            result -= action.returnList.get(i) * MDPContext.prices.get(i) * 0.5;
        }
        result -= action.getCutPenalty();
        return result;
    }

    public static double calculateRewardOfOneType(int index, int item) {
        double result = 0;
        Matrix probabilities = MDPContext.probabilities.get(index);
        double price = MDPContext.prices.get(index);
        for(int i = 0; i <= MDPContext.maxStore; ++i) {
            double probability = probabilities.get(item, i);
            // all the consumers buy the things they want
            if(i <= item) {
                result += probability * 0.75 * price * i;
            } else {
                result += probability * (0.75 * item * price  - (i - item) * 0.25 * price);
            }
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
