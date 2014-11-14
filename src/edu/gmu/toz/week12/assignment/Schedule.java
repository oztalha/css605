package edu.gmu.toz.week12.assignment;

/**
 * Simple schedule interface that is somewhat similar to one that is found in
 * the Mason simulation library.
 * 
 * @author randy
 *
 */
public interface Schedule {

	public void schedule(Agent a);

	public void stepSameOrder();

	public void stepRandomOrder();

}
