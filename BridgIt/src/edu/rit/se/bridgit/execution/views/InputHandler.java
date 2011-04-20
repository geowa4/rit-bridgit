package edu.rit.se.bridgit.execution.views;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class InputHandler implements Listener
{
	private static final Logger log = Logger.getLogger(InputHandler.class);
	
	private Text input;

	public InputHandler(Text input)
	{
		this.input = input;
		this.input.addListener(SWT.KeyDown, this);
	}

	@Override
	public void handleEvent(Event event)
	{
		if(event.keyCode == SWT.CR)
		{
			log.debug("You entered: " + input.getText());
		}
	}
	
	public void detach()
	{
		input.removeListener(SWT.KeyDown, this);
	}
}
