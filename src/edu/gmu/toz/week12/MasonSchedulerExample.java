package edu.gmu.toz.week12;

import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * Simple example of using the scheduler in the MASON simulation library.
 * 
 * @author randy
 *
 */
public class MasonSchedulerExample extends SimState {

	private static final long serialVersionUID = 1L;

	public MasonSchedulerExample(long seed) {
		super(seed);
	}

	public void start() {
		super.start();

		// Uncomment one of the following three lines...
		
		// startRandomOrder();
		startRandomOrderActivatedEachRound();
		// startSameOrder();
	}

	public void startRandomOrder() {
		for (int i = 0; i < 10; i++) {
			// Following executes in random order
			schedule.scheduleRepeating(new SimpleSteppable(i));
		}

		// Gets executed along with the rest
		schedule.scheduleRepeating(new SimpleSteppable(1000));
	}

	public void startRandomOrderActivatedEachRound() {
		for (int i = 0; i < 10; i++) {
			// Following executes in random order, but each get executed each
			// round
			schedule.scheduleRepeating(new SimpleSteppable(i));
		}

		// Gets executed at the end of each round
		schedule.scheduleRepeating(new SimpleSteppable(1000), 1000, 1);
	}

	public void startSameOrder() {
		for (int i = 0; i < 10; i++) {
			// Following executes in same order
			schedule.scheduleRepeating(new SimpleSteppable(i), i, 1);
		}

		// Gets executed at the end of each round
		schedule.scheduleRepeating(new SimpleSteppable(1000), 1000, 1);
	}

	public static void main(String[] args) {
		doLoop(MasonSchedulerExample.class, args);
		System.exit(0);
	}

	public class SimpleSteppable implements Steppable {
		private int i;

		public SimpleSteppable(int i) {
			this.i = i;
		}

		@Override
		public void step(SimState state) {
			System.err.println("step called for " + i);
		}
	}

}
