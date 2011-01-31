package edu.rit.se.bridgit.monklypse;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.monklypse.core.SWTDefaultImplementor;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jmex.swt.input.SWTKeyInput;
import com.jmex.swt.input.SWTMouseInput;

public class RenderCanvas extends SWTDefaultImplementor {

        private static final Logger logger = Logger.getLogger(RenderCanvas.class
                        .getName());

        private Quaternion rotQuat;
        private float angle = 0;
        private Vector3f axis;
        private Box box;
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
                
                // Normal Scene setup stuff...
                rotQuat = new Quaternion();
                axis = new Vector3f(1, 1, 0.5f);
                axis.normalizeLocal();

                Vector3f max = new Vector3f(5, 5, 5);
                Vector3f min = new Vector3f(-5, -5, -5);

                box = new Box("Box", min, max);
                box.setModelBound(new BoundingBox());
                box.updateModelBound();
                box.setLocalTranslation(new Vector3f(0, 0, -10));
                box.setRenderQueueMode(Renderer.QUEUE_SKIP);
                rootNode.attachChild(box);

                box.setRandomColors();

                startTime = System.currentTimeMillis() + 5000;

                input = new FirstPersonHandler(cam, 50, 1);
                //execute in rendercontext to correctly create the resource in the opengl context
                render(new Callable<Object>() {
                        /*
                         * (non-Javadoc)
                         * 
                         * @see java.util.concurrent.Callable#call()
                         */
                        @Override
                        public Object call() throws Exception {
                                TextureManager.clearCache();
                                TextureState ts = renderer.createTextureState();
                                ts.setEnabled(true);
                                URL resource = getClass().getClassLoader().getResource(
                                                "org/monklypse/core/Monkey.jpg");
                                Texture texture = TextureManager.loadTexture(resource,
                                                Texture.MinificationFilter.BilinearNearestMipMap,
                                                Texture.MagnificationFilter.Bilinear);
                                ts.setTexture(texture);
                                rootNode.setRenderState(ts);
                                rootNode.updateRenderState();
                                return null;
                        }
                });

        }

        public void simpleUpdate() {
                if (canvas.isUpdateInput()) {
                        input.update(tpf);
                }

                // Code for rotating the box... no surprises here.
                if (tpf < 1) {
                        angle = angle + (tpf * 25);
                        if (angle > 360) {
                                angle = 0;
                        }
                }
                rotQuat.fromAngleNormalAxis(angle * FastMath.DEG_TO_RAD, axis);
                box.setLocalRotation(rotQuat);

                if (startTime > System.currentTimeMillis()) {
                        fps++;
                } else {
                        long timeUsed = 5000 + (startTime - System.currentTimeMillis());
                        startTime = System.currentTimeMillis() + 5000;
                        doUpdate();
                        logger.info(fps + " frames in " + (timeUsed / 1000f)
                                        + " seconds = " + (fps / (timeUsed / 1000f))
                                        + " FPS (average)");
                        fps = 0;
                }

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
