package mdp.component;

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
    public double calculateTotalReward(State currentState, Action action) {
        State newState = action.generateNewState(currentState);
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

}
