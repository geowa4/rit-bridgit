package edu.rit.se.bridgit.language.model.bridge;

import java.util.HashMap;
import java.util.LinkedList;

public class GraphicalModelBridgeFactory
{
	protected static HashMap<String, GraphicalBridge> availableclasses = 
		new HashMap<String, GraphicalBridge>();
	protected static HashMap<String, LinkedList<GraphicalBridge>> currentinstances = 
		new HashMap<String, LinkedList<GraphicalBridge>>();
	
	public static GraphicalBridge buildBridge(String pseudoType)
	{
		if(availableclasses.containsKey(pseudoType))
		{
			GraphicalBridge returnval = new GraphicalBridge(availableclasses.get(pseudoType));
			
			if(!currentinstances.containsKey(pseudoType))
			{
				currentinstances.put(pseudoType, new LinkedList<GraphicalBridge>());
			}
			currentinstances.get(pseudoType).add(returnval);
			return returnval;
		}
		else
			return null;
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
