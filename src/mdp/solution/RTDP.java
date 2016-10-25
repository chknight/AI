package mdp.solution;

import mdp.component.Action;
import mdp.component.State;
import mdp.util.MDPContext;
import problem.Simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The RTDP method to find the solution of MDP
 * Created by ch_knight on 10/18/2016.
 */
public class RTDP  {

    // The simulator for the
    Simulator simulator;

    //the initialState of one time RTDP update
    State initialSate;

    List<State> solveState;

    public RTDP() throws IOException {
        simulator = new Simulator(MDPContext.problemSpec);
        solveState = new ArrayList<>();
    }

    public Action getNextStep(State currentState, int maxWeek, double factor) {
        Stack<State> visited = new Stack<>();
        Action result = new Action();
        while(isSolved(currentState)) {
            visited.push(currentState);
            Action nextAction = greedyAction(currentState);
            updateValueFunction(currentState, nextAction);
            currentState = updateState(currentState, nextAction);
        }
        while (!visited.isEmpty()) {
            State state = visited.pop();
            checkSolved(currentState, factor);
        }
        return result;
    }

//    public Action getNextStepWithouLabel(State currentState, int maxWeek, double factor) {
//
//    }

    public Action greedyAction(State currentState) {
        Action action = new Action();
        return action;
    }

    public void updateValueFunction(State currentState, Action nextAction) {
        
    }

    public State updateState(State currentState, Action action) {
        State nextState = action.generateNewState(currentState);
        nextState.toNextWeek();
        return nextState;
    }

    public boolean checkSolved(State currentState, double factor) {
        boolean resolved = true;
        Stack<State> open = new Stack<>();
        Stack<State> close = new Stack<>();
        if(isSolved(currentState)) {
            return true;
        }
        open.push(currentState);
        while(!open.empty()) {
            State state = open.pop();
            close.push(state);
            if(residual(state, factor)) {
                resolved = false;
            } else {

            }
        }
        return resolved;
    }

    public boolean isSolved(State state) {
        if(solveState.contains(state)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean residual(State state, double factor) {

        return true;
    }
}
