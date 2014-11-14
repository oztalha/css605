package edu.gmu.toz.week05.assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Here is your assignment for Week 5:
 * 
 * DUE: September 29th at noon
 *
 * TASK 1: Copy Week5.java and Week5Test.java to your own package following the
 * same pattern as used above (i.e., replace toz with your username).
 * 
 * TASK 2: Implement the writeDataToFile and readDataFromFile methods.
 *
 * TASK 3: Make calls to these methods in the main method to make sure they are
 * producing the correct results.
 *
 * TASK 4: Run the unit tests in Week5Test.java to ensure your methods are
 * correct.
 * 
 * TASK 5: Email your version of Week5.java to toz@gmu.edu
 * 
 */
public class Week5 {

	/**
	 * Write the List of Strings to a file.
	 * 
	 * @param filename
	 *            the name of the file to write the Strings to
	 * @param stringList
	 *            the List of Strings to write to file
	 */
	public static void writeDataToFile(String filename, List<String> stringList) {

		PrintWriter out = null;
		try {
			// Create a writer using the filename
			out = new PrintWriter(filename);

			// Iterate over all elements of stringList
			for (String string : stringList) {
				out.println(string);
			}
			
		} catch (FileNotFoundException e) {
			// Just print the error
			e.printStackTrace();
		} finally {
			// Make sure to close the file
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * Read the List of Strings from a file.
	 * 
	 * @param filename
	 *            the name of the file to read the Strings from
	 * @return the List of Strings read from the file
	 */
	public static List<String> readDataFromFile(String filename) {

		BufferedReader in = null;
		List<String> stringList = new ArrayList<String>();
		try {
			// Create the reader for the filename
			in = new BufferedReader(new FileReader(filename));

			// While there is still another line, then parse it
			String nextLine = null;
			while ((nextLine = in.readLine()) != null) {
				// Split it up based on space characters
				stringList.addAll(Arrays.asList(nextLine.split("\\s")));
			}

		} catch (FileNotFoundException e) {
			// Just print the error
			e.printStackTrace();
		} catch (IOException e) {
			// Just print the error
			e.printStackTrace();
		} finally {
			// Make sure to close the file
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// Just print the error
					e.printStackTrace();
				}
			}
		}
		return stringList;
	}

	public static void main(String[] args) {

		List<String> sl = new ArrayList<String>(3);
		sl.add("bism");
		sl.add("think");
		sl.add("elh");
		//write to file
		writeDataToFile("test.txt", sl);
		//read from file
		List<String> read = readDataFromFile("test.txt");
		System.out.println(read);

	}

}
