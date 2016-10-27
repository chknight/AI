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
	
	private static State oldState;
	//public Action action;
	private static State newState;
	//public static List<Matrix> probabilities;
	


	//get the probabilities of one single item
	public static double getProbabilityForOneItem(int lastStock, int newStock, int itemIndex){
		
		if(newStock > lastStock){
			return 0;
		}
		if(newStock > 0 && newStock <= lastStock){
			//System.out.println("itemIndex:" + itemIndex);
			//System.out.println("second:" + (lastStock + orderItems - returnItems));
			//System.out.println("lastStock:" + lastStock);
			//System.out.println("orderItem:" + orderItems);
			//System.out.println("third:" + (lastStock + orderItems - returnItems - newStock));
			return MDPContext.probabilities.get(itemIndex).get(lastStock, lastStock - newStock);
		}
		if(newStock == 0){
			double result = 0;
			for(int i = lastStock; i < MDPContext.maxStore + 1; i++){
				
				result += MDPContext.probabilities.get(itemIndex).get(lastStock, i);
			}
			return result;
		}
		return 0;
	}
	
	//get the transaction probabilities between two states
	public static double getTransactionValue(State currentState, State newState){
		double result = 1;
		for(int i = 0; i < MDPContext.MaxType; i++){
			int lastStock = currentState.getItems().get(i);
			//int orderItems = action.getOrderList().get(i);
			//int returnItems = action.getReturnList().get(i);
			int newStock = newState.getItems().get(i);
			double prob = getProbabilityForOneItem(lastStock, newStock, i);
			
			result *= prob;
		}	
		return result;
	}

	

	public static Map<State, Double> getAllProbabilities(State currentState, Action action) {


		Map<State, Double> probabilities = new HashMap<>();

		List<State> possibleState = getAllPossibleState(currentState, action);

		for(State state : possibleState) {
			double probability = getTransactionValue(currentState, state);
			probabilities.put(state, probability);
		}

		return probabilities;
	}

	public static Map<State, Double> getAllProbabilities(State currentState, List<State> possibleState, Action action) {

		Map<State, Double> probabilities = new HashMap<>();

		for(State state : possibleState) {
			double probability = getTransactionValue(currentState, state);
			probabilities.put(state, probability);
		}

		return probabilities;
	}

	public static List<List<Integer>> getAllPossibleItemList(State currentState, Action action) {
		int[] indexes = new int[MDPContext.MaxType];
		int[] arrayLength = new int[MDPContext.MaxType];
		State newState = action.generateNewState(currentState);
		List<Integer> currentItems = newState.getItems();

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

