package edu.rit.se.bridgit.language.model.bridge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.ardor3d.extension.model.collada.jdom.ColladaImporter;
import com.ardor3d.extension.model.collada.jdom.data.ColladaStorage;
import com.ardor3d.util.resource.SimpleResourceLocator;

import edu.rit.se.bridgit.ardor3d.NavigationView;
import edu.rit.se.bridgit.edit.content.ContentLoadedListener;
import edu.rit.se.bridgit.language.bridge.PseudoBridge;
import edu.rit.se.bridgit.language.bridge.PseudoInstanceBridge;
import edu.rit.se.bridgit.language.model.Type;

public class GraphicalModelBridgeFactory implements PseudoBridge
{	
	private static Collection<ContentLoadedListener> contentLoadedListeners = new LinkedList<ContentLoadedListener>();
	
	private static HashMap<String, GraphicalBridge> availableclasses = 
		new HashMap<String, GraphicalBridge>();
	
	private static HashMap<String, LinkedList<GraphicalBridge>> currentinstances = 
		new HashMap<String, LinkedList<GraphicalBridge>>();

	//private static RenderCanvas jmeCanvas = new RenderCanvas();

	
	private static Vector<Queue<Command>> actionQueue = new Vector<Queue<Command>>();
	
	//static final FormatConverter CONVERTER_3DS = new MaxToJme();

	enum CommandType
	{
		Standard, MoveOverTime
	};
	
	private static HashMap<String, Command> commandMap = new HashMap<String, Command>();
	
	
	public static void addAction(Command in_command, int pseudoThread)
	{
		while(actionQueue.size() - 1 < pseudoThread)
		{
			actionQueue.add(new LinkedList<Command>());
		}
		actionQueue.get(pseudoThread).add(in_command);
	}
	
	public static void update(double delta)
	{
		for(int i = 0; i < actionQueue.size(); i++)
		{
			Command curCommand = actionQueue.get(i).peek();
			if(curCommand.bridge.executeAction(curCommand, delta))
			{
				actionQueue.get(i).poll();
			}
		}
	}

	public static GraphicalBridge buildGraphicalBridge(String pseudoType)
	{
		if(availableclasses.containsKey(pseudoType))
		{
			GraphicalBridge returnval = new GraphicalBridge(availableclasses.get(pseudoType));
			
			if(!currentinstances.containsKey(pseudoType))
			{
				currentinstances.put(pseudoType, new LinkedList<GraphicalBridge>());
			}
			currentinstances.get(pseudoType).add(returnval);
			
			//jmeCanvas.addNode(returnval.getGeometry());
			return returnval;
		}
		else return null; 
	}
	
	@Override
	public PseudoInstanceBridge buildInstanceBridge(String pseudoType)
	{
		return buildGraphicalBridge(pseudoType);
	}
	
	public static void loadContent() throws URISyntaxException, IOException
	{
		
		File content_folder = new File(System.getProperty("user.home") + File.separator + "Dropbox" + File.separator + "Models");

		if(content_folder.exists())
		{
			loadModels(content_folder);
		}
		else
		{
			throw new FileNotFoundException("Content Directory could not be found:\nShould be located at:\n" + content_folder);
		}
		fireContentLoaded();
		
		//load the command types
		
	}
	
	private static void loadModels(File in_current_folder) throws URISyntaxException, IOException
	{
		File[] list_of_files = in_current_folder.listFiles();
		for(int i = 0; i < list_of_files.length; i++ )
		{
			if(!list_of_files[i].isHidden())
			{
				if(list_of_files[i].isFile())
				{
					String name = list_of_files[i].getName();
					if(name.endsWith(".dae"))
					{
						String class_name = name.substring(0, name.indexOf('.'));
						List<String> methods = new LinkedList<String>();
						methods.add("setTranslation");
						methods.add("offsetTranslation");
						methods.add("setScale");
						methods.add("moveOverTime");
						methods.add("scaleOvertime");
						methods.add("remove");
						ColladaStorage cs = loadCollada(class_name, list_of_files[i]);
						cs.hashCode();
						//availableclasses.put(class_name, new GraphicalBridge(class_name, methods, loadCollada(list_of_files[i])));
					}
				}
				else if(list_of_files[i].isDirectory())
				{
					loadModels(list_of_files[i]);
				}
			}
		}
	}
	
	private static ColladaStorage loadCollada(String name, File model) 
	throws URISyntaxException, IOException
	{
		ColladaImporter importer = new ColladaImporter();
		importer.setModelLocator(
				new SimpleResourceLocator(model.getParentFile().toURI()));
		importer.setTextureLocator(
				new SimpleResourceLocator(new URI(model.getParentFile().getParent() + 
						"/images/")));
		return importer.load(model.getName());
	}
	
	public static Collection<String> getAvailableClasses()
	{
		List<String> l = new ArrayList<String>(availableclasses.keySet());
		Collections.sort(l);
		return l;
	}
	
	public static void addPossibleClass(GraphicalBridge gb)
	{
		availableclasses.put(gb.getPseudoType(), gb);
	}
	
	public static int getNumberInstances(String inpseudoType)
	{
		return currentinstances.get(inpseudoType).size();
	}
	
	public static String getThumbnailForType(String type)
	{
		return ((GraphicalBridge) availableclasses.get(type)).getThumbnail();
	}

	public static void addContentLoadedListener(ContentLoadedListener contentListener)
	{
		contentLoadedListeners.add(contentListener);
	}
	
	private static void fireContentLoaded()
	{
		for(ContentLoadedListener cll : contentLoadedListeners)
		{
			cll.contentLoaded(getAvailableClasses());
		}
	}

	@Override
	public String getUserInput(String prompt)
	{
		IWorkbenchWindow workbench = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		InputDialog dialog = new InputDialog(workbench.getShell(), "The application requests your input", 
				prompt, "", null);
		dialog.open();
		String value = dialog.getValue() == null ? "" : dialog.getValue();
		return value;
	}

	public static Command createCommand(String methodName,
			List<Type> arguments, GraphicalBridge graphicalBridge) 
	{
		Command retCommand = commandMap.get(methodName);
		retCommand.setArguments(arguments);
		retCommand.setMethodName(methodName);
		retCommand.setBridge(graphicalBridge);
		return retCommand;
		
	}
	
	public static IViewPart getNavView()
	{
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(NavigationView.ID);
	}
}
