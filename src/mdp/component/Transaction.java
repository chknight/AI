package mdp.component;

import java.util.List;

import problem.Matrix;

/**
 * The transaction function for MDP
 * The
 * Created by ch_knight on 10/18/2016.
 */
public class Transaction {
	
	private State oldState;
	private State newState;
	public static int types;
	public static int capacity;
	public static List<Matrix> probabilities;
	public static List<Integer> orderList;
    public static List<Integer> returnList;
	
	
	public Transaction(State oldState, State newState){
		this.newState = newState;
		this.oldState = oldState;
		types = oldState.getTypeOfItems();
	}
	
	//get the probabilities of one single item
	public double getProbabilityForOneItem(int lastStock, int orderItems, int returnItems, int newStock, int itemIndex){
		if(newStock > lastStock + orderItems - returnItems){
			return 0;
		}
		if(newStock > 0 && newStock < lastStock + orderItems - returnItems){
			return probabilities.get(itemIndex).get(lastStock + orderItems - returnItems, lastStock + orderItems - returnItems - newStock);
		}
		if(newStock == 0){
			int result = 0;
			for(int i = lastStock + orderItems - returnItems; i < capacity; i++){
				result += probabilities.get(itemIndex).get(lastStock + orderItems - returnItems, i);
			}
			return result;
		}
		return 0;
	}
	
	//get the transaction probabilities between two states
	public double getTransactionValue(){
		double result = 1;
		for(int i = 0; i < types; i++){
			int lastStock = oldState.getItems().get(i);
			int orderItems = orderList.get(i);
			int returnItems = returnList.get(i);
			int newStock = newState.getItems().get(i);
			double prob = getProbabilityForOneItem(lastStock, orderItems, returnItems, newStock, i);
			result *= prob;
		}	
		return result;
	}
}

