package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

import com.jme.bounding.BoundingBox;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.TriMesh;

import edu.rit.se.bridgit.language.model.Type;


public class GraphicalBridge
{
	private String pseudoType;
	private List<String> availableMethods;
	
	private TriMesh render_node;

	
	public GraphicalBridge(String pseudoType, List<String> methods, TriMesh in_render_node)
	{
		this.pseudoType = pseudoType;
		this.availableMethods = methods;

        render_node = in_render_node;
        
        render_node.setModelBound(new BoundingBox());
        render_node.updateModelBound();
        render_node.setLocalTranslation(new Vector3f(0, 0, 0));
        render_node.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
	}
	
	
	public GraphicalBridge(GraphicalBridge other) 
	{
		this(other.pseudoType, other.availableMethods, other.render_node);
	}
	

	public Object sendMessage(String methodName, List<Type> arguments, int pseudo_thread) throws NoMethodFoundException
	{
		Command com;
		com = new Command(methodName, arguments);
		if(!availableMethods.contains(com.getMethodName()))
		{
			throw new NoMethodFoundException(pseudoType, com.getMethodName());
		}
		GraphicalModelBridgeFactory.addAction(com, this, 0);
		return new Boolean(true);
	}
	
	public Object sendMessage(String methodName, List<Type> arguments) throws NoMethodFoundException
	{
		return sendMessage(methodName,arguments,0);
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
	
	public TriMesh getGeometry()
	{
		return render_node;
	}
	
	public void update(float tps)
	{
		
	}
	
	public boolean executeAction(Command in_action, double delta)
	{
		
		Command curCommand = in_action;
		if(curCommand.getMethodName().equals("setTranslation"))
		{
			this.render_node.setLocalTranslation(Float.parseFloat(curCommand.getArguments().get(0).toString()),
					Float.parseFloat(curCommand.getArguments().get(1).toString()),
					Float.parseFloat(curCommand.getArguments().get(2).toString()));
			return true;
		}
		else if(curCommand.getMethodName().equals("offsetTranslation"))
		{
			Vector3f oldTranslation = this.render_node.getLocalTranslation();
			this.render_node.setLocalTranslation(oldTranslation.x + Float.parseFloat(curCommand.getArguments().get(0).toString()),
					oldTranslation.y + Float.parseFloat(curCommand.getArguments().get(1).toString()),
					oldTranslation.z + Float.parseFloat(curCommand.getArguments().get(2).toString()));
			return true;
		}
		else if(curCommand.getMethodName().equals("setScale"))
		{
			this.render_node.setLocalScale(Float.parseFloat(curCommand.getArguments().get(0).toString()));
			return true;
		}
		else if(curCommand.getMethodName().equals("setRotation"))
		{
			Quaternion q = new Quaternion();
			q.fromAngles(Float.parseFloat(curCommand.getArguments().get(0).toString()),
					Float.parseFloat(curCommand.getArguments().get(1).toString()),
					Float.parseFloat(curCommand.getArguments().get(2).toString()));
			this.render_node.setLocalRotation(q);
		}
		else if(curCommand.getMethodName().equals("moveOverTime"))
		{
			MoveOverTimeCommand motc = (MoveOverTimeCommand)curCommand;
			if(!motc.initialized())
			{
				motc.setTime(Float.parseFloat(motc.getArguments().get(3).toString()));
				motc.setInitialPosition(this.render_node.getLocalTranslation());
			}
			
			this.render_node.setLocalTranslation(motc.startPos.add(motc.update(delta)));
			
			return motc.finished();
		}
		else if(curCommand.getMethodName().equals("scaleOverTime"))
		{
			MoveOverTimeCommand motc = (MoveOverTimeCommand)curCommand;
			if(!motc.initialized())
			{
				motc.setTime(Float.parseFloat(motc.getArguments().get(3).toString()));
			}
			
			this.render_node.setLocalScale(motc.startPos.add(motc.update(delta)));
			
			return motc.finished();
		}
		else if(curCommand.getMethodName().equals("rotateOverTime"))
		{
			MoveOverTimeCommand motc = (MoveOverTimeCommand)curCommand;
			if(!motc.initialized())
			{
				motc.setTime(Float.parseFloat(motc.getArguments().get(3).toString()));
			}
			
			Quaternion q = new Quaternion();
			Vector3f ypr = motc.update(delta);
			q.fromAngles(ypr.x, ypr.y, ypr.z);
			this.render_node.setLocalRotation(q);
			
			return motc.finished();
		}
		else if(curCommand.getMethodName().equals("remove"))
		{
			this.render_node.removeFromParent();
		}
		
		return false;
	}
}
