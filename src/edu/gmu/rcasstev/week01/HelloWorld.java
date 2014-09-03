package edu.gmu.rcasstev.week01;

/**
 * A simple hello world program that show the difference between System.out and
 * System.err.
 * 
 * Shows in important lesson to be careful not to mix the two while debugging.
 * 
 * @author randy
 *
 */
public class HelloWorld {

	/**
	 * GOOD CODING PRACTICE: Use System.err for debugging instead of System.out
	 * to help better understand what is going on in your code. Better yet...
	 * use a debugger!
	 */
	public static void main(String[] args) {

		// This is a comment

		/*
		 * This is a block comment that can extend over multiple lines.
		 */

		// Standard hello world command on System.out
		System.out.println("Hello World!");

		// Since the following is not buffered it may actually appear before the
		// previous statement
		System.err.println("Hello World! (not buffered!!)");
	}
}
