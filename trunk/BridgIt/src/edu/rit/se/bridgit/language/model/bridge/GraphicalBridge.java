package edu.rit.se.bridgit.language.model.bridge;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;

import edu.rit.se.bridgit.language.model.Type;

public class GraphicalBridge
{
	String pseudoType;
	List<String> availableMethods;
	Queue<Command> actionQueue;
	Node render_node;
	
	public GraphicalBridge(String pseudoType, List<String> methods, Queue<Command> actionQueue, Node in_render_node)
	{
		this.pseudoType = pseudoType;
		this.availableMethods = methods;
		this.actionQueue = actionQueue;

        render_node = in_render_node;
        
        render_node.setModelBound(new BoundingBox());
        render_node.updateModelBound();
        render_node.setLocalTranslation(new Vector3f(0, 0, 0));
        render_node.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
	}
	
	public GraphicalBridge(String pseudoType, List<String> methods, Node in_render_node)
	{
		this(pseudoType, methods, new LinkedList<Command>(), in_render_node);
	}
	
	public GraphicalBridge(GraphicalBridge other) 
	{
		this(other.pseudoType, other.availableMethods, other.actionQueue, other.render_node);
	}
	
	public Object sendMessage(String methodName, List<Type> arguments) throws NoMethodFoundException
	{
		Command com = new Command(methodName, arguments);
		if(!availableMethods.contains(com.getMethodName()))
		{
			throw new NoMethodFoundException(pseudoType, com.getMethodName());
		}
		actionQueue.add(com); //TODO: this should add something real
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
	
	public void executeActionQueue()
	{
		while(actionQueue.size() > 0)
		{
			Command curCommand = actionQueue.poll();
			if(curCommand.getMethodName().equals("setTranslation"))
			{
				this.render_node.setLocalTranslation(Float.parseFloat(curCommand.getArguments().get(0).toString()),
						Float.parseFloat(curCommand.getArguments().get(1).toString()),
						Float.parseFloat(curCommand.getArguments().get(2).toString()));
			}
			else if(curCommand.getMethodName().equals("offsetTranslation"))
			{
				Vector3f oldTranslation = this.render_node.getLocalTranslation();
				this.render_node.setLocalTranslation(oldTranslation.x + Float.parseFloat(curCommand.getArguments().get(0).toString()),
						oldTranslation.y + Float.parseFloat(curCommand.getArguments().get(1).toString()),
						oldTranslation.z + Float.parseFloat(curCommand.getArguments().get(2).toString()));
			}
			else if(curCommand.getMethodName().equals("setScale"))
			{
				this.render_node.setLocalScale(Float.parseFloat(curCommand.getArguments().get(0).toString()));
			}
		}
	}
}
