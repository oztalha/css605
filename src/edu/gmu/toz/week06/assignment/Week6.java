package edu.gmu.toz.week06.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Here is your assignment for Week 6:
 * 
 * DUE: Tuesday, October 7th at noon
 *
 * TASK 1: Copy AgentData.java, Week6.java and Week6Test.java to your own
 * package following the same pattern as used above (i.e., replace toz with
 * your username).
 * 
 * TASK 2: Implement the methods (addValueForAgent, getAgentsWithValues, and
 * getValuesForAgent) from the AgentData interface in the Week6 class.
 *
 * TASK 3: Make calls to these methods in the main method to make sure they are
 * producing the correct results.
 *
 * TASK 4: Run the unit tests in Week6Test.java to ensure your methods are
 * correct.
 * 
 * TASK 5: Email your version of Week6.java to toz@gmu.edu
 * 
 */
public class Week6 implements AgentData {

	//data structure interface to keep agent data
	HashMap<Object, List<Double>> agents;
	
	Week6(){
		// internal data structure is a linked hash map
		// because it keeps the insertion order (otherwise test three fails)
		agents = new LinkedHashMap<Object, List<Double>>();
	}
	
	/**
	 * Stores the double value for the given agent. So, when the
	 * getValuesForAgent method is called, it will return the values that are
	 * associated with the provided agent Object.
	 * 
	 * @param agent
	 * @param x
	 */
	@Override
	public void addValueForAgent(Object agent, double x) {
		if(agents.containsKey(agent)){
			List<Double> alist = agents.get(agent);
			alist.add(x);
			agents.replace(x, alist);			
		}else{
			List<Double> alist = new ArrayList<Double>();
			alist.add(x);
			agents.put(agent, alist);
		}
	}

	/**
	 * Returns a list of agent Objects that have values associated with them.
	 * 
	 * @return
	 */
	@Override
	public List<Object> getAgentsWithValues() {

		return (List<Object>) new ArrayList<Object>(agents.keySet());
	}

	/**
	 * Returns only the values that are associated with the agent input
	 * parameter.
	 * 
	 * @param agent
	 * @return
	 */
	@Override
	public List<Double> getValuesForAgent(Object agent) {

		return agents.get(agent);
	}

	public static void main(String[] args) {
		Object agent1 = "my first agent";
		Object agent2 = "my second agent";
		
		AgentData agentData = new Week6();
		
		agentData.addValueForAgent(agent1, 1.0);
		System.out.println(agentData.getValuesForAgent(agent1));
		agentData.addValueForAgent(agent1, 2.0);
		System.out.println(agentData.getValuesForAgent(agent1));

		System.out.println(agentData.getAgentsWithValues());
		agentData.addValueForAgent(agent2, 3.0);
		agentData.addValueForAgent(agent2, 4.0);
		System.out.println(agentData.getAgentsWithValues());
		
	}
}
