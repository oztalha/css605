package edu.gmu.toz.week02.assignment;

import static org.junit.Assert.*;

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
 * @author randy
 *
 */
public class Week2Test {

	// This is just a constant with a small value
	public static final double EPSILON = 0.00001;

	// The numbers to be used in the unit tests
	private double[] nums1;

	private double[] nums2;

	// Variables to hold the answers to the unit tests
	private double nums1Average;

	private double nums2Average;

	private double[] nums1Squared;

	private double[] nums2Squared;

	/**
	 * Setup the input values used for the unit tests.
	 */
	@Before
	public void setup() {
		// Create arrays of values for testing
		nums1 = new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		nums2 = new double[] { 2.0, 4.5, 11, 0, 1, 17.5 };

		// Calculate the correct answers to the unit tests
		nums1Average = (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9) / 9.0;
		nums2Average = (2.0 + 4.5 + 11 + 0 + 1 + 17.5) / 6.0;

		nums1Squared = new double[] { 1 * 1, 2 * 2, 3 * 3, 4 * 4, 5 * 5, 6 * 6,
				7 * 7, 8 * 8, 9 * 9 };
		nums2Squared = new double[] { 2.0 * 2.0, 4.5 * 4.5, 11 * 11, 0 * 0,
				1 * 1, 17.5 * 17.5 };
	}

	/**
	 * Tests the method for averaging the elements of an array.
	 */
	@Test
	public void testAverage() {
		assertEquals("Test failed for average of nums1", nums1Average,
				Week2.averageElements(nums1), EPSILON);
		assertEquals("Test failed for average of nums2", nums2Average,
				Week2.averageElements(nums2), EPSILON);
	}

	/**
	 * Tests the method for squaring each element of an array.
	 */
	@Test
	public void testSquare() {
		assertArrayEquals("Test failed for squaring each element in nums1",
				nums1Squared, Week2.squareElements(nums1), EPSILON);
		assertArrayEquals("Test failed for squaring each element in nums2",
				nums2Squared, Week2.squareElements(nums2), EPSILON);
	}
}
