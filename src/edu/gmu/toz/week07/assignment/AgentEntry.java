package edu.gmu.toz.week07.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class AgentEntry<A> {

	// The following reference to the agent isn't used now, but could be useful
	// in a future lecture.
	private A agent;

	HashMap<String,List<Double>> fields;

	public AgentEntry(A agent) {
		this.agent = agent;
		this.fields = null;
	}

	public Set<String> getFieldNames(){
		return fields.keySet();
	}
	public List<Double> getFieldValues(String field) {
		if(!fields.containsKey(field))
			return null;
		else
			return fields.get(field);
	}
	
	public boolean hasValue(){
		return fields == null ? false : true ;
	}
	
	public void addFieldValue(String field, double value){
		if(fields == null)
			fields = new LinkedHashMap<String, List<Double>>();
		
		if(fields.containsKey(field)){
			List<Double> alist = fields.get(field);
			alist.add(value);
			fields.replace(field, alist);			
		}else{
			List<Double> alist = new ArrayList<Double>();
			alist.add(value);
			fields.put(field, alist);
		}
	}
}
