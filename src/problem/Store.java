package problem;

public class Store {
	
	/** Name of store class */
	private String name;
	/** Max number of items the store can stock. */
	private int capacity;
	/** Max number of items the store can order in a week. */
    private int maxOrder;
	/** Max number of items the store can return in a week. */
	private int maxReturns;
	/** Max number of item types a user might need in a week */
	private int maxTypes;
	
	/**
	 * Constructor
	 * @param name
	 * @param capacity
	 * @param maxTypes
	 */
	public Store(String name, int capacity, int maxOrder, int maxReturns, int maxTypes) {
		this.name = name;
		this.capacity = capacity;
		this.maxOrder = maxOrder;
		this.maxReturns = maxReturns;
		this.maxTypes = maxTypes;
	}
	
	/**
	 * Constructor
	 * @param name Takes values tiny, small, medium, large or super
	 */
	public Store(String name) {
		this.name = name;
		if (name.equals("tiny")) { 
			capacity = 3;
			maxOrder = 2;
			maxReturns = 1;
			maxTypes = 2;
		} else if (name.equals("small")) {
			capacity = 8;
			maxOrder = 3;
			maxReturns = 2;
			maxTypes = 2;
		} else if (name.equals("medium")) {
			capacity = 8;
			maxOrder = 3;
			maxReturns = 2;
			maxTypes = 3;
		} else if (name.equals("large")) {
			capacity = 10;
			maxOrder = 5;
			maxReturns = 3;
			maxTypes = 5;
		} else if (name.equals("mega")) {
			capacity = 20;
			maxOrder = 15;
			maxReturns = 10;
			maxTypes = 10;
		} else {
			throw new IllegalArgumentException("Invalid store class.");
		}
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public int getMaxPurchase() {
	    return maxOrder;
	}

	public int getMaxReturns() { return maxReturns; }

	public int getMaxTypes() {
		return maxTypes;
	}
}
