package edu.gmu.toz.week01.assignment;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Before you can use this file, you need to add JUnit to your build path in
 * Eclipse.
 * 
 * In my version of Eclipse:
 *
 * Project -> Properties -> Java Build Path -> Libraries -> Add Library ->
 * select JUnit
 * 
 * @author toz
 *
 */
public class Week1Test {

	// This is just a constant with a small value
	public static final double EPSILON = 0.00001;

	// The numbers to be used in the unit tests
	private double[] nums;

	/**
	 * Setup the input values used for the unit tests.
	 * 
	 * This could have been done above, but this shows how you can setup values
	 * before each unit test is executed.
	 */
	@Before
	public void setup() {
		// We will go over arrays soon, but here is an example of
		// creating a new array and initializing the values
		nums = new double[] { 2.0, 4.5, 11, 0, 1, 12345, 99999,
				Double.MAX_VALUE, Math.random(), Math.random() };
	}

	/**
	 * Tests the square method with several values.
	 */
	@Test
	public void testSquare() {
		for (double num : nums) {
			assertEquals("Test failed for: " + num, Math.pow(num, 2),
					Week1.square(num), EPSILON);
		}
	}

	/**
	 * Tests the cube method with several values.
	 */
	@Test
	public void testCube() {
		for (double num : nums) {
			assertEquals("Test failed for: " + num, Math.pow(num, 3),
					Week1.cube(num), EPSILON);
		}
	}
}
