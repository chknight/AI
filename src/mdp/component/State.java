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

    // The totalAmount of the items
    public int totalAmount;

    // the number of the kind of items
    private  int typeOfItems;
    // the number of different items in current state
    private List<Integer> items;

    public int currentWeek;

    public State(int typeOfItems) {
        this.typeOfItems = typeOfItems;
        items = new ArrayList<>();
    }

    public State(int typeOfItems, List<Integer> initialState) {
        this.typeOfItems = typeOfItems;
        items = new ArrayList<>(initialState);
    }

    public State(State oldState) {
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
    }

    public void sumAllItem() {
        int sum = 0;
        for(Integer item : items) {
            sum += item;
        }
        totalAmount = sum;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }

    public void toNextWeek() {
        currentWeek++;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()) {
            return false;
        }
        State other = (State) obj;
        if(other.getItems().equals(items)) {
            return true;
        } else {
            return false;
        }
    }
}
