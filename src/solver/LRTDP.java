package solver;

import mdp.component.*;
import mdp.util.MDPContext;
import mdp.util.UtilFunctions;
import problem.ProblemSpec;
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

    long maxTime = 55 * 1000;

    double factor = 1e-7;

    List<State> solveState;

    public LRTDP(ProblemSpec problemSpec) {
        UtilFunctions.initializeMDPContext(problemSpec);
        if(MDPContext.MaxType <= 3) {
            maxTime = 28 * 1000;
        } else {
            maxTime = 55 * 1000;
        }
    }


    @Override
    public void doOfflineComputation() {

    }

    @Override
    public List<Integer> generateStockOrder(List<Integer> inventory, int numWeeksLeft) {
        State currentState = MDPContext.allStates.get(inventory);
        if(currentState == null) {
            currentState = new State(inventory);
            MDPContext.allStates.put(inventory, currentState);
        }
        Action action = getNextStep(currentState, factor);
        return action.generateOrderList();
    }

    public LRTDP() throws IOException {
        simulator = new Simulator(MDPContext.problemSpec);
        solveState = new ArrayList<>();
    }

    public Action getNextStep(State currentState,  double factor) {
        Stack<State> visited = new Stack<>();
        State previousState = currentState;
        long startTime = System.currentTimeMillis();
        long currentTime;
        while(!currentState.isSolved()) {
            currentTime = System.currentTimeMillis();
            if(currentTime - startTime >= maxTime) {
                break;
            }
            visited.push(currentState);
            Action nextAction = greedyAction(currentState);
            currentState = getNextState(currentState, nextAction);
        }
        while (!visited.isEmpty()) {
            State state = visited.pop();
            if(!checkSolved(state, factor)) {
                break;
            }
        }
        System.out.println("One of the get next Step end, the time is " +  (System.currentTimeMillis() - startTime));
        return previousState.bestAction;
    }

    // apply greedy method to get next action to apply
    public Action greedyAction(State currentState) {
        ValueFunction.getOneIterationValue(currentState);
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
                Action action = greedyAction(state);
                List<State> allStates = Transaction.getAllPossibleState(state);
                Map<State, Double> possibleStates = Transaction.getAllProbabilities(state, action);
                for(State temp : allStates) {
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

    // get the next state
    private State getNextState(State currentState, Action action) {
        List<State> allPossibleState = Transaction.getAllPossibleState(currentState, action);
        Map<State, Double> probabilties = Transaction.getAllProbabilities(currentState, allPossibleState);
        Random random = new Random();
        double total = 0.0;
        double next = random.nextDouble();
        for(State state : allPossibleState) {
            double temp = probabilties.get(state);
            if(total <= next  && next < temp + total) {
                return state;
            } else{
                total += temp;
            }
        }
        return allPossibleState.get(allPossibleState.size() - 1);
    }


    public boolean residual(State state, double factor) {
        return Math.abs(state.value - state.lastValue) < factor;
    }

}
