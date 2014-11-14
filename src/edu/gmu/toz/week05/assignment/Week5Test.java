package edu.gmu.toz.week05.assignment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
public class Week5Test {

	// This is just a constant with a small value
	public static final double EPSILON = 0.00001;

	// The values to be used in the unit test
	private List<String> stringList;

	private String filename;

	/**
	 * Setup the input values used for the unit tests.
	 */
	@Before
	public void setup() {
		// Create values for testing
		stringList = new ArrayList<String>();
		stringList.add("one");
		stringList.add("two");
		stringList.add("three");
		stringList.add("four");
		stringList.add("five");

		filename = Week5.class.getPackage().getName().replace('.', '_')
				+ "_Week5_output.txt";
	}

	/**
	 * Tests the method for finding values between two dates.
	 */
	@Test
	public void testWriteThenReadData() {

		// Write date to file
		Week5.writeDataToFile(filename, stringList);

		// Read data from file
		List<String> stringListFromFile = Week5.readDataFromFile(filename);

		// Make sure it isn't the exact same data
		assertTrue(
				"stringList and stringListFromFile should not be referring to the same instance of List",
				stringList != stringListFromFile);

		String[] stringListArray = stringList.toArray(new String[stringList
				.size()]);
		String[] stringListFromFileArray = stringListFromFile
				.toArray(new String[stringListFromFile.size()]);

		// Make sure the data was read in correctly
		assertArrayEquals(
				"Original String List and String List from file are not the same",
				stringListArray, stringListFromFileArray);
	}
}
