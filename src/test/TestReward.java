//package test;
//
//import mdp.MDP;
//import mdp.component.Action;
//import mdp.component.Reward;
//import mdp.component.State;
//import mdp.util.MDPContext;
//import org.junit.Test;
//import problem.Matrix;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import static org.junit.Assert.assertEquals;
//
///**
// * The junit test to test the reward function
// * Created by ch_knight on 10/25/2016.
// */
//public class TestReward {
//
//    public Action generateActionWithoutReturn() {
//        Action action = new Action();
//        List<Integer> orderList = new ArrayList<>(2);
//        List<Integer> returnList = new ArrayList<>(2);
//        orderList.add(1);
//        orderList.add(1);
//        returnList.add(0);
//        returnList.add(0);
//        action.setOrderList(orderList);
//        action.setReturnList(returnList);
//        return action;
//    }
//
//    public Action generateActionWithReturn() {
//        Action action = generateActionWithoutReturn();
//        List<Integer> returnList = new ArrayList<>(2);
//        action.getOrderList().set(0, 2);
//        returnList.add(1);
//        returnList.add(0);
//        action.setReturnList(returnList);
//        return action;
//    }
//
////    @Test
//    public void testRewardFunction() {
//        MDPContext.maxStore = 2;
//        MDPContext.MaxType = 2;
//        Action.maxReturn = 2;
//        Action.maxOrder = 3;
//        MDPContext.cutoffPenalytPerItem = 2;
//        MDPContext.prices = new ArrayList<>(2);
//        MDPContext.prices.add(1.0);
//        MDPContext.prices.add(2.0);
//        double[][] possabilities1 = {{0.3, 0.4, 0.3}, {0.4, 0.4, 0.2}, {0.4, 0.4, 0.2}};
//        double[][] possabilities2 = {{0.4, 0.2, 0.4}, {0.2, 0.6, 0.2}, {0.5, 0.5, 0.0}};
//        MDPContext.probabilities = new ArrayList<>();
//        MDPContext.probabilities.add(new Matrix(possabilities1));
//        MDPContext.probabilities.add(new Matrix(possabilities2));
//        testWithoutCutOff();
//        testWithReturn();
//        testWithCutOff();
//    }
//
//    public void testWithoutCutOff() {
//        State state = TestState.generate0State();
//        Action action = generateActionWithoutReturn();
//        double expected = 1.0 * 0.4 * 0.75 * 0 + 1.0 * 0.4 * 0.75 * 1 + 1.0 * 0.2 * (0.75 - 0.25);
//        expected += 2.0 * 0.2 * 0.75 * 0 + 2.0 * 0.6 * 0.75 * 1 + 2.0 * 0.2 * (0.75 - 0.25);
//        double result = Reward.calculateTotalReward(state, action);
////        assertEquals(expected, result, 0.0001);
//
//    }
//
//    public void testWithCutOff() {
//        State state = TestState.generate1State();
//        Action action = generateActionWithoutReturn();
//        double expected = 1.0 * 0.4 * 0.75 * 0 + 1.0 * 0.4 * 0.75 * 1 + 1.0 * 0.2 * (0.75 - 0.25);
//        expected += 2.0 * 0.2 * 0.75 * 0 + 2.0 * 0.6 * 0.75 * 1 + 2.0 * 0.2 * (0.75 - 0.25);
//        expected -= MDPContext.cutoffPenalytPerItem * 1;
//        double result = Reward.calculateTotalReward(state, action);
////        assertEquals(expected, result, 0.0001);
//    }
//
//    public void testWithReturn() {
//        State state = TestState.generate0State();
//        Action action = generateActionWithReturn();
//        double expected = 1.0 * 0.4 * 0.75 * 0 + 1.0 * 0.4 * 0.75 * 1 + 1.0 * 0.2 * (0.75 - 0.25);
//        expected += 2.0 * 0.2 * 0.75 * 0 + 2.0 * 0.6 * 0.75 * 1 + 2.0 * 0.2 * (0.75 - 0.25);
//        expected -= 1.0 * 0.5 * 1;
//        double result = Reward.calculateTotalReward(state, action);
////        assertEquals(expected, result, 0.0001);
//    }
//}
