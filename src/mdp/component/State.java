package mdp.component;

import mdp.MDP;
import mdp.util.MDPContext;

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

    private boolean isSolved = false;

    public double value;
    public double lastValue;
    public Action bestAction;
    public ArrayList<Action> allActions;
    public ArrayList<Integer> nextState;

    public State(int typeOfItems) {
        this.typeOfItems = typeOfItems;
        items = new ArrayList<>();
        value = Double.MIN_VALUE;
        lastValue = Double.MIN_VALUE;
    }

    public State(List<Integer> initialState) {
        this(initialState.size());
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

    public boolean isValid() {
        sumAllItem();
        return totalAmount <= MDPContext.maxStore;
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


    @Override
    public String toString() {
        String result = "The order is: ";
        for(Integer item : items) {
            result += item + " ";
        }
        return result;
    }

    public void initialize2DArray(){
    	nextState = new ArrayList<Integer>();
    }
    
    public void generatePossibleStatesList(int itemIndex){  	
    	if(itemIndex < typeOfItems + 1){
    		for(int i = 0; i < maxStore + 1; i++){
    			nextState.add(i);
    			nextState.add(itemIndex - 1);
    			generatePossibleStatesList(itemIndex + 1);
    		}
    	}
    }
    
    public ArrayList<State> generateAllStates(){
    	ArrayList<State> states = new ArrayList<State>();
    	return states;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
