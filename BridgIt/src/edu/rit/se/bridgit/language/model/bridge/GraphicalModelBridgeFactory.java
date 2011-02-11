package edu.rit.se.bridgit.language.model.bridge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

public class GraphicalModelBridgeFactory
{
	protected static HashMap<String, GraphicalBridge> availableclasses = 
		new HashMap<String, GraphicalBridge>();;
	static 
	{
		loadAvailableClasses();
	}
	
	protected static HashMap<String, Vector<GraphicalBridge>> currentinstances = 
		new HashMap<String, Vector<GraphicalBridge>>();
	
	public static GraphicalBridge buildBridge(String pseudoType)
	{
		if(availableclasses.containsKey(pseudoType))
		{
			GraphicalBridge returnval = new GraphicalBridge(availableclasses.get(pseudoType));
			
			if(!currentinstances.containsKey(pseudoType))
			{
				currentinstances.put(pseudoType, new Vector<GraphicalBridge>());
			}
			currentinstances.get(pseudoType).add(returnval);
			return returnval;
		}
		else
			return null;
	}
	
	private static void loadAvailableClasses()
	{
		//TODO this cannot stay like this. we need to actually load something.
		availableclasses.put("Ballerina", new GraphicalBridge("Ballerina", 
				new Vector<String>() 
				{
					private static final long serialVersionUID = 176187087612072108L;
				{
					add("jump");
				}}, 
				new LinkedList<Object>()));
	}

	public static Set<String> getAvailableClasses()
	{
		return availableclasses.keySet();
	}
	
	public static void addPossibleClass(GraphicalBridge gb)
	{
		availableclasses.put(gb.pseudoType, gb);
	}
	
	public static int getNumberInstances(String inpseudoType)
	{
		return currentinstances.get(inpseudoType).size();
	}
}
