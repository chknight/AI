package mdp.util;

import mdp.component.State;
import problem.Matrix;
import problem.ProblemSpec;

import java.util.List;
import java.util.Map;

/**
 * The class to store necessary variables
 * Created by ch_knight on 10/21/2016.
 */
public class MDPContext {
    public static List<Matrix> probabilities;
    public static List<Double> prices;
    public static ProblemSpec problemSpec;
    public static int MaxType;
    public static int maxStore;
    public static double cutoffPenalytPerItem;
    public static Map<State, Double> valueFunction;
    public static double discountFactor;
    public static Map<List<Integer>, State> allStates;
}
