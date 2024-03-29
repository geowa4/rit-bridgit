package edu.rit.se.bridgit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;

import edu.rit.se.bridgit.execution.views.Console;

public class PlayHandler extends AbstractHandler
{
	// The ID of the play perspective
	public static final String EDIT_PERSPECTIVE_ID = "edu.rit.se.bridgit.edit.perspectives.EditPerspective";
	public static final String EXECUTION_PERSPECTIVE_ID = "edu.rit.se.bridgit.execution.perspectives.ExecutionPerspective";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// Get the workbench window and current perspective
		IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		IPerspectiveDescriptor activePerspective = workbenchWindow.getActivePage().getPerspective();
		
		// Now, if we're in the edit perspective
		if(activePerspective.getId().equals(EDIT_PERSPECTIVE_ID))
		{		
			// Now, show the right perspective
			try
			{
				workbenchWindow.getWorkbench().showPerspective(EXECUTION_PERSPECTIVE_ID, workbenchWindow);
				workbenchWindow.getWorkbench().getDisplay().asyncExec(new LanguageHandler());
				Console console = (Console) workbenchWindow.getActivePage().findView(Console.class.getName());
				console.clear();
				console.setFocus();
			} catch (WorkbenchException e)
			{
				e.printStackTrace();
			}
		}
		// Otherwise, if we're in the execution perspective
		else if(activePerspective.getId().equals(EXECUTION_PERSPECTIVE_ID))
		{
			// Now, show the right perspective
			try
			{
				workbenchWindow.getWorkbench().showPerspective(EDIT_PERSPECTIVE_ID, workbenchWindow);
			} catch (WorkbenchException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
