package mdp.component;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for MDP
 * The state is the item status at the begin of each week
 * Created by ch_knight on 10/18/2016.
 */
public class State {


    //max number of item could store each week
    public static int maxStore;
    //the penalty for per item
    public static double penaltyPerItem;

    // The totalAmount of the items
    public int totalAmount;

    // the number of the kind of items
    private  int typeOfItems;
    // the number of different items in current state
    private List<Integer> items;
    // The penalty to cu
    public double cutPenalty;

    public State(int typeOfItems) {
        this.typeOfItems = typeOfItems;
        items = new ArrayList<>();
    }

    public State(int typeOfItems, List<Integer> initialState) {
        this.typeOfItems = typeOfItems;
        items = new ArrayList<>(initialState);
    }

    public State(State oldState) {
        this.totalAmount = oldState.getTotalAmount();
        this.typeOfItems = oldState.getTypeOfItems();
        items = new ArrayList<>(oldState.getItems());
    }

    public int getTypeOfItems() {
        return typeOfItems;
    }

    public void setTypeOfItems(int typeOfItems) {
        this.typeOfItems = typeOfItems;
    }

    public List<Integer> getItems() {
        return items;
    }

    public void setItems(List<Integer> items) {
        this.items = items;
        sumAllItem();
        stockAdapter();
    }

    public double getCutPenalty() {
        return cutPenalty;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    // the function to adjust the amount of the stock
    public void stockAdapter() {
        int index = 0;
        cutPenalty = 0;
        while(totalAmount > maxStore) {
            int temp = items.get(index);
            if(temp > 0) {
                items.set(index, temp - 1);
                cutPenalty += penaltyPerItem;
                totalAmount--;
            } else {
                index++;
            }
        }
    }

    public void sumAllItem() {
        int sum = 0;
        for(Integer item : items) {
            sum += item;
        }
        totalAmount = sum;
    }
}
