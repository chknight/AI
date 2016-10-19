package test;

import junit.framework.Assert;
import mdp.component.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test the state class
 * Created by ch_knight on 10/19/2016.
 */
public class TestState {
    @Test
    public void testState() {
        State state = new State(3);
        State.penaltyPerItem = 4;
        State.maxStore = 3;
        List<Integer> temp = new ArrayList<>(3);
        temp.add(5);
        temp.add(3);
        temp.add(2);
        int[] expectedValue = {0, 1, 2};
        state.setItems(temp);
        for(int i = 0; i < 3; ++i) {
            System.out.println(state.getItems().get(i));
            Assert.assertEquals(state.getItems().get(i) == expectedValue[i], true);
        }
        System.out.println(state.getCutPenalty());
    }
}
