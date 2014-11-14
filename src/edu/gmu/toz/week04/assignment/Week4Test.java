package edu.gmu.toz.week04.assignment;

import static org.junit.Assert.*;

import java.util.Date;

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
public class Week4Test {

	// This is just a constant with a small value
	public static final double EPSILON = 0.00001;

	// The numbers to be used in the unit test
	private double[] nums;

	private Date[] dates;

	/**
	 * Setup the input values used for the unit tests.
	 */
	@Before
	public void setup() {
		// Create arrays of values for testing
		nums = new double[10];
		dates = new Date[10];

		for (int i = 0; i < dates.length; i++) {
			nums[i] = i + 1;
			dates[i] = new Date((i + 1) * 1000);
		}
	}

	/**
	 * Tests the method for finding values between two dates.
	 */
	@Test
	public void testRowSum() {
		assertArrayEquals("Test 1 - start 500, end 5500", new double[] { 1, 2,
				3, 4, 5 }, Week4.findValuesWithinRange(dates, nums, new Date(
				500), new Date(5500)), EPSILON);

		assertArrayEquals("Test 2 - start 1000, end 10000", new double[] { 1,
				2, 3, 4, 5, 6, 7, 8, 9, 10 }, Week4.findValuesWithinRange(
				dates, nums, new Date(1000), new Date(10000)), EPSILON);

		assertArrayEquals("Test 3 - start 6000, end 6000", new double[] { 6 },
				Week4.findValuesWithinRange(dates, nums, new Date(6000),
						new Date(6000)), EPSILON);

		try {
			Week4.findValuesWithinRange(dates, new double[] { 1, 2, 3, 4, 5 },
					new Date(1000), new Date(7000));

			assertFalse("Should have thrown an IllegalArgumentException", true);
		} catch (IllegalArgumentException e) {
			// Do nothing because since it made it here, then it did what it was
			// supposed to do.
		}
	}
}
