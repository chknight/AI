package test;

import mdp.component.State;
import mdp.util.MDPContext;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test the state class
 * Created by ch_knight on 10/19/2016.
 */
public class TestState {
    @Test
    public void testState() {
        State state = new State(3);
        State.maxStore = 3;
        MDPContext.maxStore = 3;
        testLegalState();
        testIllegalState();
    }


    private void testLegalState() {
       State state = generateLegalState();
        int[] expectedValue = {1, 2, 0};
        for(int i = 0; i < 3; ++i) {
            assertEquals(state.getItems().get(i) == expectedValue[i], true);
        }
        assertTrue(state.isValid());
    }

    public void testIllegalState() {
        State state = generateilLegalState();
        assertFalse(state.isValid());
    }

    public static State generateLegalState() {
        State state = new State(3);
        List<Integer> temp = new ArrayList<>(3);
        temp.add(1);
        temp.add(2);
        temp.add(0);
        state.setItems(temp);
        return state;
    }

    public static State generateilLegalState() {
        State state = new State(3);
        List<Integer> temp = new ArrayList<>(3);
        temp.add(1);
        temp.add(2);
        temp.add(1);
        state.setItems(temp);
        return state;
    }

    public static State generate0State() {
        State state = new State(2);
        List<Integer> items = new ArrayList<>(2);
        items.add(0);
        items.add(0);
        state.setItems(items);
        return state;
    }

    public static State generate1State() {
        State state = new State(2);
        List<Integer> items = new ArrayList<>(2);
        items.add(1);
        items.add(0);
        state.setItems(items);
        return state;
    }
}
