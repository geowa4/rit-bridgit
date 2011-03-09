package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;

import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.converters.FormatConverter;
import com.jmex.model.converters.ObjToJme;

import edu.rit.se.bridgit.monklypse.RenderCanvas;

public class GraphicalModelBridgeFactory
{
	public static HashMap<String, GraphicalBridge> availableclasses = 
		new HashMap<String, GraphicalBridge>();
	
	//public static RenderCanvas jme_canvas = new RenderCanvas();
	
	
	
	public static HashMap<String, Vector<GraphicalBridge>> currentinstances = 
		new HashMap<String, Vector<GraphicalBridge>>();

	public static RenderCanvas jme_canvas = new RenderCanvas();
	
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
			
			jme_canvas.addNode(returnval.getGeometry());
			return returnval;
		}
		else
			return null;
	}
	
	public static void loadAvailableClasses() throws FileNotFoundException
	{
		
		File content_folder = new File(System.getProperty("user.home") + File.separator + "Models");

		if(content_folder.exists())
		{
			loadModels(content_folder);
		}
		else
		{
			throw new FileNotFoundException("Content Directory could not be found:\nShould be located at:\n" + content_folder);
		}
	}
	
	private static void loadModels(File in_current_folder)
	{
		File[] list_of_files = in_current_folder.listFiles();
		for(int i = 0; i < list_of_files.length; i++ )
		{
			if(!list_of_files[i].isHidden())
			{
				if(list_of_files[i].isFile())
				{
					String name = list_of_files[i].getName();
					if(name.contains(".obj"))
					{
						String class_name = list_of_files[i].getName();
						List<String> methods = new LinkedList<String>();
						methods.add("jump");
						methods.add("fly");
						availableclasses.put(class_name, new GraphicalBridge(class_name, methods, loadObjModel(list_of_files[i])));
					}
				}
				else if(list_of_files[i].isDirectory())
				{
					loadModels(list_of_files[i]);
				}
			}
		}
	}
	
	private static Node loadObjModel(File in_model_file)
	{
		URL model_url = null;
		Node model_node = null;
		try 
		{
			model_url = in_model_file.toURI().toURL();
			
			// Create something to convert .obj format to .jme
			FormatConverter converter = new ObjToJme();
			
			// Point the converter to where it will find the .mtl file from
			converter.setProperty("mtllib", model_url);
			
			// This byte array will hold my .jme file
			ByteArrayOutputStream BO = new ByteArrayOutputStream();
		
			// Use the format converter to convert .obj to .jme
			InputStream i_stream = model_url.openStream();
			converter.convert(i_stream, BO);
			model_node = (Node) BinaryImporter.getInstance().load(
				new ByteArrayInputStream(BO.toByteArray()));
	
			return model_node;
		} 
		catch (MalformedURLException e) 
		{
			System.err.println("Could not open file:" + in_model_file.getName());

		}
		catch (IOException e) 
		{ 
			System.err.println("Error converting from obj to jme" + model_url.getFile());
		}
		catch (Exception e)
		{
			System.err.println("Error in model loading" + model_url.getFile());
			e.printStackTrace();
		}
		
	
		return null;
		
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
	
	public static String getThumbnailForType(String type)
	{
		return ((GraphicalBridge) availableclasses.get(type)).getThumbnail();
	}
}
