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
	public Action action;

	
	
	public Transaction(State oldState, State newState, Action action){
		this.newState = newState;
		this.oldState = oldState;
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
			for(int i = lastStock + orderItems - returnItems; i < capacity + 1; i++){
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
	public double getTransactionValue(){
		double result = 1;
		for(int i = 0; i < types; i++){
			int lastStock = oldState.getItems().get(i);
			int orderItems = action.getOrderList().get(i);
			int returnItems = action.getReturnList().get(i);
			int newStock = newState.getItems().get(i);
			double prob = getProbabilityForOneItem(lastStock, orderItems, returnItems, newStock, i);
			result *= prob;
		}	
		return result;
	}
}

