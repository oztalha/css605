package edu.gmu.toz.week07.assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Here is your assignment for Week 7:
 * 
 * DUE: Tuesday, October 14th at noon
 *
 * TASK 1: Copy AdvancedAgentData.java, Week7.java and Week7Test.java to your
 * own package following the same pattern as used above (i.e., replace toz
 * with your username).
 * 
 * TASK 2: Implement the methods (addValueForAgent, getAgentsWithValues,
 * getFieldsWithValues, and getValuesForAgent) from the AdvancedAgentData
 * interface in the Week7 class.
 *
 * TASK 3: Make calls to these methods in the main method to make sure they are
 * producing the correct results.
 *
 * TASK 4: Run the unit tests in Week7Test.java to ensure your methods are
 * correct.
 * 
 * TASK 5: Email your version of Week7.java and any other supplementary class
 * files (hint: something like AgentEntry.java from the lecture notes may be
 * helpful) to toz@gmu.edu
 * 
 */
public class Week7<A> implements AdvancedAgentData<A> {

	// Notice that we are using generics this week. For the unit test, any agent
	// class could be used to represent the agents, but for simplicity, I am
	// just using the String class to represent each of the agents.
	
	//map string fields to double values
	//HashMap<String,List<Double>> Fields = new LinkedHashMap<String, List<Double>>();
	
	//map agent to fields
	//HashMap<A, HashMap<String,List<Double>>> agents;
	private Map<A, AgentEntry<A>> agentEntriesMap = new LinkedHashMap<A, AgentEntry<A>>();

	/**
	 * Stores the double value for the given agent and field. So, when the
	 * getValuesForAgent method is called, it will return the list of values
	 * associated with the provided agent and field.
	 * 
	 * @param agent
	 *            the agent that may have multiple attributes that we care about
	 *            storing (fields)
	 * @param field
	 *            the field that is being stored
	 * @param x
	 *            the value that is being stored during this method call
	 */
	@Override
	public void addValueForAgent(A agent, String field, double x) {
		AgentEntry<A> entry = null;
		if(agentEntriesMap.containsKey(agent))
			entry = agentEntriesMap.get(agent);
		else
			entry = new AgentEntry<A>(agent);
		
		entry.addFieldValue(field, x);
		agentEntriesMap.put(agent, entry);
	}

	/**
	 * Returns a list of agent objects of type A that have values associated
	 * with any field.
	 * 
	 * @return
	 */
	@Override
	public List<A> getAgentsWithValues() {
		List <A> awv = new ArrayList<A>();
		for(Map.Entry<A, AgentEntry<A>> entry : agentEntriesMap.entrySet()){
			if(entry.getValue().hasValue())
				awv.add(entry.getKey());
		}
		return Collections.unmodifiableList(awv);
	}

	/**
	 * Returns a list of fields of type String that have values associated with
	 * them.
	 * 
	 * @return
	 */
	@Override
	public List<String> getFieldsWithValues() {

		Set <String> fieldWithValues = new LinkedHashSet<String>();
		for(Map.Entry<A, AgentEntry<A>> entry : agentEntriesMap.entrySet()){
			if(entry.getValue().hasValue())
				fieldWithValues.addAll(entry.getValue().getFieldNames());
		}
		return Collections.unmodifiableList(new ArrayList<String>(fieldWithValues));
	}

	/**
	 * Returns a list of values that are associated with the agent and field
	 * input parameters.
	 * 
	 * @param agent
	 *            the agent associated with the list of values returned
	 * @param field
	 *            the field associated with the list of values returned
	 * @return
	 * 	if a field is not defined for an agent then return null, otherwise return values
	 */
	@Override
	public List<Double> getValuesForAgent(A agent, String field) {
		if(!agentEntriesMap.get(agent).getFieldNames().contains(field))
			return null;
		return Collections.unmodifiableList(agentEntriesMap.get(agent).getFieldValues(field));
	}

	public static void main(String[] args) {

		String agent1 = "agent1";
		String agent2 = "agent2";
		
		String field1 = "sugar";
		String field2 = "spice";
		
		List<Double> agent1Field1Values = new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0));
		List<Double> agent1Field2Values = new ArrayList<Double>(Arrays.asList(22.0, 23.0, 24.0));
		List<Double> agent2Field1Values = new ArrayList<Double>(Arrays.asList(21.0, 22.0, 23.0));
		List<Double> agent2Field2Values = new ArrayList<Double>(Arrays.asList(11.0, 12.0, 13.0));

		AdvancedAgentData<String> agentData = new Week7<String>();
		
		for (Double x : agent1Field1Values) {
			agentData.addValueForAgent(agent1, field1, x);
		}
		for (Double x : agent2Field1Values) {
			agentData.addValueForAgent(agent2, field1, x);
		}
		
		System.out.println(agentData.getValuesForAgent(agent1, field1));
		System.out.println(agentData.getValuesForAgent(agent2, field1));
		
		//if a field is not defined for an agent then return null
		System.out.println(agentData.getValuesForAgent(agent1, field2));
		
		for (Double x : agent1Field2Values) {
			agentData.addValueForAgent(agent1, field2, x);
		}
		for (Double x : agent2Field2Values) {
			agentData.addValueForAgent(agent2, field2, x);
		}
		
		System.out.println(agentData.getValuesForAgent(agent1, field2));
		System.out.println(agentData.getValuesForAgent(agent2, field2));
	}
}
