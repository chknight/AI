package mdp.component;

import mdp.util.MDPContext;

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

    // the operation of an action
    public List<Integer> orderList;

    public List<Integer> returnList;


    // The penalty to cut off the order
    public double cutPenalty;


    public Action() {
        orderList = new ArrayList<>();
        returnList = new ArrayList<>();
        cutPenalty = 0;
    }

    public List<Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Integer> orderList) {
        this.orderList = orderList;
    }

    public List<Integer> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<Integer> returnList) {
        this.returnList = returnList;
    }

    public double getCutPenalty() {
        return cutPenalty;
    }

    public State generateNewState(State oldState) {
        State newState = new State(oldState);
        if(isValid(oldState.getItems())) {
            int totalAmount = 0;
            List<Integer> items = oldState.getItems();
            List<Integer> newItems = new ArrayList<>();
            for(int i = 0; i < MDPContext.MaxType; ++i) {
                newItems.add(items.get(i) + orderList.get(i) - returnList.get(i));
                if(newItems.get(i) < 0) {
                    return null;
                }
                totalAmount += newItems.get(i);
            }
            adjustItem(newItems, totalAmount);
            newState.setItems(newItems);
        } else {
            return null;
        }
        return newState;
    }

    // to verify whether the order is valid
    boolean isValid(List<Integer> items) {

        // the item to order in
        int numOfOrderItem = 0;
        //
        int numOfReturnItem = 0;

        // add all order operation together
        for(int i = 0; i < orderList.size(); ++i) {
            numOfOrderItem += orderList.get(i);
            numOfReturnItem += returnList.get(i);
        }

        return numOfOrderItem <= maxOrder && numOfReturnItem <= maxReturn;
    }

    public void adjustItem(List<Integer> items, int totalAmount) {
        cutPenalty = 0;
        int index = 0;
        while(totalAmount > MDPContext.maxStore) {
            int temp = items.get(index);
            if(temp > 0) {
                items.set(index, temp - 1);
                cutPenalty += MDPContext.cutoffPenalytPerItem;
                totalAmount--;
            } else {
                index++;
            }
        }
    }

    public List<Integer> generateOrderList() {
        List<Integer> newList = new ArrayList<>();
        for(int i = 0; i < orderList.size(); ++i) {
            if(returnList.get(i) > 0) {
                newList.add(-returnList.get(i));
            } else {
                newList.add(orderList.get(i));
            }
        }
        return newList;
    }

    @Override
    public String toString() {
        String result = "";
        result += "The order list is: ";
        for(int i = 0; i < orderList.size(); ++i) {
            result += orderList.get(i);
            result += " ";
        }
        result += "\nThe return list is: ";
        for(int j = 0; j < returnList.size(); ++j) {
            result += returnList.get(j);
            result += " ";
        }
        result += "\n";
        return result;
    }

}
