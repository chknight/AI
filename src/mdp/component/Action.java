package mdp.component;

import java.util.ArrayList;
import java.util.List;

/**
 * The action of the agent for MDP
 * The action including buy/return items
 * Created by ch_knight on 10/18/2016.
 */
public class Action {

    // max number of item to order each week
    public static int maxOrder;

    // max number of item could return each week
    public static int maxReturn;

    //max number of item could store each week
    public static int maxStore;

    // the operation of an action
    public List<Integer> operations;

    public State generateNewState(State oldState) {
        State newState = new State(oldState);
        if(isValid(oldState.getItems())) {
            List<Integer> items = oldState.getItems();
            List<Integer> newItems = new ArrayList<>();
            for(int i = 0; i < newState.getTypeOfItems(); ++i) {
                newItems.add(items.get(i) + operations.get(i));
            }
            newState.setItems(newItems);
        }
        return newState;
    }

    // to verify whether the order is valid
    boolean isValid(List<Integer> items) {

        // the item to order in
        int numOfOrderItem = 0;
        //
        int numOfReturnItem = 0;
        // whether the order is valid
        boolean valid = true;

        // add all order operation together
        for(Integer operation : operations) {
            if (operation > 0) {
                numOfOrderItem += operation;
            }
        }

        for(Integer operation : operations) {
            if(operation < 0) {
                numOfReturnItem -= operation;
            }
        }

        return numOfOrderItem < maxOrder && numOfReturnItem < maxReturn;
    }

}
