package edu.rit.se.bridgit;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import edu.rit.se.bridgit.execution.views.AppConsoleAppender;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "BridgIt"; //$NON-NLS-1$
	
	private static final Logger log = Logger.getLogger(Activator.class);

	// The shared instance
	private static Activator plugin;

	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		log.trace("Starting the Activator.");
//		System.loadLibrary("lwjgl");
//		System.loadLibrary("jinput");
		plugin = this;
		setUpLogger();
	}

	private void setUpLogger() throws IOException
	{
		URL confURL = getBundle().getEntry("log4j.properties");
	    PropertyConfigurator.configure(FileLocator.toFileURL(confURL).getFile());
	    AppConsoleAppender aca = new AppConsoleAppender();
	    aca.setLayout(new PatternLayout("%m"));
	    Logger.getRootLogger().addAppender(aca);
	    log.trace("Log4J properly configured.");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		log.trace("Stopping the Activator.");
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
