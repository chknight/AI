package problem;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class is used for file I/O
 */
public class ProblemSpec {
	
	/** True iff user stochastic model is currently loaded */
	private boolean modelLoaded = false;
	/** The number of weeks the store will be evaluated */
	private int numWeeks;
    /** Penalty per item cut for when order-return causes store to go over capacity */
	private double penaltyFee;
	/** Discount factor */
	private double discountFactor;
	/** The store type */
	private Store store;
	/** The probabilities for the user's consumption behaviour */
	private List<Matrix> probabilities;
    /** The prices of each item type */
    private List<Double> prices;
    /** Initial stock of each item type before replenishment */
    private List<Integer> initialStock;
	
	public ProblemSpec() {
	}
	
	public ProblemSpec(String specFileName) throws IOException {
	    this();
	    loadInputFile(specFileName);
	}
	
	/**
	 * Loads the user's stochastic model from file
	 * @param filename the path of the text file to load.
	 * @throws IOException
	 * 		if the text file doesn't exist or doesn't meet the assignment
	 *      specifications.
	 */
	public void loadInputFile(String filename) throws IOException {
		modelLoaded = false;
		BufferedReader input = new BufferedReader(new FileReader(filename));
		String line;
		int lineNo = 0;
		Scanner s;
		try {
		    // read store type
            line = input.readLine();
            lineNo++;
            store = new Store(line.trim().toLowerCase());

            // read discount factor
		    line = input.readLine();
            lineNo++;
            s = new Scanner(line);
            s.useLocale(Locale.US); // For decimal point handling for computers set up for foreign locale
            discountFactor = s.nextDouble();
            s.close();

            // read number of weeks over which the store is tested
			line = input.readLine();
			lineNo++;
			s = new Scanner(line);
			numWeeks = s.nextInt();
			s.close();

            // read penalty fee
			line = input.readLine();
			lineNo++;
			s = new Scanner(line);
            s.useLocale(Locale.US);
			penaltyFee = s.nextDouble();
			s.close();
			
			// read item prices
            line = input.readLine();
            lineNo++;
            s = new Scanner(line);
            s.useLocale(Locale.US);
            prices = new ArrayList<Double>(store.getMaxTypes());
            for(int i = 0; i < store.getMaxTypes(); i++) {
                if(s.hasNextDouble()) {
                    prices.add(s.nextDouble());
                } else {
                    throw new IOException("Not enough prices for the store type.");
                }

            }

            // read initial stock levels
            line = input.readLine();
            lineNo++;
            s = new Scanner(line);
            initialStock = new ArrayList<Integer>(store.getMaxTypes());
            for(int i = 0; i < store.getMaxTypes(); i++) {
                if(s.hasNextInt()) {
                    initialStock.add(s.nextInt());
                } else {
                    throw new IOException("Not enough initial stock levels for the store type.");
                }
            }
			
			// read customer behaviour model
			probabilities = new ArrayList<Matrix>();
			for (int k = 0; k < store.getMaxTypes(); k++) {
				double[][] data = new double[store.getCapacity() + 1]
						[store.getCapacity() + 1];
				for (int i = 0; i <= store.getCapacity(); i++) {
					line = input.readLine();
					lineNo++;
					double rowSum = 0;
					s = new Scanner(line);
                    s.useLocale(Locale.US);
					for (int j = 0; j <= store.getCapacity(); j++) {
						data[i][j] = s.nextDouble();
						rowSum += data[i][j];
					}
					s.close();
					if (Math.round(rowSum*100000) != 100000) {
						throw new InputMismatchException(
								"Row probabilities do not sum to 1.");
					}
				}
				probabilities.add(new Matrix(data));
			}
			modelLoaded = true;
		} catch (InputMismatchException e) {
			throw new IOException(String.format(
					"Invalid number format on line %d: %s", lineNo,
					e.getMessage()));
		} catch (NoSuchElementException e) {
			throw new IOException(String.format("Not enough tokens on line %d",
					lineNo));
		} catch (NullPointerException e) {
			throw new IOException(String.format(
					"Line %d expected, but file ended.", lineNo));
		} finally {
			input.close();
		}
	}
	
	/**
	 * Save output to file
	 * @param filename The file path to save to
	 * @param totalProfit The total cost
	 * @param orderHistory List of all shopping orders starting at week 0
	 * @throws IOException
	 */
	public void saveOutput(String filename, double totalProfit, List<List<Integer>> requestHistory,
                           List<List<Integer>> orderHistory, List<List<Integer>> returnHistory) throws IOException {
		String ls = System.getProperty("line.separator");
		FileWriter output = new FileWriter(filename);

        // write number of weeks
        output.write(String.format("%d %s", numWeeks, ls));

        // write initial stock
        for(int item : initialStock) {
            output.write(item + " ");
        }
        output.write(ls);

        for(int week = 0; week < numWeeks; week++) {

            // write request
            for(int item : requestHistory.get(week)) {
                output.write(item + " ");
            }
            output.write(ls);

            // do not write order and request for week N
            if(week == numWeeks) {
                break;
            }

            // write order
            for(int item : orderHistory.get(week)) {
                output.write(item + " ");
            }
            output.write(ls);


            // write returns
            for(int item : returnHistory.get(week)) {
                output.write(item + " ");
            }
            output.write(ls);

        }

		//output.write(String.format("%f", totalProfit));
		output.close();
	}

	public boolean isModelLoaded() {
		return modelLoaded;
	}

	public int getNumWeeks() {
		return numWeeks;
	}

	public double getPenaltyFee() {
		return penaltyFee;
	}

	public double getDiscountFactor() {
		return discountFactor;
	}

	public Store getStore() {
		return store;
	}

	public List<Matrix> getProbabilities() {
		return new ArrayList<Matrix>(probabilities);
	}

	public List<Double> getPrices() {
	    return new ArrayList<Double>(prices);
	}

	public List<Integer> getInitialStock() {
	    return new ArrayList<Integer>(initialStock);
    }
}
