package test;

import org.junit.Test;
import mdp.component.Action;
import mdp.component.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Test the action class
 * Created by ch_knight on 10/19/2016.
 */
public class TestAction {

    @Test
    public void testAction() {
        Action action= new Action();
        State state = new State(3);
        State.penaltyPerItem = 4;
        State.maxStore = 3;
        List<Integer> temp = new ArrayList<>(3);
        temp.add(1);
        temp.add(0);
        temp.add(0);
        int[] expectedValue = {0, 1, 2};
        state.setItems(temp);

    }
}
