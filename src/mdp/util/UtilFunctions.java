package mdp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ch_knight on 10/27/2016.
 */
public class UtilFunctions {

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
}
