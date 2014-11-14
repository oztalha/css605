package edu.gmu.toz.week07.assignment;

import java.util.List;

/**
 * Interface for storing data about a group of agents. Each agent of type A
 * (using generics here), can have double values for a variety of fields. So,
 * when a value is added for a specified field, then add it to the agent's list
 * of values for that field. Therefore, there will be a list of doubles
 * associated with each agent/field pair.
 * 
 * @author randy
 *
 */
public interface AdvancedAgentData<A> {

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
	void addValueForAgent(A agent, String field, double x);

	/**
	 * Returns a list of agent objects of type A that have values associated
	 * with any field.
	 * 
	 * @return
	 */
	List<A> getAgentsWithValues();

	/**
	 * Returns a list of fields of type String that have values associated with
	 * them.
	 * 
	 * @return
	 */
	List<String> getFieldsWithValues();

	/**
	 * Returns a list of values that are associated with the agent and field
	 * input parameters.
	 * 
	 * @param agent
	 *            the agent associated with the list of values returned
	 * @param field
	 *            the field associated with the list of values returned
	 * @return
	 */
	List<Double> getValuesForAgent(A agent, String field);
}
