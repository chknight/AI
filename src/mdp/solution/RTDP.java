package mdp.solution;

import mdp.component.Action;
import mdp.component.State;
import mdp.util.MDPContext;
import problem.Simulator;

import java.io.IOException;

/**
 * The RTDP method to find the solution of MDP
 * Created by ch_knight on 10/18/2016.
 */
public class RTDP  {

    // The simulator for the
    Simulator simulator;

    //the initialState of one time RTDP update
    State initialSate;

    public RTDP() throws IOException {
        simulator = new Simulator(MDPContext.problemSpec);

    }

    public Action getNextStep(State currentState, int maxWeek) {
        Action result = new Action();
        while(currentState.getCurrentWeek() < maxWeek) {
            Action nextActioon = greedyAction(currentState);

        }
        return result;
    }

    public Action greedyAction(State currentState) {
        Action action = new Action();
        return action;
    }

    public void updateValueFunction(State currentState, Action nextAction) {
        
    }


}
