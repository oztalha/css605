package edu.gmu.toz.week01.assignment;

/**
 * Here is your assignment for Week 1:
 * 
 * DUE: September 2nd at noon (since September 1st is Labor Day)
 * 
 * NOTE: You cannot use the Math library for this assignment.
 *
 * TASK 1: Copy Week1.java and Week1Test.java to your our package following the
 * same pattern as used above (i.e., replace rcasstev with your username).
 * 
 * TASK 2: Implement the square and cube methods.
 *
 * TASK 3: Make several calls to these methods in the main method and output the
 * results to standard out.
 *
 * TASK 4: Run the unit tests in Week1Test.java to ensure your methods are
 * correct.
 * 
 * TASK 5: Email your version of Week1.java to rcasstev@gmu.edu
 * 
 */
public class Week1 {

	/**
	 * Calculates the square.
	 * 
	 * @param num
	 *            the input value
	 * @return the square of the num
	 */
	public static double square(double num) {
		return num*num;
	}

	/**
	 * Calculates the cube.
	 * 
	 * @param num
	 *            the input value
	 * @return the cube of the num
	 */
	public static double cube(double num) {
		return square(num)*num;
	}

	public static void main(String[] args) {
		System.out.println(square(0.5));
		System.out.println(square(-2));
		System.out.println(cube(2.5));
		System.out.println(cube(-3));
	}

}
