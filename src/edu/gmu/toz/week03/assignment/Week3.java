package edu.gmu.toz.week03.assignment;


/**
 * Here is your assignment for Week 3:
 * 
 * DUE: September 15th at noon
 *
 * TASK 1: Copy Week3.java and Week3Test.java to your own package following the
 * same pattern as used above (i.e., replace toz with your username).
 * 
 * TASK 2: Implement the sumRowElements, sumColumnElements, and stringToArray
 * methods.
 *
 * TASK 3: Make several calls to these methods in the main method to make sure
 * they are calculating the results correctly and output the results to standard
 * out (i.e., System.out).
 *
 * TASK 4: Run the unit tests in Week3Test.java to ensure your methods are
 * correct.
 * 
 * TASK 5: Email your version of Week3.java to toz@gmu.edu
 * 
 */
public class Week3 {

	/**
	 * Sum the numbers in each of the rows of the two dimensional array given as
	 * input.
	 * 
	 * @param nums
	 *            the numbers to be summed
	 * @return the sum for each row
	 */
	public static double[] sumRowElements(double[][] nums) {

		double[] rowSum = new double[nums.length]; //initialized to zero
		
		for (int i = 0; i < nums.length; i++) {
			double[] ds = nums[i];
			for (double d : ds) {
				rowSum[i] += d;
			}
		}
		return rowSum;
	}

	/**
	 * Sum the numbers in each of the columns of the two dimensional array given
	 * as input.
	 * 
	 * Note: This can be tricker because there could rows with different column
	 * lengths.
	 * 
	 * @param nums
	 *            the numbers to be summed
	 * @return the sum for each column
	 */
	public static double[] sumColumnElements(double[][] nums) {

		//get the longest row length
		int longestRow = 0;
		for (double[] ds : nums) {
			if(ds.length > longestRow)
				longestRow = ds.length;
		}
		
		double[] columnSum = new double[longestRow]; //initialized to zero
		
		for (int i = 0; i < longestRow; i++) {
			for (double[] ds : nums) {
				if ( ds.length > i)
					columnSum[i] += ds[i];
			}			
		}

		return columnSum;
	}

	/**
	 * Convert the String input into an array of doubles. The numbers in the
	 * input String are separated with a comma (,).
	 * 
	 * @param numsString
	 *            the String that contains the numbers separated by a comma
	 * @return the array that contains the numbers as doubles
	 */
	public static double[] stringToArray(String numsString) {

		String [] numbers = numsString.split(",");
		double [] doubles = new double[numbers.length];
		
		for (int i = 0; i < numbers.length; i++) {
			doubles[i] = Double.parseDouble(numbers[i]); //parseDouble trims the string
		}

		return doubles;
	}
}
