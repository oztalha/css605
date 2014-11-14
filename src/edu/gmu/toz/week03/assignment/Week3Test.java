package edu.gmu.toz.week03.assignment;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Before you can use this file, you need to add JUnit to your build path in
 * Eclipse.
 * 
 * In my version of Eclipse:
 *
 * Project -> Properties -> Java Build Path -> Libraries -> Add Library… ->
 * select JUnit
 * 
 * @author randy
 *
 */
public class Week3Test {

	// This is just a constant with a small value
	public static final double EPSILON = 0.00001;

	// The numbers to be used in the unit tests
	private double[][] nums1;

	private double[][] nums2;

	// Variables to hold the answers to the unit tests
	private double[] nums1RowSum;

	private double[] nums2RowSum;

	private double[] nums1ColumnSum;

	private double[] nums2ColumnSum;

	private String numsString1;
	private String numsString2;

	private double[] numsString1Answer;
	private double[] numsString2Answer;

	/**
	 * Setup the input values used for the unit tests.
	 */
	@Before
	public void setup() {
		// Create arrays of values for testing
		nums1 = new double[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 },
				{ 9, 10, 11, 12 } };
		nums2 = new double[][] { { 2.0, 4.5, 11, 4.0 }, { 1.0, 17.5 },
				{ 2.5, 1.0, 0.5 } };

		// Calculate the correct answers to the unit tests
		nums1RowSum = new double[] { 1 + 2 + 3 + 4, 5 + 6 + 7 + 8,
				9 + 10 + 11 + 12 };
		nums2RowSum = new double[] { 2.0 + 4.5 + 11 + 4.0, 1 + 17.5,
				2.5 + 1.0 + 0.5 };

		nums1ColumnSum = new double[] { 1 + 5 + 9, 2 + 6 + 10, 3 + 7 + 11,
				4 + 8 + 12 };
		nums2ColumnSum = new double[] { 2.0 + 1.0 + 2.5, 4.5 + 17.5 + 1.0,
				11 + 0.5, 4.0 };

		numsString1 = "1,2,4,8,16,32,64";
		numsString1Answer = new double[] { 1, 2, 4, 8, 16, 32, 64 };

		numsString2 = "1.25,   2.5   , 5.0,   10.0   ";
		numsString2Answer = new double[] { 1.25, 2.5, 5.0, 10.0 };
	}

	/**
	 * Tests the method for summing the rows of an array.
	 */
	@Test
	public void testRowSum() {
		assertArrayEquals("Row sum test failed for average of nums1",
				nums1RowSum, Week3.sumRowElements(nums1), EPSILON);
		assertArrayEquals("Row sum test failed for average of nums2",
				nums2RowSum, Week3.sumRowElements(nums2), EPSILON);
	}

	/**
	 * Tests the method for summing the columns of an array.
	 */
	@Test
	public void testColumnSum() {
		assertArrayEquals("Column sum test failed for average of nums1",
				nums1ColumnSum, Week3.sumColumnElements(nums1), EPSILON);
		assertArrayEquals("Column sum test failed for average of nums2",
				nums2ColumnSum, Week3.sumColumnElements(nums2), EPSILON);
	}

	/**
	 * Tests the method for converting a String into an array of doubles.
	 */
	@Test
	public void testStringToArray() {
		assertArrayEquals("Column sum test failed for average of nums1",
				numsString1Answer, Week3.stringToArray(numsString1), EPSILON);
		assertArrayEquals("Column sum test failed for average of nums2",
				numsString2Answer, Week3.stringToArray(numsString2), EPSILON);
	}
}
