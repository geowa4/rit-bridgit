package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

import com.ardor3d.bounding.BoundingBox;
import com.ardor3d.renderer.Renderer;
import com.ardor3d.scenegraph.Node;



import edu.rit.se.bridgit.language.bridge.PseudoInstanceBridge;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.NoMethodFoundException;


public class GraphicalBridge implements PseudoInstanceBridge
{
	private String pseudoType;
	private List<String> availableMethods;
	
	public Node render_node;
	private String thumbnail;
	
	public GraphicalBridge(String pseudoType, List<String> methods, Node node)
	{
		this.pseudoType = pseudoType;
		this.availableMethods = methods;

        render_node = node;
        
        render_node.setTranslation(0, 0, 0);
	}
	
	
	public GraphicalBridge(GraphicalBridge other) 
	{
		this(other.pseudoType, other.availableMethods, other.render_node);
	}
	
	@Override
	public Object sendMessage(String methodName, List<Type> arguments, int pseudo_thread) throws NoMethodFoundException
	{
		if(!availableMethods.contains(methodName))
		{
			throw new NoMethodFoundException(pseudoType, methodName);
		}
		Command com = GraphicalModelBridgeFactory.createCommand(methodName, arguments, this);

		GraphicalModelBridgeFactory.addAction(com, pseudo_thread);
		return new Boolean(true);
	}

	@Override
	public Object sendMessage(String methodName, List<Type> arguments) throws NoMethodFoundException
	{
		return sendMessage(methodName, arguments, 0);
	}

	@Override
	public List<String> getAvailableMethods()
	{
		return availableMethods;
	}

	@Override
	public String getPseudoType()
	{
		return pseudoType;
	}

	@Override
	public String getThumbnail()
	{
		if(thumbnail == null || thumbnail.isEmpty())
			return "/Users/student/Desktop/BridgIt_Project/BridgIt/BridgIt/icons/alt_about.gif";
		else
			return thumbnail;
	}
	
	@Override
	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	public Node getGeometry()
	{
		return render_node;
	}

	public void update(float tps)
	{
		
	}

	public boolean executeAction(Command in_action, double delta)
	{
		return in_action.execute(delta);	
	}
	
	public void remove()
	{
		this.render_node.removeFromParent();
	}
}
