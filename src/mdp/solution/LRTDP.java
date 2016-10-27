package mdp.solution;

import mdp.component.*;
import mdp.util.MDPContext;
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

    List<State> solveState;

    public LRTDP(ProblemSpec problemSpec) {
        MDPContext.MaxType = problemSpec.getStore().getMaxTypes();
        MDPContext.maxStore = problemSpec.getStore().getCapacity();
        MDPContext.prices = problemSpec.getPrices();
        MDPContext.allStates = new HashMap<>();
        MDPContext.cutoffPenalytPerItem = problemSpec.getPenaltyFee();
        MDPContext.discountFactor = problemSpec.getDiscountFactor();
        MDPContext.problemSpec = problemSpec;
        StateGenerator.generateAllState();
        Action.maxOrder = problemSpec.getStore().getMaxPurchase();
        Action.maxReturn = problemSpec.getStore().getMaxReturns();
        ActionGenerator.generateAllActions(Action.maxOrder, Action.maxReturn);
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
        if(currentState != null) {
            currentState = new State(inventory);
        }
        Action action = getNextStep(currentState, MDPContext.discountFactor);
        return action.getOrderList();
    }

    public LRTDP() throws IOException {
        simulator = new Simulator(MDPContext.problemSpec);
        solveState = new ArrayList<>();
    }

    public Action getNextStep(State currentState,  double factor) {
        Stack<State> visited = new Stack<>();
        Action result = new Action();
        long startTime = System.currentTimeMillis();
        long currentTime;
        while(currentState.isSolved()) {
            visited.push(currentState);
            Action nextAction = greedyAction(currentState);
            currentState = getNextState(currentState, nextAction);
        }
        while (!visited.isEmpty()) {
            State state = visited.pop();
            checkSolved(state, factor);
        }
        return result;
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
                Action action = greedyAction(currentState);
                Map<State, Double> possibleStates = Transaction.getAllProbabilities(state, action);
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

    // get the next state
    private State getNextState(State currentState, Action action) {
        List<State> allPossibleState = Transaction.getAllPossibleState(currentState, action);
        Map<State, Double> probabilties = Transaction.getAllProbabilities(currentState, allPossibleState, action);
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
