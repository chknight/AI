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
		//types = oldState.getTypeOfItems();
	}

	public Transaction(State oldState, State newState, Action action){
		this.oldState = oldState;
		this.newState = newState;
		this.action = action;
		//types = oldState.getTypeOfItems();
	}

	//get the probabilities of one single item
	public double getProbabilityForOneItem(int lastStock, int orderItems, int returnItems, int newStock, int itemIndex){
		/*for(Matrix m : probabilities){
			System.out.println(m.get(0, 0));
		}*/
		
		if(newStock > lastStock + orderItems - returnItems){
			return 0;
		}
		if(newStock > 0 && newStock <= lastStock + orderItems - returnItems){
			//System.out.println(lastStock + orderItems - returnItems);
			//System.out.println(lastStock + orderItems - returnItems - newStock);
			return probabilities.get(itemIndex).get(lastStock + orderItems - returnItems, lastStock + orderItems - returnItems - newStock);
		}
		if(newStock == 0){
			double result = 0;
			for(int i = lastStock + orderItems - returnItems; i < MDPContext.maxStore + 1; i++){
				//result += 0.5;
				result += probabilities.get(itemIndex).get(lastStock + orderItems - returnItems, i);
				//System.out.println(probabilities.get(itemIndex).get(lastStock + orderItems - returnItems, i));
				//System.out.println("result:" + result);
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

	public static Map<State, Double> getAllProabilities(State currentState, Action action) {

		Transaction transaction = new Transaction(currentState, action);


		Map<State, Double> probabilities = new HashMap<>();

		List<State> possibleState = getAllPossibleState(currentState, action);

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

		for(int i = 0; i < currentItems.size(); ++i) {
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
		}
		return allPossibleStates;
	}

	public static void generateAllPossibleState(int[] currentIndex, int [] length, int index, List<List<Integer>> possibleState) {
		// end of recursive, add the state to the list
		if(index >= currentIndex.length) {
			List<Integer> items = new ArrayList<>(currentIndex.length);
			for(int i = 0; i < currentIndex[i]; ++i) {
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

