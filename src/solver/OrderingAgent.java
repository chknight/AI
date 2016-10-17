package solver;

import java.util.List;

/** A generic interface for the automatic ordering system  */
public interface OrderingAgent {

    /** 
     * Perform any computations that should be performed offline,
     * before the simulation begins
     */
    public void doOfflineComputation();
    
    /** 
     * Represents the policy of this solver. Given the current inventory
     * and number of weeks to go, this method should return a valid
     * stock order.
     * @param inventory the 
     * @param numWeeksLeft the number of weeks of weeks left after this one;
     *  a value of 0 means this is the last week.
     * @return 
     */
    public List<Integer> generateStockOrder(List<Integer> inventory,
                                            int numWeeksLeft);
}
