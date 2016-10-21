package mdp;

import mdp.component.Action;
import mdp.component.State;
import mdp.component.Transaction;
import mdp.util.MDPContext;
import problem.ProblemSpec;

/**
 * Created by ch_knight on 10/21/2016.
 */
public class MDP {

    ProblemSpec problemSpec;

    // the bootstrap function for the program
    public static void main(String[] args) {

        String filePath = args[0];

    }

    public MDP(String inputPath) {
        problemSpec = new ProblemSpec();
        try {
            problemSpec.loadInputFile(inputPath);
            State.penaltyPerItem = problemSpec.getPenaltyFee();
            Transaction.probabilities = problemSpec.getProbabilities();
            MDPContext.problemSpec = problemSpec;
            MDPContext.probabilities = problemSpec.getProbabilities();
            MDPContext.prices = problemSpec.getPrices();
            MDPContext.MaxType = problemSpec.getStore().getMaxTypes();
            Action.maxOrder = problemSpec.getStore().getMaxPurchase();
            Action.maxReturn = problemSpec.getStore().getMaxReturns();
            State.maxStore = problemSpec.getStore().getCapacity();

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}