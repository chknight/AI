package mdp.solution;

import mdp.component.Action;
import mdp.component.State;
import mdp.component.Transaction;
import mdp.component.ValueFunction;
import mdp.util.MDPContext;
import problem.Simulator;
import solver.OrderingAgent;

import java.io.IOException;
import java.util.*;

/**
 * The LRTDP method to find the solution of MDP
 * Created by ch_knight on 10/18/2016.
 */
public class LRTDP implements OrderingAgent {

    // The simulator for the
    Simulator simulator;

    //the initialState of one time LRTDP update
    State initialSate;

    List<State> solveState;

    @Override
    public void doOfflineComputation() {

    }

    @Override
    public List<Integer> generateStockOrder(List<Integer> inventory, int numWeeksLeft) {
        return null;
    }

    public LRTDP() throws IOException {
        simulator = new Simulator(MDPContext.problemSpec);
        solveState = new ArrayList<>();
    }

    public Action getNextStep(State currentState, int maxWeek, double factor) {
        Stack<State> visited = new Stack<>();
        Action result = new Action();
        long startTime = System.currentTimeMillis();
        long currentTime;
        while(currentState.isSolved()) {
            visited.push(currentState);
            Action nextAction = greedyAction(currentState);
        }
        while (!visited.isEmpty()) {
            State state = visited.pop();
            checkSolved(currentState, factor);
        }
        return result;
    }

    // apply greedy method to get next action to apply
    public Action greedyAction(State currentState) {
        ValueFunction.getValue(currentState);
        return currentState.bestAction;
    }

    public boolean checkSolved(State currentState, double factor) {
        boolean resolved = true;
        Stack<State> open = new Stack<>();
        Stack<State> close = new Stack<>();
        if(currentState.isSolved()) {
            return true;
        }
        open.push(currentState);
        while(!open.empty()) {
            State state = open.pop();
            close.push(state);
            if(!residual(state, factor)) {
                resolved = false;
            } else {
                Action action = greedyAction(currentState);
                Map<State, Double> possibleStates = Transaction.getAllProabilities(state, action);
                Set<State> allKeys = possibleStates.keySet();
                State[] states = (State[])allKeys.toArray();
                for(State temp : states) {
                    Double probability = possibleStates.get(state);
                    if(probability > 0) {
                        if(!temp.isSolved() && ! open.contains(temp) && ! close.contains(temp)) {
                            open.add(temp);
                        }
                    }
                }
            }
        }
        if(resolved) {
            while(!close.empty()) {
                State temp = close.pop();
                temp.setSolved(true);
            }
        }
        return resolved;
    }


    public boolean residual(State state, double factor) {
        return Math.abs(state.value - state.lastValue) < factor;
    }

}
