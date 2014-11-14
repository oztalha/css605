package edu.gmu.toz.week04.assignment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Here is your assignment for Week 4:
 * 
 * DUE: September 22th at noon
 *
 * TASK 1: Copy Week4.java and Week4Test.java to your own package following the
 * same pattern as used above (i.e., replace toz with your username).
 * 
 * TASK 2: Implement the findValuesWithinRange method.
 *
 * TASK 3: Make several calls to this method in the main method to make sure
 * they are calculating the results correctly and output the results to standard
 * out (i.e., System.out).
 *
 * TASK 4: Run the unit tests in Week4Test.java to ensure your method is
 * correct.
 * 
 * TASK 5: Email your version of Week4.java to toz@gmu.edu
 * 
 */
public class Week4 {

	/**
	 * The first two input parameters for this method are two arrays that should
	 * be of equal length. If they are not, then throw an
	 * IllegalArgumentException. The two arrays represent a time series where
	 * the Date array is the time the values in the double array were collected.
	 * 
	 * Using the start and end Date, return an array of doubles that are the
	 * values from the input parameter that lie between the two dates (including
	 * the data points that are equal to the start and end dates).
	 * 
	 * @param dates
	 * @param nums
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static double[] findValuesWithinRange(Date[] dates, double[] nums,
			Date startDate, Date endDate) throws IllegalArgumentException {

		if (dates.length != nums.length)
			throw new IllegalArgumentException("number of dates and numbers are different !");
		
		List<Double> doubles = new ArrayList<Double>();
		for (int i = 0; i < nums.length; i++) {
			if(dates[i].compareTo(startDate) >= 0 && dates[i].compareTo(endDate) <= 0)
				doubles.add(nums[i]);
		}
		
		 double[] target = new double[doubles.size()];
		 for (int i = 0; i < target.length; i++)
		    target[i] = doubles.get(i);
		
		return target;
	
	}

	public static void main(String[] args) throws IllegalArgumentException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
		Date [] dates = {sdf.parse("08-11-2007"),sdf.parse("08-11-2008"),sdf.parse("08-11-2009")};
		double[] nums = {20.07, 20.08, 20.09};
		double[] nums2 = {20.07, 20.08, 20.09, 20.10};
		
		System.out.println(Arrays.toString(findValuesWithinRange(dates,nums,sdf.parse("01-01-2008"),new Date())));
		
		System.out.println(Arrays.toString(findValuesWithinRange(dates,nums2,sdf.parse("01-01-2008"),new Date())));
	}

}
