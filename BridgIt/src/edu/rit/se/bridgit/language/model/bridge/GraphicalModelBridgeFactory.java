package edu.rit.se.bridgit.language.model.bridge;

import java.util.HashMap;
import java.util.Vector;

public class GraphicalModelBridgeFactory
{
	private HashMap<String, GraphicalBridge> _available_classes;
	private HashMap<String, Vector<GraphicalBridge>> _current_instances;
	
	private static GraphicalModelBridgeFactory _instance = null;
	
	private GraphicalModelBridgeFactory()
	{
		_available_classes = new HashMap<String, GraphicalBridge>();
		_current_instances = new HashMap<String, Vector<GraphicalBridge>>();
	}
	
	public static GraphicalModelBridgeFactory getInstance()
	{
		if(_instance != null)
		{
			return _instance;
		}
		else
		{
			_instance = new GraphicalModelBridgeFactory();
			return _instance;
		}
	}
	
	public GraphicalBridge buildBridge(String in_pseudoType)
	{
		//check if that type is available
		if(_available_classes.containsKey(in_pseudoType))
		{
			//get the type from the map of available types
			GraphicalBridge return_val = new GraphicalBridge(_available_classes.get(in_pseudoType));
			
			//check if we have a vector to hold available instances
			if(!_current_instances.containsKey(in_pseudoType))
			{
				//if we don't add a vector for it
				_current_instances.put(in_pseudoType, new Vector<GraphicalBridge>());
			}
			
			//add the new instance to list of current instances
			_current_instances.get(in_pseudoType).add(return_val);
			return return_val;
		}
		else
			return null;
	}
	
	public void addPossibleClass(GraphicalBridge in_gb)
	{
		_available_classes.put(in_gb._pseudoType, in_gb);
	}
	
	public int getNumberInstances(String in_pseudoType)
	{
		return _current_instances.get(in_pseudoType).size();
	}
}
