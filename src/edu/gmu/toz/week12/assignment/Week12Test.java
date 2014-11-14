package edu.gmu.toz.week12.assignment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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
public class Week12Test {

	private int NUM_AGENTS = 100;

	private Week12 schedule;

	private List<Agent> agents;

	private List<Agent> executionOrder;

	@Before
	public void setup() {
		schedule = new Week12();
		agents = new ArrayList<Agent>();
		executionOrder = new ArrayList<Agent>();

		for (int i = 0; i < NUM_AGENTS; i++) {
			Agent a = new SimpleAgent(i);
			agents.add(a);
			schedule.schedule(a);
		}
	}

	@Test
	public void testSameOrder() {

		for (int i = 0; i < NUM_AGENTS; i++) {
			schedule.stepSameOrder();
		}

		assertEquals("Incorrect number of agents activated: ", NUM_AGENTS,
				executionOrder.size());

		assertArrayEquals("Order should be the same as added to schedule",
				agents.toArray(new Agent[NUM_AGENTS]),
				executionOrder.toArray(new Agent[NUM_AGENTS]));
	}

	/**
	 * Using only one agent and one field, tests if the correct values are
	 * returned for the agent.
	 */
	@Test
	public void testRandomOrder() {

		for (int i = 0; i < NUM_AGENTS; i++) {
			schedule.stepRandomOrder();
		}

		assertEquals("Incorrect number of agents activated: ", NUM_AGENTS,
				executionOrder.size());

		boolean orderEquals = Arrays.equals(
				agents.toArray(new Agent[NUM_AGENTS]),
				executionOrder.toArray(new Agent[NUM_AGENTS]));

		assertFalse("Order should be random", orderEquals);
	}

	public class SimpleAgent implements Agent {

		private int agentNum;

		public SimpleAgent(int agentNum) {
			this.agentNum = agentNum;
		}

		@Override
		public void step() {
			executionOrder.add(this);
		}
	}

}
