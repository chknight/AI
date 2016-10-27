package mdp.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mdp.MDP;
import mdp.util.MDPContext;
import problem.Matrix;

/**
 * The transaction function for MDP
 * The
 * Created by ch_knight on 10/18/2016.
 */
public class Transaction {
	
	private State oldState;
	public Action action;
	private State newState;
	public static List<Matrix> probabilities;
	

	
	
	public Transaction(State oldState, Action action){
		this.oldState = oldState;
		this.action = action;
	}

	public Transaction(State oldState, State newState, Action action){
		this.oldState = oldState;
		this.newState = newState;
		this.action = action;
	}

	//get the probabilities of one single item
	public double getProbabilityForOneItem(int lastStock, int orderItems, int returnItems, int newStock, int itemIndex){
		
		if(newStock > lastStock + orderItems - returnItems){
			return 0;
		}
		if(newStock > 0 && newStock <= lastStock + orderItems - returnItems){
			return probabilities.get(itemIndex).get(lastStock + orderItems - returnItems, lastStock + orderItems - returnItems - newStock);
		}
		if(newStock == 0){
			double result = 0;
			for(int i = lastStock + orderItems - returnItems; i < MDPContext.maxStore + 1; i++){
				result += probabilities.get(itemIndex).get(lastStock + orderItems - returnItems, i);
			}
			return result;
		}
		return 0;
	}
	
	//get the transaction probabilities between two states
	public double getTransactionValue(State newState){
		double result = 1;
		for(int i = 0; i < MDPContext.MaxType; i++){
			int lastStock = oldState.getItems().get(i);
			int orderItems = action.getOrderList().get(i);
			int returnItems = action.getReturnList().get(i);
			int newStock = newState.getItems().get(i);
			double prob = getProbabilityForOneItem(lastStock, orderItems, returnItems, newStock, i);
			result *= prob;
		}	
		return result;
	}

	public double getTransactionValue(){
		return getTransactionValue(this.newState);
	}

	public static Map<State, Double> getAllProbabilities(State currentState, Action action) {

		Transaction transaction = new Transaction(currentState, action);


		Map<State, Double> probabilities = new HashMap<>();

		List<State> possibleState = getAllPossibleState(currentState, action);

		for(State state : possibleState) {
			double probability = transaction.getTransactionValue(state);
			probabilities.put(state, probability);
		}

		return probabilities;
	}

	public static Map<State, Double> getAllProbabilities(State currentState, List<State> possibleState, Action action) {

		Transaction transaction = new Transaction(currentState, action);

		Map<State, Double> probabilities = new HashMap<>();

		for(State state : possibleState) {
			double probability = transaction.getTransactionValue(state);
			probabilities.put(state, probability);
		}

		return probabilities;
	}

	public static List<List<Integer>> getAllPossibleItemList(State currentState, Action action) {
		int[] indexes = new int[MDPContext.MaxType];
		int[] arrayLength = new int[MDPContext.MaxType];
		List<Integer> currentItems = currentState.getItems();
		for(int i = 0; i < indexes.length; ++i) {
			indexes[i] = 0;
			arrayLength[i] = currentItems.get(i);
		}
		List<List<Integer>> possibleState = new ArrayList<>();
		generateAllPossibleState(indexes, arrayLength, 0, possibleState);
		return possibleState;
	}

	public static List<State> getAllPossibleState(State currentState, Action action) {
		List<List<Integer>> allPossibleList = getAllPossibleItemList(currentState, action);
		List<State> allPossibleStates = new ArrayList<>();
		for (List<Integer> possibleList : allPossibleList) {
			State temp = MDPContext.allStates.get(possibleList);
			if(temp == null) {
				temp = new State(possibleList);
				MDPContext.allStates.put(possibleList, temp);
			}
			allPossibleStates.add(temp);
		}
		return allPossibleStates;
	}

	public static void generateAllPossibleState(int[] currentIndex, int [] length, int index, List<List<Integer>> possibleState) {
		// end of recursive, add the state to the list
		if(index >= currentIndex.length) {
			List<Integer> items = new ArrayList<>(currentIndex.length);
			for(int i = 0; i < currentIndex.length; ++i) {
				items.add(currentIndex[i]);
			}
			possibleState.add(items);
		} else {
			for(int i = 0; i <= length[index]; ++i) {
				int[] newArray = currentIndex.clone();
				newArray[index] = i;
				generateAllPossibleState(newArray, length, index+1, possibleState);
			}
		}
	}

}

