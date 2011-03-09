package edu.rit.se.bridgit.language.model.bridge;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;

public class GraphicalBridge
{
	String pseudoType;
	List<String> availableMethods;
	Queue<?> actionQueue;
	Node render_node;
	
	public GraphicalBridge(String pseudoType, List<String> methods, Queue<?> actionQueue, Node in_render_node)
	{
		this.pseudoType = pseudoType;
		this.availableMethods = methods;
		this.actionQueue = actionQueue;

        render_node = in_render_node;
        
        render_node.setModelBound(new BoundingBox());
        render_node.updateModelBound();
        render_node.setLocalTranslation(new Vector3f(0, 0, -10));
        render_node.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
	}
	
	public GraphicalBridge(String pseudoType, List<String> methods, Node in_render_node)
	{
		this(pseudoType, methods, new LinkedList<String>(), in_render_node);
	}
	
	public GraphicalBridge(GraphicalBridge other) 
	{
		this(other.pseudoType, other.availableMethods, other.actionQueue, other.render_node);
	}
	
	public Object sendMessage(String methodName) throws NoMethodFoundException
	{
		if(!availableMethods.contains(methodName))
		{
			throw new NoMethodFoundException(pseudoType, methodName);
		}
		actionQueue.add(null); //TODO: this should add something real
		return new Boolean(true); //TODO: must return something real
	}
	
	public List<String> getAvailableMethods()
	{
		return availableMethods;
	}
	
	public String getPseudoType()
	{
		return pseudoType;
	}
	
	public String getThumbnail()
	{
		return "/Users/student/Desktop/BridgIt_Project/BridgIt/BridgIt/icons/alt_about.gif";
	}
	
	public Node getGeometry()
	{
		return render_node;
	}
}
