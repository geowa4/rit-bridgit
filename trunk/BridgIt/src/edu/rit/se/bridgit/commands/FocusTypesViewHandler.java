package edu.rit.se.bridgit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

/**
 * Focuses the types view of the application.
 * 
 * @author Bradley R. Blankenship
 *
 */
public class FocusTypesViewHandler extends AbstractHandler
{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// Attempt to perform the following
		try
		{
			// Get the view
			IViewPart view = PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getActivePage().findView("edu.rit.se.bridgit.edit.views.typesView");
			
			// Focus the scope
			if(view != null && view.getSite().getPage().isPartVisible(view))
				view.getSite().getPage().activate(view);
		}
		catch(Exception e)
		{
			// Print the error
			System.err.println("Could not focus the types view: ");
			e.printStackTrace();
		}
		
		// Return
		return null;
	}

}