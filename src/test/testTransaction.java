//package test;
//
//import mdp.component.Action;
//import mdp.component.State;
//import mdp.component.Transaction;
//import mdp.util.MDPContext;
//import problem.Matrix;
//
////import org.junit.Test;
//
////import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class testTransaction {
//
//	private ArrayList<Integer> initialStock = new ArrayList<Integer>();
//	private ArrayList<Integer> newStock = new ArrayList<Integer>();
//	private ArrayList<Integer> orderList = new ArrayList<Integer>();
//	private ArrayList<Integer> returnList = new ArrayList<Integer>();
//
//
////	 @Test
//	 public void testTransaction() {
//		 MDPContext.probabilities = new ArrayList<Matrix>();
//		 double[][] coke = new double[5][5];
//		 for(int i = 0; i < 5; i++){
//			 coke[i][0] = 0.3;
//			 coke[i][1] = 0.2;
//			 coke[i][2] = 0.2;
//			 coke[i][3] = 0.1;
//			 coke[i][4] = 0.2;
//		 }
//		 double[][] sprite = new double[5][5];
//		 for(int i = 0; i < 5; i++){
//			 sprite[0][i] = 0.2;
//		 }
//		 for(int i = 1; i < 5; i++){
//			 sprite[i][0] = 0.2;
//			 sprite[i][1] = 0.2;
//			 sprite[i][2] = 0.2;
//			 sprite[i][3] = 0.1;
//			 sprite[i][4] = 0.3;
//		 }
//		 Matrix cokeMatrix = new Matrix(coke);
//		 Matrix spriteMatrix = new Matrix(sprite);
//		 MDPContext.probabilities.add(cokeMatrix);
//		 MDPContext.probabilities.add(spriteMatrix);
//
//		 MDPContext.MaxType = 2;
//		 MDPContext.maxStore = 4;
//		 Action.maxOrder = 2;
//		 Action.maxReturn = 1;
//		 generateInitialState();
//		 generateNewState();
//		 generateOrderList();
//		 generateReturnList();
//		 State currentState = new State(initialStock);
//		 State newState = new State(newStock);
//		 Action action = new Action();
//		 action.setOrderList(orderList);
//		 action.setReturnList(returnList);
//
//
//		 State temp = action.generateNewState(currentState);
//
//
//
//
//		 //transaction.probabilities = probabilities;
//		 //System.out.println(transaction.getProbabilityForOneItem(3, 0, 0, 3, 0));
////		 assertTrue(Transaction.getTransactionValue(temp, newState) == 0.24);
//	 }
//
////	@Test
//	public void testAllPossibleFunctions() {
//		TesterInitialize.initiizeWith2();
//		State state = new State(2);
//		List<Integer> items = new ArrayList<>(2);
//		items.add(0);
//		items.add(0);
//		state.setItems(items);
//		Action action = new Action();
//		List<Integer> temp = new ArrayList<>(2);
//		temp.add(1);
//		temp.add(1);
//		action.setReturnList(items);
//		action.setOrderList(temp);
//		List<State> allStates =  Transaction.getAllPossibleState(state, action);
//		for(State current : allStates) {
//			System.out.println(current.toString());
//		}
//
//        Map<State, Double> allProbablities;
//        System.out.println("Begin to test probabilities ---------------------");
//        allProbablities = Transaction.getAllProbabilities(state, action);
//        double[] expectedValue = new double[4];
//        expectedValue[0] = 0.4 * 0.6 + 0.2 * 0.2 + 0.2 * 0.6 + 0.4 * 0.2;
//        expectedValue[1] = 0.2 * 0.4 + 0.2 * 0.2;
//        expectedValue[2] = 0.4 * 0.6 + 0.4 * 0.2;
//        expectedValue[3] = 0.4 * 0.2;
//        int index = 0;
//        for(State current : allStates) {
//            System.out.println(current.toString());
//            System.out.println(allProbablities.get(current));
////            assertEquals(expectedValue[index],allProbablities.get(current), 0.00001 );
//            index++;
//            System.out.println("---------------------------------------------");
//        }
//	}
//
//
//	public void generateInitialState(){
//		initialStock.add(3);
//		initialStock.add(0);
//	}
//
//	public void generateNewState(){
//		newStock.add(3);
//		newStock.add(0);
//	}
//
//	public void generateOrderList(){
//		orderList.add(0);
//		orderList.add(1);
//	}
//
//	public void generateReturnList(){
//		returnList.add(0);
//		returnList.add(0);
//	}
//
//}
