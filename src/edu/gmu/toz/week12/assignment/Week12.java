package edu.gmu.toz.week12.assignment;

import java.util.ArrayList;
import java.util.List;

import ec.util.MersenneTwisterFast;
import edu.gmu.toz.week12.assignment.Week12Test.SimpleAgent;

/**
 * Here is your assignment for Week 12:
 * 
 * DUE: Tuesday, November 18th at noon
 *
 * TASK 1: Copy Agent.java, Schedule.java, Week12.java and Week12Test.java to
 * your own package following the same pattern as used above (i.e., replace
 * rcasstev with your username).
 * 
 * TASK 2: Implement the methods (schedule, stepSameOrder, and stepRandomOrder)
 * from the Schedule interface in the Week12 class.
 *
 * TASK 3: Make calls to these methods in the main method to make sure they are
 * producing the correct results.
 *
 * TASK 4: Run the unit tests in Week12Test.java to ensure your methods are
 * correct.
 * 
 * TASK 5: Email your version of Week12.java to rcasstev@gmu.edu
 * 
 */
public class Week12 implements Schedule {

	MersenneTwisterFast random = new MersenneTwisterFast();
	protected List<Agent> queue;
	int lastStepped = 0;
	boolean isShuffled = false;
	protected List<Agent> shuffledQueue;
	/**
	 * Schedule the Agent for activation in the future.
	 * 
	 */
	@Override
	public void schedule(Agent a) {
		if (queue==null)
			queue = new ArrayList<Agent>();
		//append agent
		queue.add(a);
	}

	/**
	 * Steps through the agents in the same order as they were added to the
	 * Schedule.
	 * 
	 */
	@Override
	public void stepSameOrder() {
		if (lastStepped==queue.size())
			lastStepped = 0;
		queue.get(lastStepped++).step();
	}

	/**
	 * Steps through the agents in an random order.
	 * 
	 */
	@Override
	public void stepRandomOrder() {
		
		if(lastStepped==queue.size()){
			lastStepped = 0;
			isShuffled = false;
		}
		if(!isShuffled){
			if(shuffledQueue==null)
				shuffledQueue = new ArrayList<Agent>(queue);
			for(int i=0; i<1000; i++){
				int ind1 = random.nextInt(shuffledQueue.size());
				Agent temp = shuffledQueue.get(ind1);
				int ind2 = random.nextInt(shuffledQueue.size());
				shuffledQueue.set(ind1, shuffledQueue.get(ind2));
				shuffledQueue.set(ind2, temp);
			}
			isShuffled = true;
		}
		shuffledQueue.get(lastStepped++).step();
	}
	
	public static void main(String[] args) {
		int NUM_AGENTS = 10;
		Week12 schedule = new Week12();
		List<Agent> agents = new ArrayList<Agent>();
		
		class SimpleAgent implements Agent {

			private int agentNum;

			public SimpleAgent(int agentNum) {
				this.agentNum = agentNum;
			}

			@Override
			public void step() {
				System.out.println("Agent "+agentNum+" stepped in.");
			}
		}
		
		for (int i = 0; i < NUM_AGENTS; i++) {
			Agent a = new SimpleAgent(i);
			agents.add(a);
			schedule.schedule(a);
		}
		System.out.println("First in order:");
		for (int i = 0; i < NUM_AGENTS; i++) {
			schedule.stepSameOrder();
		}
		System.out.println("Second in order:");
		for (int i = 0; i < NUM_AGENTS; i++) {
			schedule.stepSameOrder();
		}		
		System.out.println("First random:");
		for (int i = 0; i < NUM_AGENTS; i++) {
			schedule.stepRandomOrder();
		}
		System.out.println("Second random:");
		for (int i = 0; i < NUM_AGENTS; i++) {
			schedule.stepRandomOrder();
		}
	}
	


}
