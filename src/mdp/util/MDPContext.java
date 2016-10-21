package mdp.util;

import problem.Matrix;
import problem.ProblemSpec;

import java.util.List;

/**
 * The class to store necessary variables
 * Created by ch_knight on 10/21/2016.
 */
public class MDPContext {
    public static List<Matrix> probabilities;
    public static List<Double> prices;
    public static ProblemSpec problemSpec;
    public static int MaxType;
}
