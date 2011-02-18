package edu.rit.se.bridgit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.handlers.HandlerUtil;

import antlr.RecognitionException;

import edu.rit.se.bridgit.edit.editors.ProgramEditor;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class PlayHandler extends AbstractHandler {
	// The ID of the play perspective
	public static final String EDIT_PERSPECTIVE_ID = "edu.rit.se.bridgit.edit.perspectives.editPerspective";
	public static final String EXECUTION_PERSPECTIVE_ID = "edu.rit.se.bridgit.execution.perspectives.executionPerspective";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get the workbench window and current perspective
		IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		IPerspectiveDescriptor activePerspective = workbenchWindow.getActivePage().getPerspective();
		
		// Now, if we're in the edit perspective
		if(activePerspective.getId().equals(EDIT_PERSPECTIVE_ID)) {
			// Reset the calling button's tooltip
			
			// Now, show the right perspective
			try {
				workbenchWindow.getWorkbench().showPerspective(EXECUTION_PERSPECTIVE_ID, workbenchWindow);
			} catch (WorkbenchException e) {
				e.printStackTrace();
			}

			System.out.println(LanguageHandler.evaluateProgram(ProgramEditor.text.getText()));
		}
		// Otherwise, if we're in the execution perspective
		else if(activePerspective.getId().equals(EXECUTION_PERSPECTIVE_ID)) {
			// Reset the calling button's tooltip
			
			// Now, show the right perspective
			try {
				workbenchWindow.getWorkbench().showPerspective(EDIT_PERSPECTIVE_ID, workbenchWindow);
			} catch (WorkbenchException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}