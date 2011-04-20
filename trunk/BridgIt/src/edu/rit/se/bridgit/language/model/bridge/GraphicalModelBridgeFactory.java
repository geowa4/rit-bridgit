package edu.rit.se.bridgit.language.model.bridge;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.system.DisplaySystem;
import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.resource.ResourceLocatorTool;
import com.jme.util.resource.SimpleResourceLocator;
import com.jmex.model.converters.FormatConverter;
import com.jmex.model.converters.MaxToJme;
import com.jmex.model.converters.ObjToJme;

import edu.rit.se.bridgit.edit.content.ContentLoadedListener;
import edu.rit.se.bridgit.monklypse.RenderCanvas;

public class GraphicalModelBridgeFactory
{	
	private static Collection<ContentLoadedListener> contentLoadedListeners = new LinkedList<ContentLoadedListener>();
	
	private static HashMap<String, GraphicalBridge> availableclasses = 
		new HashMap<String, GraphicalBridge>();
	
	private static HashMap<String, Vector<GraphicalBridge>> currentinstances = 
		new HashMap<String, Vector<GraphicalBridge>>();

	private static RenderCanvas jmeCanvas = new RenderCanvas();
	
	static final FormatConverter CONVERTER_3DS = new MaxToJme();

	
	public static RenderCanvas getJmeCanvas()
	{
		return jmeCanvas;
	}

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
			
			jmeCanvas.addNode(returnval.getGeometry());
			return returnval;
		}
		else return null; 
	}
	
	public static void loadContent() throws FileNotFoundException
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
						String class_name = name.substring(0,name.indexOf('.'));
						List<String> methods = new LinkedList<String>();
						methods.add("setTranslation");
						methods.add("offsetTranslation");
						methods.add("setScale");
						availableclasses.put(class_name, new GraphicalBridge(class_name, methods, loadObjModel(list_of_files[i])));
					}
					if(name.contains(".dae"))
					{
						
					}
				}
				else if(list_of_files[i].isDirectory())
				{
					loadModels(list_of_files[i]);
				}
			}
		}
	}
	
	private static TriMesh load3dsModel(String in_model_file, String textureDir)
	{
		Spatial output = null; // the geometry will go here.
		final ByteArrayOutputStream outStream =
			new ByteArrayOutputStream(); // byte array streams don't have to be closed
		try {
			final File textures;
			if(textureDir != null) { // set textureDir location
				textures = new File( textureDir );
			} else {// try to infer textureDir from modelPath.
				textures = new File(
						in_model_file.substring(0, in_model_file.lastIndexOf('/')) );
			}	// Add texture URL to auto-locator
			final SimpleResourceLocator location =
				new SimpleResourceLocator(textures.toURI().toURL());
            ResourceLocatorTool.addResourceLocator(
            		ResourceLocatorTool.TYPE_TEXTURE, location );
 
			// read .3ds file into memory & convert it to a jME usable format.
			final FileInputStream rawIn = new FileInputStream(in_model_file);
			CONVERTER_3DS.convert(rawIn, outStream);
			rawIn.close(); // FileInputStream s must be explicitly closed.
 
			// prepare outStream for loading.
			final ByteArrayInputStream convertedIn =
				new ByteArrayInputStream(outStream.toByteArray());
 
			// import the converted stream to jME as a Spatial
			output = (Spatial) BinaryImporter.getInstance().load(convertedIn);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File not found at: " + in_model_file);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable read model at: " + in_model_file);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.err.println("Invalid texture location at:" + textureDir);
		}	/*
		* The bounding box is an important optimization.
		* There is no point in rendering geometry outside of the camera's
		* field of view. However, testing whether each individual triangle
		* is visible is nearly as expensive as actually rendering it. So you
		* don't test every triangle. Instead, you just test the bounding box.
		* If the box isn't in view, don't bother looking for triangles inside.
			*/
		output.setModelBound(new BoundingBox());
		output.updateModelBound();
		return (TriMesh) output;

	}
	
	private static TriMesh loadObjModel(File in_model_file)
	{
		

		URL model_url = null;
		TriMesh model_node = null;
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
	
			
			model_node = (TriMesh) BinaryImporter.getInstance().load(
					new ByteArrayInputStream(BO.toByteArray()));
			
			model_node.setModelBound(new BoundingSphere());
			model_node.updateModelBound();
	
			return model_node;
		} 
		catch (MalformedURLException e) 
		{
			System.err.println("Could not open file:" + in_model_file.getName() + "\nPossibly missing texture");

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

	public static Collection<String> getAvailableClasses()
	{
		List<String> l = new ArrayList<String>(availableclasses.keySet());
		Collections.sort(l);
		return l;
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
}
