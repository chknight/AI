package test;

import mdp.component.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        List<Integer> temp = new ArrayList<>();
        temp.add(5);
        temp.add(3);
        temp.add(2);
        int[] expectedValue = {0, 1, 2};
        state.setItems(temp);
        for(int i = 0; i < 3; ++i) {
            System.out.println(state.getItems().get(i));
            assertEquals(state.getItems().get(i) == expectedValue[i], true);
        }
        double expectedCutoff = 28.0;
        assertEquals(state.getCutPenalty(), expectedCutoff, 0.0001);
        testLegalState();

    }

    private void testLegalState() {
       State state = generateLegalState();
        int[] expectedValue = {1, 2, 0};
        for(int i = 0; i < 3; ++i) {
            assertEquals(state.getItems().get(i) == expectedValue[i], true);
        }
        assertEquals(state.getCutPenalty(), 0.0, 0.00001);
    }

    public static State generateLegalState() {
        State.penaltyPerItem = 4;
        State state = new State(3);
        List<Integer> temp = new ArrayList<>(3);
        temp.add(1);
        temp.add(2);
        temp.add(0);
        state.setItems(temp);
        return state;
    }
}
