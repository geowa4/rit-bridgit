package edu.rit.se.bridgit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Focuses the types view of the application.
 * 
 * @author Bradley R. Blankenship
 *
 */
public class FocusTypesViewHandler extends AbstractHandler
{
	// The ID of the play perspective
	public static final String EDIT_PERSPECTIVE_ID = "edu.rit.se.bridgit.edit.perspectives.EditPerspective";
	public static final String EXECUTION_PERSPECTIVE_ID = "edu.rit.se.bridgit.execution.perspectives.ExecutionPerspective";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// Attempt to perform the following
		try
		{
			// Get the workbench window and current perspective
			IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
			IPerspectiveDescriptor activePerspective = workbenchWindow.getActivePage().getPerspective();
			
			// If we're in the edit perspective
			if(activePerspective.getId().equals(EDIT_PERSPECTIVE_ID))
			{
				// Get the view
				IViewPart view = PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getActivePage().findView(
							"edu.rit.se.bridgit.edit.views.TypesView");
			
				// Focus the scope
				if(view != null && view.getSite().getPage().isPartVisible(view))
					view.getSite().getPage().activate(view);
			}
			// Otherwise, if we're in the execution perspective
			else if(activePerspective.getId().equals(EXECUTION_PERSPECTIVE_ID))
			{
				// Get the view
				IViewPart view = PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getActivePage().findView(
							"edu.rit.se.bridgit.execution.views.Console");
			
				// Focus the scope
				if(view != null && view.getSite().getPage().isPartVisible(view))
					view.getSite().getPage().activate(view);
			}
		}
		// Print the errors
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
