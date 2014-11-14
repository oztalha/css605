package edu.gmu.toz.week02.assignment;

import java.util.Arrays;

/**
 * Here is your assignment for Week 2:
 * 
 * DUE: September 8th at noon
 *
 * TASK 1: Copy Week2.java and Week2Test.java to your own package following the
 * same pattern as used above (i.e., replace toz with your username).
 * 
 * TASK 2: Implement the averageElements and squareElements methods.
 *
 * TASK 3: Make several calls to these methods in the main method to make sure
 * they are calculating the results correctly and output the results to standard
 * out (i.e., System.out).
 *
 * TASK 4: Run the unit tests in Week2Test.java to ensure your methods are
 * correct.
 * 
 * TASK 5: Email your version of Week2.java to toz@gmu.edu
 * 
 */
public class Week2 {

	/**
	 * Take the average of all the elements in the array.
	 * 
	 * @param nums
	 * @return
	 */
	public static double averageElements(double[] nums) {
		double avg = 0;
		for (int i = 0; i < nums.length; i++) {
			avg += nums[i];
		}
		avg /= nums.length ;
		return avg;
	}

	/**
	 * Square each of the elements and return the values in an array.
	 * 
	 * @param nums
	 * @return
	 */
	public static double[] squareElements(double[] nums) {
		double [] sq = new double[nums.length];
		for (int i = 0; i < nums.length; i++) {
			sq[i] = nums[i]*nums[i];
		}
		return sq;
	}
	
	public static void main(String[] args) {
		double[] nums = new double[]{-2.,-1.,-.5,0.,0.5,1.,2.};
		double [] sq = squareElements(nums);
		
		System.out.println(Arrays.toString(sq));
		System.out.println(averageElements(sq)); // 1.5 = 10.5 / 6
		System.out.println(averageElements(nums)); // zero

	}
}
