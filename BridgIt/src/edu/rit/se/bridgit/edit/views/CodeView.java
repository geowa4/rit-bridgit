package edu.rit.se.bridgit.edit.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.rit.bridgit.model.ProgramEditorInput;
import edu.rit.bridgit.model.ProgramEditorModel;
import edu.rit.se.bridgit.edit.editors.ProgramEditor;

public class CodeView extends ViewPart {

	public CodeView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// Attempt to attach the program editor to this view
		try {
			IEditorPart editor = getSite().getPage().openEditor(new ProgramEditorInput(),
					"edu.rit.se.bridgit.edit.editors.ProgramEditor");
			editor.createPartControl(parent);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
