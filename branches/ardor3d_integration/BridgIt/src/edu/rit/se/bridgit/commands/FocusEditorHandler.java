package edu.rit.se.bridgit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * Focuses the top editor of the application.
 * 
 * @author Bradley R. Blankenship
 *
 */
public class FocusEditorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Attempt to perform the following
		try
		{
			// Get the view
			IEditorReference[] editors = PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getActivePage().findEditors(
						null, "edu.rit.se.bridgit.edit.editors.ProgramEditor", IWorkbenchPage.MATCH_ID);
		
			// If the editor is there
			if(editors.length > 0 && editors[0].getPage().isPartVisible(editors[0].getPart(true)))
			{
				// Set the first one to on top and set focus
				editors[0].getPage().activate(editors[0].getPart(true));
			}
		}
		catch(Exception e)
		{
			// Print the error
			System.err.println("Could not focus the editor: ");
			e.printStackTrace();
		}
		
		// Return
		return null;
	}
}
