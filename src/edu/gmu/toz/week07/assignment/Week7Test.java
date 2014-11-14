package edu.gmu.toz.week07.assignment;

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
public class Week7Test {

	// NOTE: I am just using a String to represent an agent, but since we are
	// using generics, we could use any object to represent our agents.

	/**
	 * Using only one agent and one field, tests if the correct values are
	 * returned for the agent.
	 */
	@Test
	public void testOneAgentOneFieldValues() {
		String agent = "some agent";
		String field = "some field";

		List<Double> agentValues = new ArrayList<Double>(Arrays.asList(1.0,
				2.0, 3.0));

		AdvancedAgentData<String> agentData = new Week7<String>();

		for (Double x : agentValues) {
			agentData.addValueForAgent(agent, field, x);
		}

		assertArrayEquals("Agent values not correct", agentValues.toArray(),
				agentData.getValuesForAgent(agent, field).toArray());
	}

	/**
	 * Using only one agent and one field, tests if the correct list of agents
	 * are returned (size would be 1).
	 */
	@Test
	public void testOneFieldOneAgentWithValues() {
		String agent = "some agent";
		String field = "some field";

		List<Double> agentValues = new ArrayList<Double>(Arrays.asList(1.0,
				2.0, 3.0));

		List<String> agentsWithValues = new ArrayList<String>(
				Arrays.asList(agent));

		AdvancedAgentData<String> agentData = new Week7<String>();

		for (Double x : agentValues) {
			agentData.addValueForAgent(agent, field, x);
		}

		assertArrayEquals("Agents with values not correct",
				agentsWithValues.toArray(), agentData.getAgentsWithValues()
						.toArray());
	}

	/**
	 * Using only one agent and one field, tests if the correct list of fields
	 * are returned (size would be 1).
	 */
	@Test
	public void testOneAgentOneFieldWithValues() {
		String agent = "some agent";
		String field = "some field";

		List<Double> agentValues = new ArrayList<Double>(Arrays.asList(1.0,
				2.0, 3.0));

		List<String> fieldsWithValues = new ArrayList<String>(
				Arrays.asList(field));

		AdvancedAgentData<String> agentData = new Week7<String>();

		for (Double x : agentValues) {
			agentData.addValueForAgent(agent, field, x);
		}

		assertArrayEquals("Fields with values not correct",
				fieldsWithValues.toArray(), agentData.getFieldsWithValues()
						.toArray());
	}

	/**
	 * Using three agents and two fields, tests if the correct values are
	 * returned for each agent.
	 */
	@Test
	public void testThreeAgentsTwoFieldsValues() {
		String agent1 = "agent1";
		String agent2 = "agent2";
		String agent3 = "agent3";
		String field1 = "sugar";
		String field2 = "spice";
		List<Double> agent1Field1Values = new ArrayList<Double>(Arrays.asList(
				1.0, 2.0, 3.0));
		List<Double> agent2Field1Values = new ArrayList<Double>(Arrays.asList(
				4.0, 5.0, 6.0));
		List<Double> agent3Field1Values = new ArrayList<Double>(Arrays.asList(
				7.0, 8.0, 9.0, 10.0));

		List<Double> agent1Field2Values = new ArrayList<Double>(Arrays.asList(
				100.0, 200.0, 300.0));
		List<Double> agent2Field2Values = new ArrayList<Double>(Arrays.asList(
				400.0, 500.0, 600.0));
		List<Double> agent3Field2Values = new ArrayList<Double>(Arrays.asList(
				700.0, 800.0));

		AdvancedAgentData<String> agentData = new Week7<String>();

		// Add the field 1 values
		for (Double x : agent1Field1Values) {
			agentData.addValueForAgent(agent1, field1, x);
		}
		for (Double x : agent2Field1Values) {
			agentData.addValueForAgent(agent2, field1, x);
		}
		for (Double x : agent3Field1Values) {
			agentData.addValueForAgent(agent3, field1, x);
		}

		// Add the field 2 values
		for (Double x : agent1Field2Values) {
			agentData.addValueForAgent(agent1, field2, x);
		}
		for (Double x : agent2Field2Values) {
			agentData.addValueForAgent(agent2, field2, x);
		}
		for (Double x : agent3Field2Values) {
			agentData.addValueForAgent(agent3, field2, x);
		}

		// Test to see if field 1 returns the correct values
		assertArrayEquals("Agent1 values not correct for field1",
				agent1Field1Values.toArray(),
				agentData.getValuesForAgent(agent1, field1).toArray());
		assertArrayEquals("Agent2 values not correct for field1",
				agent2Field1Values.toArray(),
				agentData.getValuesForAgent(agent2, field1).toArray());
		assertArrayEquals("Agent3 values not correct for field1",
				agent3Field1Values.toArray(),
				agentData.getValuesForAgent(agent3, field1).toArray());

		// Test to see if field 2 returns the correct values
		assertArrayEquals("Agent1 values not correct for field2",
				agent1Field2Values.toArray(),
				agentData.getValuesForAgent(agent1, field2).toArray());
		assertArrayEquals("Agent2 values not correct for field2",
				agent2Field2Values.toArray(),
				agentData.getValuesForAgent(agent2, field2).toArray());
		assertArrayEquals("Agent3 values not correct for field2",
				agent3Field2Values.toArray(),
				agentData.getValuesForAgent(agent3, field2).toArray());

	}

	/**
	 * Using three agents and two fields, tests if the correct list of agents
	 * are returned (size would be 3).
	 */
	@Test
	public void testTwoFieldsThreeAgentsWithValues() {
		String agent1 = "agent1";
		String agent2 = "agent2";
		String agent3 = "agent3";
		String field1 = "sugar";
		String field2 = "spice";
		List<Double> agent1Field1Values = new ArrayList<Double>(Arrays.asList(
				1.0, 2.0, 3.0));
		List<Double> agent2Field1Values = new ArrayList<Double>(Arrays.asList(
				4.0, 5.0, 6.0));
		List<Double> agent3Field1Values = new ArrayList<Double>(Arrays.asList(
				7.0, 8.0, 9.0, 10.0));

		List<Double> agent1Field2Values = new ArrayList<Double>(Arrays.asList(
				100.0, 200.0, 300.0));
		List<Double> agent2Field2Values = new ArrayList<Double>(Arrays.asList(
				400.0, 500.0, 600.0));
		List<Double> agent3Field2Values = new ArrayList<Double>(Arrays.asList(
				700.0, 800.0));

		List<String> agentsWithValues = new ArrayList<String>(Arrays.asList(
				agent1, agent2, agent3));

		AdvancedAgentData<String> agentData = new Week7<String>();

		// Add the field 1 values
		for (Double x : agent1Field1Values) {
			agentData.addValueForAgent(agent1, field1, x);
		}
		for (Double x : agent2Field1Values) {
			agentData.addValueForAgent(agent2, field1, x);
		}
		for (Double x : agent3Field1Values) {
			agentData.addValueForAgent(agent3, field1, x);
		}

		// Add the field 2 values
		for (Double x : agent1Field2Values) {
			agentData.addValueForAgent(agent1, field2, x);
		}
		for (Double x : agent2Field2Values) {
			agentData.addValueForAgent(agent2, field2, x);
		}
		for (Double x : agent3Field2Values) {
			agentData.addValueForAgent(agent3, field2, x);
		}

		assertArrayEquals("Agents with values not correct",
				agentsWithValues.toArray(), agentData.getAgentsWithValues()
						.toArray());
	}

	/**
	 * Using three agents and two fields, tests if the correct list of fields
	 * are returned (size would be 3).
	 */
	@Test
	public void testThreeAgentsTwoFieldsWithValues() {
		String agent1 = "agent1";
		String agent2 = "agent2";
		String agent3 = "agent3";
		String field1 = "sugar";
		String field2 = "spice";
		List<Double> agent1Field1Values = new ArrayList<Double>(Arrays.asList(
				1.0, 2.0, 3.0));
		List<Double> agent2Field1Values = new ArrayList<Double>(Arrays.asList(
				4.0, 5.0, 6.0));
		List<Double> agent3Field1Values = new ArrayList<Double>(Arrays.asList(
				7.0, 8.0, 9.0, 10.0));

		List<Double> agent1Field2Values = new ArrayList<Double>(Arrays.asList(
				100.0, 200.0, 300.0));
		List<Double> agent2Field2Values = new ArrayList<Double>(Arrays.asList(
				400.0, 500.0, 600.0));
		List<Double> agent3Field2Values = new ArrayList<Double>(Arrays.asList(
				700.0, 800.0));

		List<String> fieldsWithValues = new ArrayList<String>(Arrays.asList(
				field1, field2));

		AdvancedAgentData<String> agentData = new Week7<String>();

		// Add the field 1 values
		for (Double x : agent1Field1Values) {
			agentData.addValueForAgent(agent1, field1, x);
		}
		for (Double x : agent2Field1Values) {
			agentData.addValueForAgent(agent2, field1, x);
		}
		for (Double x : agent3Field1Values) {
			agentData.addValueForAgent(agent3, field1, x);
		}

		// Add the field 2 values
		for (Double x : agent1Field2Values) {
			agentData.addValueForAgent(agent1, field2, x);
		}
		for (Double x : agent2Field2Values) {
			agentData.addValueForAgent(agent2, field2, x);
		}
		for (Double x : agent3Field2Values) {
			agentData.addValueForAgent(agent3, field2, x);
		}

		assertArrayEquals("Fields with values not correct",
				fieldsWithValues.toArray(), agentData.getFieldsWithValues()
						.toArray());
	}
}
