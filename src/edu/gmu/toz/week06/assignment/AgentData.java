package edu.gmu.toz.week06.assignment;


import java.util.List;

/**
 * Simple interface for storing data about a group of agents.
 * 
 * @author randy
 *
 */
public interface AgentData {

	/**
	 * Stores the double value for the given agent. So, when the
	 * getValuesForAgent method is called, it will return the values that are
	 * associated with the provided agent Object.
	 * 
	 * @param agent
	 * @param x
	 */
	void addValueForAgent(Object agent, double x);

	/**
	 * Returns a list of agent Objects that have values associated with them.
	 * 
	 * @return
	 */
	List<Object> getAgentsWithValues();

	/**
	 * Returns only the values that are associated with the agent input
	 * parameter.
	 * 
	 * @param agent
	 * @return
	 */
	List<Double> getValuesForAgent(Object agent);
}
