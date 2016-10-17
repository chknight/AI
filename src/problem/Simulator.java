package problem;

import solver.OrderingAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {
    private Random random = new Random();

	private int currentWeek;
	private ProblemSpec problemSpec;
	private List<Integer> stockInventory;
	private ArrayList<List<Integer>> stockInventoryHistory;
	private ArrayList<List<Integer>> orderHistory;
    private ArrayList<List<Integer>> returnHistory;
	private ArrayList<List<Integer>> requestHistory;
	private ArrayList<Double> penaltyHistory;
    private double totalProfit = 0;
	private Store store;
	private List<Matrix> probabilities;
	private boolean verbose = true;
	
	/** 
	 * True if you want the store to start off being full, with random
	 * initial stock stockInventory.
	 */
	public static boolean RANDOM_INITIAL_CONTENTS = false;
	
	/**
	 * Constructor
	 * @param problemSpecPath path to input file
	 * @throws IOException
	 */
	public Simulator(String problemSpecPath) throws IOException {
	    this(new ProblemSpec(problemSpecPath));
	}
	
	/**
	 * Constructor
	 * @param spec A ProblemSpec
	 */
	public Simulator(ProblemSpec spec) {
	    problemSpec = spec;
		store = problemSpec.getStore();
		probabilities = problemSpec.getProbabilities();
	
        reset();
		
		if (verbose) {
			System.out.println("Problem spec loaded.");
			System.out.println("Store: " + store.getName());
			System.out.println("Discount factor: " + 
					problemSpec.getDiscountFactor());
			System.out.println("Penalty for order cutting: " + problemSpec.getPenaltyFee());
		}
	}
	
	public void reset() {
	    currentWeek = 1;
	    stockInventory = problemSpec.getInitialStock();
	    stockInventoryHistory = new ArrayList<List<Integer>>();
	    orderHistory = new ArrayList<List<Integer>>();
        returnHistory = new ArrayList<List<Integer>>();
	    requestHistory = new ArrayList<List<Integer>>();
	    penaltyHistory = new ArrayList<Double>();
        totalProfit = 0;

	}
	
	/**
	 * Simulate a week. A runtime exception is thrown if the stock order is
	 * invalid. If the stock order is valid, the customer consumption is sampled
	 * and the current week is advanced.
	 * @param solver
     * @param numWeeksLeft
	 */
	public void simulateStep(OrderingAgent solver, int numWeeksLeft) {
		if (verbose && currentWeek > problemSpec.getNumWeeks()) {
			System.out.println("Warning: problem spec num weeks exceeded.");
		}

		// compute profit for this week
		double profit = 0.0;

        // record stock level at start of week
        stockInventoryHistory.add(stockInventory);
        ArrayList<Integer> weekStartInventory = new ArrayList<Integer>(stockInventory);

        // ##### Simulate customer consumption
        List<Integer> wants = sampleUserWants(stockInventory);

        for (int j = 0; j < wants.size(); j++) {
            // compute profit from sales
            int sold = Math.min(wants.get(j), stockInventory.get(j));
            profit += (sold * problemSpec.getPrices().get(j) * 0.75);

            // compute missed opportunity penalty
            int missed = wants.get(j) - sold;
            profit -= (missed * problemSpec.getPrices().get(j) * 0.25);

            // update stock levels
            stockInventory.set(j, stockInventory.get(j) - sold);
        }

        // Add user wants to history
        requestHistory.add(wants);

        // record stock level after consumption
        List<Integer> afterConsumptionInventory = new ArrayList<Integer>(stockInventory);


        // ##### Get order
        List<Integer> order = solver.generateStockOrder(afterConsumptionInventory, numWeeksLeft);


		// ##### Cut items from order if necessary
		if (order.size() != store.getMaxTypes()) {
			throw new IllegalArgumentException("Invalid stock order list size");
		}

        // remove items from order until under capacity
        int sum = 0;
        for(int item : order) {
            sum += item;
        }

        ArrayList<Integer> correctedOrder = new ArrayList<Integer>(order);

        int item = 0;
        while(sum > store.getCapacity()) {
            if(order.get(item) == 0) {
                // go to next item type
                item++;

                if(item >= store.getMaxTypes()) {
                    throw new IllegalArgumentException("Capacity exceeded and cannot cut order any further");
                }
            } else {
                // cut the order of item type i
                order.set(item, order.get(item) - 1);
                sum--;
                correctedOrder.set(item, correctedOrder.get(item) - 1);

                // subtract penalty fee
                profit -= problemSpec.getPenaltyFee();
            }
        }
        order = correctedOrder;

        // ##### Apply order and returns to inventory
        int totalOrdered = 0;
        int totalReturned = 0;
		for (int i = 0; i < order.size(); i++) {
		    if(order.get(i) > 0) {
                totalOrdered += order.get(i);
                stockInventory.set(i, stockInventory.get(i) + order.get(i));
            } else {
                totalReturned += order.get(i);
                if(order.get(i) > stockInventory.get(i)) {
                    throw new IllegalArgumentException("Return amount exceeds current stock.");
                }
                stockInventory.set(i, stockInventory.get(i) - order.get(i));

                // subtract return fees
                profit -= (problemSpec.getPrices().get(i) * 0.5 * Math.abs(order.get(i)));
            }

		}
		if (totalOrdered > store.getMaxPurchase()) {
		    throw new IllegalArgumentException("Number of items ordered too large.");
		}
		if (totalReturned > store.getMaxReturns()) {
            throw new IllegalArgumentException("Number of items returned too large.");
        }

        ArrayList<Integer> afterOrderInventory = new ArrayList<Integer>(stockInventory);

        // record order and return history
        ArrayList<Integer> itemOrders = new ArrayList<Integer>();
        ArrayList<Integer> itemReturns = new ArrayList<Integer>();

        for(int j = 0; j < order.size(); j++) {
            if(order.get(j) > 0) {
                itemOrders.add(order.get(j));
                itemReturns.add(0);
            } else {
                itemOrders.add(0);
                itemReturns.add(order.get(j) * -1);
            }
        }

		orderHistory.add(itemOrders);
        returnHistory.add(itemReturns);


        // update total profit
        totalProfit += (Math.pow(problemSpec.getDiscountFactor(), currentWeek - 1) * profit);
		
		if (verbose) {
			System.out.println();
			System.out.println("Week " + currentWeek);
			System.out.println("Start stock:\t\t\t\t" + weekStartInventory);
            System.out.println("Customer wants:\t\t\t\t" + wants);
            System.out.println("After customer consumption:\t" + afterConsumptionInventory);
			System.out.println("Order:\t\t\t\t\t\t" + order);
			System.out.println("Post-order:\t\t\t\t\t" + afterOrderInventory);
			System.out.println("End:\t\t\t\t\t\t" + stockInventory);
			System.out.println("Profit this week: " + profit);
		
			if (currentWeek == problemSpec.getNumWeeks()) {
				System.out.println();
                System.out.println("Total discounted profit: " + totalProfit);
			}
		}	
		currentWeek ++;	
	}
	
	/**
	 * Uses the currently loaded stochastic model to sample user wants.
	 * Note that user wants may exceed the stockInventory
	 * @param state The stockInventory
	 * @return User wants as list of item quantities
	 */
	public List<Integer> sampleUserWants(List<Integer> state) {
		List<Integer> wants = new ArrayList<Integer>();
		for (int k = 0; k < store.getMaxTypes(); k++) {
			int i = state.get(k);
			List<Double> prob = probabilities.get(k).getRow(i);
			wants.add(sampleIndex(prob));
		}
		return wants;
	}
	
	/**
	 * Returns an index sampled from a list of probabilities
	 * @precondition probabilities in prob sum to 1
	 * @param prob
	 * @return an int with value within [0, prob.size() - 1]
	 */
	public int sampleIndex(List<Double> prob) {
		double sum = 0;
		double r = random.nextDouble();
		for (int i = 0; i < prob.size(); i++) {
			sum += prob.get(i);
			if (sum >= r) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Saves the current shopping history and total penalty to file
	 * @param filename The path to the text file to save to
	 * @throws IOException
	 */
	public void saveOutput(String filename) throws IOException {
		problemSpec.saveOutput(filename, getTotalProfit(), requestHistory, orderHistory, returnHistory);
	}

	/**
	 * Set verbose to true for console output
	 * @param verbose
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	/**
	 * Get penalty from history.
	 * @precondition week < currentWeek
	 * @param week The week to retrieve. Week starts at 1. 
	 * @return penalty
	 */
	public double getPenalty(int week) {
		return penaltyHistory.get(week - 1);
	}
	
	/**
	 * Get stockInventory from history
	 * @precondition week < currentWeek
	 * @param week The week to retrieve. Week starts at 1. 
	 * @return the stockInventory for that week.
	 */
	public List<Integer> getInventoryAt(int week) {
	    return stockInventoryHistory.get(week - 1);
	}
	
	/**
     * Get shopping list from history
     * @precondition week < currentWeek
     * @param week The week to retrieve. Week starts at 1. 
     * @return the shopping list for that week.
     */
	public List<Integer> getOrderAt(int week) {
	    return orderHistory.get(week - 1);
	}
	
	 /**
     * Get user request from history
     * @precondition week < currentWeek
     * @param week The week to retrieve. Week starts at 1. 
     * @return the user request for that week.
     */
    public List<Integer> getUserRequestAt(int week) {
        return requestHistory.get(week - 1);
    }

    /**
     * @return the total profit so far
     */
    public double getTotalProfit() {
        return totalProfit;
    }
    
	
	/**
	 * @return the total penalty so far
	 */
	//public double getTotalPenalty() {
	//	return totalPenalty;
	//}
	
	/** 
	 * @return the total maximum penalty so far
	 */
	//public double getTotalMaxPenalty() {
	//	return totalMaxPenalty;
	//}

	public int getCurrentWeek() {
		return currentWeek;
	}
	
	public List<Integer> getStockInventory() {
		return new ArrayList<Integer>(stockInventory);
	}
}
