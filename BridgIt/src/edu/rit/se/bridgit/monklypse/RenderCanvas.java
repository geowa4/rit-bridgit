package edu.rit.se.bridgit.monklypse;

import java.io.FileNotFoundException;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.monklypse.core.SWTDefaultImplementor;

import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jmex.swt.input.SWTKeyInput;
import com.jmex.swt.input.SWTMouseInput;

import edu.rit.se.bridgit.language.model.bridge.GraphicalModelBridgeFactory;

public class RenderCanvas extends SWTDefaultImplementor {

        //private static final Logger logger = Logger.getLogger(RenderCanvas.class);

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
        			GraphicalModelBridgeFactory.loadContent();
        		}
        		catch(FileNotFoundException fnfe)
        		{
        			fnfe.printStackTrace();
        			System.exit(-1);
        		}
                
//        		GraphicalBridge test = GraphicalModelBridgeFactory.buildBridge("maggie");
//        		Command testCom = new Command();
//        		testCom.methodName = "setTranslation";
//        		testCom.parameters = new LinkedList<String>();
//        		testCom.parameters.add("0");
//        		testCom.parameters.add("10");
//        		testCom.parameters.add("0");
//        		try
//        		{
//        			test.sendMessage(testCom);
//        		}
//        		catch(Exception e)
//        		{
//        			e.printStackTrace();
//        		}
//        		
//        		Command testCom2 = new Command();
//        		testCom2.methodName = "setScale";
//        		testCom2.parameters = new LinkedList<String>();
//        		testCom2.parameters.add(".1");
//        		try
//        		{
//        			test.sendMessage(testCom2);
//        		}
//        		catch(Exception e)
//        		{
//        			e.printStackTrace();
//        		}
//        		
//        		test.executeActionQueue();
        		
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
        
        public void addNode(TriMesh triMesh)
        {
        	 
             if(rootNode != null)
            	 rootNode.attachChild(triMesh);
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
