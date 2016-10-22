package test;

import junit.framework.Assert;
import org.junit.Test;
import mdp.component.Action;
import mdp.component.State;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test the action class
 * Created by ch_knight on 10/19/2016.
 */
public class TestAction {

    // function to get legal action
    public static Action generateLegalAction() {
        Action action = new Action();
        List<Integer> orderList = new ArrayList<>();
        List<Integer> returnList = new ArrayList<>();
        orderList.add(1);
        orderList.add(0);
        orderList.add(1);
        returnList.add(1);
        returnList.add(1);
        returnList.add(0);
        action.setOrderList(orderList);
        action.setReturnList(returnList);
        return action;
    }

    // function to get action order exist the limit
    public static Action generateOverOrder() {
        Action action = generateLegalAction();
        List<Integer> orderList = new ArrayList<>();
        orderList.add(1);
        orderList.add(2);
        orderList.add(1);
        action.setOrderList(orderList);
        return action;
    }

    // get the action which return list
    public static Action generateOverReturn() {
        Action action = generateLegalAction();
        List<Integer> returnList = new ArrayList<>();
        returnList.add(1);
        returnList.add(1);
        returnList.add(2);
        action.setReturnList(returnList);
        return action;
    }

    @Test
    public void testAction() {
        Action.maxOrder = 3;
        Action.maxReturn = 3;
        testLegalAction();
        testActionOverReturn();
    }

    public void testLegalAction() {
        System.out.println("Begin to test legal action ---------");
        State state = TestState.generateLegalState();
        List<Integer> items = state.getItems();
        Action action = generateLegalAction();
        State newState = action.generateNewState(state);
        for(int i = 0; i < items.size(); ++i) {
            assertEquals(newState.getItems().get(i).intValue(),
                    items.get(i) - action.getReturnList().get(i) + action.getOrderList().get(i));
        }
        System.out.println("End test legal action -------------");
    }

    public void testActionOverReturn() {
        System.out.println("Begin to test action out of return ---------");
        State state = TestState.generateLegalState();
        Action action = TestAction.generateOverReturn();
        State newState = action.generateNewState(state);
        assertNull("The result should be none", newState);
        System.out.println("End to test action over return ---------");
    }

    public void testActionOverOrder() {
        System.out.println("Begin to test action out of order ---------");
        State state = TestState.generateLegalState();
        Action action = TestAction.generateOverOrder();
        State newState = action.generateNewState(state);
        assertNull("The result should be none", newState);
        System.out.println("End to test action over order---------");
    }
}
