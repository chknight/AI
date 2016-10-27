package test;

import mdp.component.Action;
import mdp.component.Transaction;
import mdp.util.MDPContext;
import problem.Matrix;

import java.util.ArrayList;

/**
 * Created by ch_knight on 10/27/2016.
 */
public class TesterInitialize {

    public static void initiizeWith2() {
        MDPContext.maxStore = 2;
        MDPContext.MaxType = 2;
        Action.maxReturn = 2;
        Action.maxOrder = 2;
        MDPContext.cutoffPenalytPerItem = 2;
        double[][] probabilities1 = {{0.3, 0.4, 0.3}, {0.4, 0.4, 0.2}, {0.4, 0.4, 0.2}};
        double[][] probabilities2 = {{0.4, 0.2, 0.4}, {0.2, 0.6, 0.2}, {0.5, 0.5, 0.0}};
        MDPContext.probabilities = new ArrayList<>();
        MDPContext.probabilities.add(new Matrix(probabilities1));
        MDPContext.probabilities.add(new Matrix(probabilities2));
    }
}
