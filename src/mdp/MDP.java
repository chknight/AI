package mdp;

import mdp.component.Action;
import mdp.component.State;
import mdp.component.Transaction;
import mdp.util.MDPContext;
import problem.ProblemSpec;

/**
 * The bootstrap class for the MDP
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
            MDPContext.problemSpec = problemSpec;
            MDPContext.maxStore = problemSpec.getStore().getCapacity();
            MDPContext.cutoffPenalytPerItem = problemSpec.getPenaltyFee();
            MDPContext.probabilities = problemSpec.getProbabilities();
            MDPContext.prices = problemSpec.getPrices();
            MDPContext.MaxType = problemSpec.getStore().getMaxTypes();
            MDPContext.discountFactor = problemSpec.getDiscountFactor();
            Action.maxOrder = problemSpec.getStore().getMaxPurchase();
            Action.maxReturn = problemSpec.getStore().getMaxReturns();
            State.maxStore = problemSpec.getStore().getCapacity();

        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
