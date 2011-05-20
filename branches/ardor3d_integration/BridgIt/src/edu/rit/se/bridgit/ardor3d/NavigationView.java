package edu.rit.se.bridgit.ardor3d;

import java.util.TimerTask;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.lwjgl.LWJGLException;

import com.ardor3d.framework.CanvasRenderer;
import com.ardor3d.framework.FrameHandler;
import com.ardor3d.framework.lwjgl.LwjglCanvasCallback;
import com.ardor3d.framework.lwjgl.LwjglCanvasRenderer;
import com.ardor3d.framework.swt.SwtCanvas;
import com.ardor3d.image.util.AWTImageLoader;
import com.ardor3d.input.ControllerWrapper;
import com.ardor3d.input.Key;
import com.ardor3d.input.PhysicalLayer;
import com.ardor3d.input.control.OrbitCamControl;
import com.ardor3d.input.logical.DummyControllerWrapper;
import com.ardor3d.input.logical.LogicalLayer;
import com.ardor3d.input.swt.SwtFocusWrapper;
import com.ardor3d.input.swt.SwtKeyboardWrapper;
import com.ardor3d.input.swt.SwtMouseManager;
import com.ardor3d.input.swt.SwtMouseWrapper;
import com.ardor3d.math.Vector3;
import com.ardor3d.renderer.Camera;

import edu.rit.bridgit.edit.editors.model.ProgramEditorInput;
import edu.rit.se.bridgit.edit.editors.ProgramEditor;

/**
 * A simple example showing a textured and lit box spinning.
 */
public class NavigationView extends ViewPart
{

	public static final String ID = "edu.rit.se.bridgit.views.WorldView";

	final com.ardor3d.util.Timer timer;

	private final FrameHandler frameWork;
	private final LogicalLayer logicalLayer;
	private final ExampleScene scene;
	private final MyExit exit;
	private final RotatingCubeGame game;


	private Runnable IdleUpdateRunnable;
	private TimerTask timerTask;
	private final  java.util.Timer timer2;

	private Action aRefreshView;


	private static class MyExit implements Exit
	{       
		private volatile boolean exit = false;

		public void exit() { exit = true; }

		public boolean isExit() { return exit; }

	}

	/**
	 * Default Constructor
	 */
	 public NavigationView()
	{
		// Create the Ardor Timer
		timer = new com.ardor3d.util.Timer();

		// Create the Frame Handler
		frameWork = new FrameHandler(timer);

		// Logical Layer to provide access to the inputs
		logicalLayer = new LogicalLayer();      

		exit = new MyExit();

		// Create the SceneManager
		scene = new ExampleScene();

		// Create the Example Scene
		game = new RotatingCubeGame(scene, exit, logicalLayer, Key.T);

		// Add the Scene Updater into the Frame handler
		frameWork.addUpdater(game);      

		// Create the Timer to refresh this View
		timer2 = new java.util.Timer();
	}


	 @Override
	 public void createPartControl(Composite parent)
	 {
		 // Create Ardor3d Context
		 createArdor3dContext(parent);

		 // Create Actions for Toolbar
		 createViewActions();

		 // Create the View's Toolbar
		 createLocalToolbar();

		 openEditor();
	 }


	 private void openEditor()
	 {
		 try {
			 getSite().getPage().openEditor(new ProgramEditorInput(),
					 ProgramEditor.class.getName());
		 } catch (PartInitException e) {
			 e.printStackTrace();
		 }
	 }


	 @Override
	 public void setFocus()
	 {      
		 // Nothing to do here yet
	 }


	 @Override
	 public void dispose()   
	 {         
		 // Stop Update Task
		 timerTask.cancel();

		 // Stop Update Timer
		 timer2.cancel();

		 // Call Super Dispose
		 super.dispose();
	 }

	 /**
	  * Add the LWJGL Callback into the SwtCanvas
	  * @param canvas
	  * @param renderer
	  */
	 private static void addCallback(final SwtCanvas canvas, final LwjglCanvasRenderer renderer)
	 {
		 renderer.setCanvasCallback(new LwjglCanvasCallback()
		 {
			 @Override
			 public void makeCurrent() throws LWJGLException
			 {
				 canvas.setCurrent();
				 canvas.update();            
			 }

			 @Override
			 public void releaseContext() throws LWJGLException
			 {
				 // Nothing to do YET
			 }
		 });
	 }


	 /**
	  *  Add new Resize Control Listener
	  * @param swtCanvas
	  * @param canvasRenderer
	  * @param frameWork
	  * @return
	  */
	 static ControlListener newResizeHandler(final SwtCanvas swtCanvas,
			 final CanvasRenderer canvasRenderer,
			 final FrameHandler frameWork )
	 {
		 // Create the New ControlListener to handle the Move and Resize events
		 final ControlListener retVal = new ControlListener()
		 {
			 // Handle the Move Event
			 public void controlMoved(final ControlEvent e)
			 {
				 frameWork.updateFrame();
			 }

			 // Handle the Resize Envent
			 public void controlResized(final ControlEvent event)
			 {
				 // Sanity Check
				 final Rectangle size = swtCanvas.getClientArea();
				 if ((size.width == 0) && (size.height == 0))
				 {
					 return;
				 }

				 final float aspect = (float) size.width / (float) size.height;
				 final Camera camera = canvasRenderer.getCamera();

				 if (camera != null)
				 {
					 final float fovY = 45; // XXX no camera.getFov()
					 final double near = camera.getFrustumNear();
					 final double far = camera.getFrustumFar();
					 camera.setFrustumPerspective(fovY, aspect, near, far);
					 camera.resize(size.width, size.height);
				 }

				 frameWork.updateFrame();
			 }
		 };

		 // Return the new ControlListener
		 return retVal;
	 }



	 /**
	  *
	  * @param parent
	  */
	 private void createArdor3dContext(Composite parent)
	 {
		 // TODO Check more info of ardor3d.XXXXX attributes
		 System.setProperty("ardor3d.useMultipleContexts", "true");

		 // Set the Parent Context Background Color
		 //parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_RED));

		 // Create New Fill Layout
		 parent.setLayout(new FillLayout());

		 // Create OpenGl Context
		 final GLData gldata = new GLData();
		 gldata.depthSize    = 8;    // Set the Depth Buffer Size
		 gldata.doubleBuffer = true;  // Activate the Double Buffer         

		 // Create the SWT Canvas that Connect with the OpenGL Context
		 final SwtCanvas canvas1 = new SwtCanvas(parent, SWT.NONE, gldata);
		 //canvas1.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));          

		 // Create the OpenGL Renderer (in our case, the LwjGL)
		 final LwjglCanvasRenderer lwjglCanvasRenderer1 = new LwjglCanvasRenderer(scene);                        

		 //      
		 parent.addControlListener(new ControlListener()
		 {

			 @Override
			 public void controlResized(ControlEvent e)
			 {            
				 frameWork.updateFrame();
			 }

			 @Override
			 public void controlMoved(ControlEvent e)
			 {                  
				 frameWork.updateFrame();
			 }
		 });


		 // Add a Callback to lwjglRender to be linked with SWTCanvas
		 addCallback(canvas1, lwjglCanvasRenderer1);

		 // Add the LWJGL Render into the SWTCanvas
		 canvas1.setCanvasRenderer(lwjglCanvasRenderer1);

		 // Add the SWTCanvas into the Frame Handler
		 frameWork.addCanvas(canvas1);

		 //
		 canvas1.addControlListener(newResizeHandler(canvas1, lwjglCanvasRenderer1, frameWork));

		 //
		 canvas1.setFocus();

		 final SwtKeyboardWrapper keyboardWrapper = new SwtKeyboardWrapper(canvas1);
		 final SwtMouseWrapper mouseWrapper = new SwtMouseWrapper(canvas1);
		 final SwtFocusWrapper focusWrapper = new SwtFocusWrapper(canvas1);
		 final SwtMouseManager mouseManager = new SwtMouseManager(canvas1);
		 final ControllerWrapper controllerWrapper = new DummyControllerWrapper();

		 final PhysicalLayer pl = new PhysicalLayer(keyboardWrapper, mouseWrapper, controllerWrapper, focusWrapper);

		 logicalLayer.registerInput(canvas1, pl);      

		 // add Orbit handler - set it up to control the main camera
		 final OrbitCamControl control = new OrbitCamControl(canvas1.getCanvasRenderer().getCamera(), new Vector3(0,0,0));
		 control.setupMouseTriggers(logicalLayer, true);
		 control.setSphereCoords(15, 0, 0);

		 frameWork.init();
		 frameWork.updateFrame();      

		 AWTImageLoader.registerLoader();

		 //        try {
			 //            final SimpleResourceLocator srl = new SimpleResourceLocator(ResourceLocatorTool.getClassPathResource(
		 //                    LwjglSwtExample.class, "textures/directory"));
		 //           
		 //           
		 //            System.out.println("Resource Dir: " + srl.getBaseDir());
		 //           
		 //            ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, srl);
		 //        } catch (final URISyntaxException ex) {
		 //            ex.printStackTrace();
		 //        }


		 //
		 IdleUpdateRunnable = new Runnable()
		 {
			 @Override
			 public void run()
			 {
				 frameWork.updateFrame();                
			 }
		 };

		 //
		 final Composite pParent = parent;

		 //
		 timerTask = new TimerTask()   
		 {         
			 @Override
			 public void run()
			 {
				 pParent.getDisplay().asyncExec(IdleUpdateRunnable);            
			 }
		 };

		 // Run the Task Every 20Hz
		 timer2.scheduleAtFixedRate(timerTask, 0, 50);

	 }


	 /**
	  * Create the Local View ToolBar
	  */
	 private void createLocalToolbar()
	 {   
		 /** Populate the Local View ToolBar **/
		 // Get the View's ToolBar
//		 IToolBarManager toolBarMgr = getViewSite().getActionBars().getToolBarManager();      
//		 toolBarMgr.add(aRefreshView);      
	 }


	 /**
	  * Create the View's Actions
	  */
	 private void createViewActions()
	 {
		 // Create the ResetView Action
		 aRefreshView = new Action()
		 {         
			 public void run()
			 {
				 if(frameWork != null)            
					 frameWork.updateFrame();            
			 }
		 };
		 aRefreshView.setText("Refresh the View");
		 aRefreshView.setToolTipText("Refresh the View");
	 }
}


