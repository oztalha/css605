package edu.gmu.toz.week06.assignment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class Week6Test {

	/**
	 * Using only one agent, tests if the correct values are returned for the
	 * agent.
	 */
	@Test
	public void testOneAgentValues() {
		Object agent = "some agent";
		List<Double> agentValues = new ArrayList<Double>(Arrays.asList(1.0,
				2.0, 3.0));

		AgentData agentData = new Week6();

		for (Double x : agentValues) {
			agentData.addValueForAgent(agent, x);
		}

		assertArrayEquals("Agent values not correct", agentValues.toArray(),
				agentData.getValuesForAgent(agent).toArray());
	}

	/**
	 * Using only one agent, tests if the correct list of agents are returned
	 * (size would be 1).
	 */
	@Test
	public void testOneAgentWithValues() {
		Object agent = "some agent";
		List<Double> agentValues = new ArrayList<Double>(Arrays.asList(1.0,
				2.0, 3.0));
		List<Object> agentsWithValues = new ArrayList<Object>(
				Arrays.asList(agent));

		AgentData agentData = new Week6();

		for (Double x : agentValues) {
			agentData.addValueForAgent(agent, x);
		}

		assertArrayEquals("Agents with values not correct",
				agentsWithValues.toArray(), agentData.getAgentsWithValues()
						.toArray());
	}

	/**
	 * Using three agents, tests if the correct values are returned for each
	 * agent.
	 */
	@Test
	public void testThreeAgentsValues() {
		Object agent1 = "agent1";
		Object agent2 = "agent2";
		Object agent3 = "agent3";
		List<Double> agentValues1 = new ArrayList<Double>(Arrays.asList(1.0,
				2.0, 3.0));
		List<Double> agentValues2 = new ArrayList<Double>(Arrays.asList(4.0,
				5.0, 6.0));
		List<Double> agentValues3 = new ArrayList<Double>(Arrays.asList(7.0,
				8.0, 9.0, 10.0));

		AgentData agentData = new Week6();

		for (Double x : agentValues1) {
			agentData.addValueForAgent(agent1, x);
		}
		for (Double x : agentValues2) {
			agentData.addValueForAgent(agent2, x);
		}
		for (Double x : agentValues3) {
			agentData.addValueForAgent(agent3, x);
		}

		assertArrayEquals("Agent1 values not correct", agentValues1.toArray(),
				agentData.getValuesForAgent(agent1).toArray());
		assertArrayEquals("Agent2 values not correct", agentValues2.toArray(),
				agentData.getValuesForAgent(agent2).toArray());
		assertArrayEquals("Agent3 values not correct", agentValues3.toArray(),
				agentData.getValuesForAgent(agent3).toArray());
	}

	/**
	 * Using only three agents, tests if the correct list of agents are returned
	 * (size would be 3).
	 */
	@Test
	public void testThreeAgentsWithValues() {
		Object agent1 = "agent1";
		Object agent2 = "agent2";
		Object agent3 = "agent3";
		List<Double> agentValues1 = new ArrayList<Double>(Arrays.asList(1.0,
				2.0, 3.0));
		List<Double> agentValues2 = new ArrayList<Double>(Arrays.asList(4.0,
				5.0, 6.0));
		List<Double> agentValues3 = new ArrayList<Double>(Arrays.asList(7.0,
				8.0, 9.0, 10.0));
		List<Object> agentsWithValues = new ArrayList<Object>(Arrays.asList(
				agent1, agent2, agent3));

		AgentData agentData = new Week6();

		for (Double x : agentValues1) {
			agentData.addValueForAgent(agent1, x);
		}
		for (Double x : agentValues2) {
			agentData.addValueForAgent(agent2, x);
		}
		for (Double x : agentValues3) {
			agentData.addValueForAgent(agent3, x);
		}
		System.out.println("------");
		assertArrayEquals("Agents with values not correct",
				agentsWithValues.toArray(), agentData.getAgentsWithValues()
						.toArray());
	}
}
