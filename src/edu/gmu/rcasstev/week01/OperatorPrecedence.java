package edu.gmu.rcasstev.week01;

/**
 * 
 * Several examples of operator precedence (i.e., order of operations).
 * 
 * @author randy
 *
 */
@SuppressWarnings("all")
public class OperatorPrecedence {

	public static void main(String[] args) {

		// Just adding
		System.out.println(1 + 1);

		// Multiple operators
		System.out.println(4 + 2 / 1 - 8 % 5);

		// Again multiple operators
		System.out.println(4 * 3 % 10 / 2);

		// Added parenthesis
		System.out.println(4 * (3 % 10) / 2);

		// Equality operator
		System.out.println(10 == 9);
		System.out.println(10 != 9);

		// Greater than and less than
		System.out.println(4 * 3 > 10);
		System.out.println(10 <= 20 / 4);

		//
		// COMMON ERRORS
		//

		System.out.println("COMMON ERRORS SECTION...");

		//
		// Floating Point Operations:
		// Using integer operations when you intended an floating point
		// operation.
		//

		// Watch out for the types!?
		System.out.println(1 + 1 / 2);

		// In the previous example, 1/2 gets evaluated first, but since it is an
		// 'int' operation, then it evaluates to zero. Here are some ways to
		// overcome this:

		// Now 1/2.0 gets evaluated as a double, so we get 1.5
		System.out.println(1 + 1 / 2.0);

		// Another way to do the same thing
		System.out.println(1 + 1 / 2d);

		// Yet another way...
		System.out.println(1 + 1.0 / 2);

		//
		// Overflow Error:
		// Another potential problem is an overflow error where the data type
		// cannot hold the result of the calculation.
		//

		// Say, we want to calculate the number of milliseconds there are in
		// four weeks
		System.out.println(1000 * 60 * 60 * 24 * 28);

		// But, this gives us a negative number! That can't be right and it
		// isn't. There was an overflow error, but there was no warnings about
		// it!

		// To fix this we can specify that the first 1000 is a long (using
		// 1000l). This looks pretty horrible because the l looks a bit like a
		// 1.
		System.out.println(1000l * 60 * 60 * 24 * 28);

		// So, a better solution would be:
		System.out.println(1000L * 60 * 60 * 24 * 28);

		//
		// The ++ Operator:
		// The ++ operator increments the variable, but depending on if it is
		// before or after the variable determines what value is used.
		//

		// The x++ can be a bit confusing because the old value of x is used and
		// THEN x is incremented. So, in the following two print statments, 10
		// and then 11 is printed.
		int x = 10;
		System.out.println(x++);
		System.out.println(x);

		// Here x is incremented, then the value used.
		x = 10;
		System.out.println(++x);

		// I avoid using the ++ operator except on a line by itself (e.g.,
		// x++;). It may take an extra line of code, but WELL worth it in
		// readability.

		//
		// Comparison of Floating Point Numbers
		// A floating point number may not be exactly the value that it was
		// assigned due to precision issues.
		//

		// For example, the following is not equal
		System.out.println(0.1f == 0.1d);

		// But, the following is equal
		System.out.println(0.5f == 0.5d);

		// Ultimately, the values are being stored in binary which means there
		// is a chance of loss of precision.
		// The safer option is to do the following:
		System.out.println(Math.abs(0.1f - 0.1d) < 0.000001);

		// Also take note that this is a problem with floating point values,
		// integers do not have this problem.

		//
		// Divide By Zero:
		// Dividing by zero is undefined so the following will throw an
		// exception.
		//

		// Throws an arithmetic exception because of divide by zero
		System.out.println(1 / 0);
	}
}
