package mdp.component;

import mdp.util.MDPContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntBinaryOperator;

/**
 * Created by ch_knight on 10/22/2016.
 * The Action to generate all Possible Action currently.
 */
public class ActionGenerator {
    public static int maxPurchase;
    public static int maxReturn;

    public static List<Action> allPossibleList = new ArrayList<>();

    public static void generateAllActions(int maxPurchase, int maxReturn) {
        ActionGenerator.maxPurchase = maxPurchase;
        ActionGenerator.maxReturn = maxReturn;
        Set<List<Integer>> possibleOrderSet = new HashSet<>();
        Set<List<Integer>> possibleReturnSet = new HashSet<>();

        List<Integer> initialOrderList = new ArrayList<>(MDPContext.MaxType);
        List<Integer> initialReturnList = new ArrayList<>(MDPContext.MaxType);
        for(int i = 0; i < MDPContext.MaxType; ++i) {
            initialOrderList.add(0);
            initialReturnList.add(0);
        }

        generateAllPossibleList(initialOrderList, 0, maxPurchase, possibleOrderSet);
        generateAllPossibleList(initialReturnList,0, maxReturn, possibleReturnSet);


        List<List<Integer>> orderLists = new ArrayList<>(possibleOrderSet);
        List<List<Integer>> returnLists = new ArrayList<>(possibleReturnSet);

        System.out.println(orderLists.size());
        System.out.println(returnLists.size());

        for(int i = 0; i < possibleOrderSet.size(); i++) {
            for(int j = 0; j < possibleReturnSet.size(); ++j) {
                Action action = new Action();
                if(isValid(orderLists.get(i), returnLists.get(j))) {
                    action.setOrderList(orderLists.get(i));
                    action.setReturnList(returnLists.get(j));
                    allPossibleList.add(action);
                }
            }
        }
    }

    public static void generateAllPossibleList(List<Integer> currentList, int total, int max, Set<List<Integer>> possibleList) {
        if(total <= max) {
            possibleList.add(currentList);
            for(int i = 0; i < currentList.size(); ++i) {
                List<Integer> temp = new ArrayList<>(currentList);
                temp.set(i, currentList.get(i) + 1);
                if(!possibleList.contains(temp)) {
                    generateAllPossibleList(temp, total + 1, max, possibleList);
                }
            }
        }
    }

    public static boolean isValid(List<Integer> orderList, List<Integer> returnList) {
        for(int i = 0; i < orderList.size(); ++i) {
            if(orderList.get(i) > 0 && returnList.get(i) > 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Action> getPossibleActions(State currentState) {
        List<Action> legalActions = new ArrayList<>(allPossibleList.size());
        for(Action action : allPossibleList) {
            State nextState = action.generateNewState(currentState);
            if(nextState != null) {
                legalActions.add(action);
            }
        }
        return legalActions;
    }
}
