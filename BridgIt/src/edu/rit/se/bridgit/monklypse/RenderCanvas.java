package edu.rit.se.bridgit.monklypse;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.monklypse.core.SWTDefaultImplementor;

import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jmex.swt.input.SWTKeyInput;
import com.jmex.swt.input.SWTMouseInput;

import edu.rit.se.bridgit.language.model.bridge.GraphicalModelBridgeFactory;

public class RenderCanvas extends SWTDefaultImplementor {

        private static final Logger logger = Logger.getLogger(RenderCanvas.class
                        .getName());

        private Vector3f axis;
        //private Box box;
        long startTime = 0;
        long fps = 0;
        private InputHandler input;
        //private List<Updatable> updatables = new ArrayList<Updatable>();

        public RenderCanvas() {
                super(640, 480);
                
        }

        public void simpleSetup() {

                canvas.setUpdateInput(false);
                canvas.setTargetRate(30);
                canvas.setUpdateRate(60);

                canvas.addKeyListener((SWTKeyInput) KeyInput.get());
                canvas.addMouseTrackListener(new MouseTrackListener() {

                        /*
                         * (non-Javadoc)
                         * 
                         * @see
                         * org.eclipse.swt.events.MouseTrackListener#mouseEnter(org.eclipse
                         * .swt.events.MouseEvent)
                         */
                        @Override
                        public void mouseEnter(MouseEvent e) {
                                canvas.setUpdateInput(true);
                        }

                        /*
                         * (non-Javadoc)
                         * 
                         * @see
                         * org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse
                         * .swt.events.MouseEvent)
                         */
                        @Override
                        public void mouseExit(MouseEvent e) {
                                canvas.setUpdateInput(false);
                        }

                        /*
                         * (non-Javadoc)
                         * 
                         * @see
                         * org.eclipse.swt.events.MouseTrackListener#mouseHover(org.eclipse
                         * .swt.events.MouseEvent)
                         */
                        @Override
                        public void mouseHover(MouseEvent e) {
                                // TODO Auto-generated method stub

                        }

                });
                SWTMouseInput.setup(canvas, true);
                
                new Quaternion();
                axis = new Vector3f(1, 1, 0.5f);
                axis.normalizeLocal();
                
                try
        		{
        			GraphicalModelBridgeFactory.loadAvailableClasses();
        		}
        		catch(FileNotFoundException fnfe)
        		{
        			fnfe.printStackTrace();
        			System.exit(-1);
        		}
                
//                URL model_url = RenderCanvas.class.getClassLoader().getResource("Models" + File.separator + 
//                		"People" + File.separator + "Ballerina" + File.separator + "Maggie.obj");
//                
//                URL mat_url = RenderCanvas.class.getClassLoader().getResource("Models" + File.separator + 
//                		"People" + File.separator + "Ballerina" + File.separator);
//                
//             // Create something to convert .obj format to .jme
//        		FormatConverter converter = new ObjToJme();
//        		// Point the converter to where it will find the .mtl file from
//        		converter.setProperty("mtllib", mat_url);
//         
//        		// This byte array will hold my .jme file
//        		ByteArrayOutputStream BO = new ByteArrayOutputStream();
//        		try {
//        			// Use the format converter to convert .obj to .jme
//        			converter.convert(model_url.openStream(), BO);
//        			Node maggie = (Node) BinaryImporter.getInstance().load(
//        				new ByteArrayInputStream(BO.toByteArray()));
//        			// shrink this baby down some
//        			maggie.setLocalScale(.07f);
//        			maggie.setModelBound(new BoundingSphere());
//        			maggie.updateModelBound();
//        			// Put her on the scene graph
//        			rootNode.attachChild(maggie);
//        		} catch (IOException e) { // Just in case anything happens
//        			logger.logp(Level.SEVERE, this.getClass().toString(),
//        				"simpleInitGame()", "Exception", e);
//        			System.exit(0);
//        		}

                //Vector3f max = new Vector3f(5, 5, 5);
                //Vector3f min = new Vector3f(-5, -5, -5);
//
//                box = new Box("Box", min, max);
//                box.setModelBound(new BoundingBox());
//                box.updateModelBound();
//                box.setLocalTranslation(new Vector3f(0, 0, -10));
//                box.setRenderQueueMode(Renderer.QUEUE_SKIP);
//                rootNode.attachChild(box);

 //               box.setRandomColors();

                startTime = System.currentTimeMillis() + 5000;

                input = new FirstPersonHandler(cam, 50, 1);
                //execute in rendercontext to correctly create the resource in the opengl context
                //render(new Callable<Object>() {
                        /*
                         * (non-Javadoc)
                         * 
                         * @see java.util.concurrent.Callable#call()
                         */
                        //@Override
                        //public Object call() throws Exception {
                                //TextureManager.clearCache();
                                //TextureState ts = renderer.createTextureState();
                                //ts.setEnabled(true);
                                //URL resource = getClass().getClassLoader().getResource(
                                  //              "org/monklypse/core/Monkey.jpg");
                                //Texture texture = TextureManager.loadTexture(resource,
                                  //              Texture.MinificationFilter.BilinearNearestMipMap,
                                    //            Texture.MagnificationFilter.Bilinear);
                                //ts.setTexture(texture);
                                //rootNode.setRenderState(ts);
                                //rootNode.updateRenderState();
                                //return null;
                        //}
                //});

        }

        public void simpleUpdate() {
                if (canvas.isUpdateInput()) {
                        input.update(tpf);
                }

                // Code for rotating the box... no surprises here.
//                if (tpf < 1) {
//                        angle = angle + (tpf * 25);
//                        if (angle > 360) {
//                                angle = 0;
//                        }
//                }
                //rotQuat.fromAngleNormalAxis(angle * FastMath.DEG_TO_RAD, axis);
                //box.setLocalRotation(rotQuat);

//                if (startTime > System.currentTimeMillis()) {
//                        fps++;
//                } else {
//                        long timeUsed = 5000 + (startTime - System.currentTimeMillis());
//                        startTime = System.currentTimeMillis() + 5000;
//                        doUpdate();
//                        logger.info(fps + " frames in " + (timeUsed / 1000f)
//                                        + " seconds = " + (fps / (timeUsed / 1000f))
//                                        + " FPS (average)");
//                        fps = 0;
//                }

        }
        
        public void addNode(Node in_model)
        {
        	 
             if(rootNode != null)
            	 rootNode.attachChild(in_model);
        }

        /* (non-Javadoc)
         * @see org.monklypse.core.SWTDefaultImplementor#simpleRender()
         */
        @Override
        public void simpleRender() {
                // TODO Auto-generated method stub
                
        }

        public long getFPS() {
                return this.fps;
        }

        /*public void addUpdatable(Updatable u) {
                this.updatables.add(u);
        }

        public void removeUpdatable(Updatable u) {
                this.updatables.remove(u);
        }

        public void fireUpdate() {
                for (Updatable u : updatables) {
                        u.update();
                }
        }*/
}
