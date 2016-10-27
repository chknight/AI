package test;

import mdp.component.Action;
import mdp.util.MDPContext;

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
    }
}
