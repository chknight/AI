package mdp.component;

import mdp.MDP;
import mdp.util.MDPContext;
import mdp.util.UtilFunctions;

import java.util.*;

/**
 * Created by ch_knight on 10/27/2016.
 */
public class StateGenerator {

    public static void generateAllState() {
        Set<List<Integer>> possibleItemLists = new HashSet<>();
        List<Integer> current = new ArrayList<>(MDPContext.MaxType);

        for(int i = 0; i < MDPContext.MaxType; ++i) {
            current.add(0);
        }

        UtilFunctions.generateAllPossibleList(current, 0, MDPContext.maxStore, possibleItemLists);
        MDPContext.stateList = new ArrayList<>();
        Iterator<List<Integer>> iterator = possibleItemLists.iterator();
        while(iterator.hasNext()) {
            State state = new State(iterator.next());
            MDPContext.stateList.add(state);
        }
    }
}
