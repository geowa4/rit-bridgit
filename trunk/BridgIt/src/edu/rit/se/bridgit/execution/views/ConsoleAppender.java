package edu.rit.se.bridgit.execution.views;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.ui.PlatformUI;

public class ConsoleAppender extends AppenderSkeleton
{
	private Console console;
	
	@Override
	protected void append(LoggingEvent e)
	{
		if(!retrieveConsole() || this.layout == null) return;
		console.append(this.layout.format(e));
	}
	
	private boolean retrieveConsole()
	{
		if(console != null)
		{
			return true;
		}
		else
		{
			try
			{
				console = (Console) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(Console.class.getName());
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
	}

	@Override
	public void close()
	{}

	@Override
	public boolean requiresLayout()
	{
		return true;
	}
}
