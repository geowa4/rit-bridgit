package edu.rit.se.bridgit.edit.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import edu.rit.bridgit.edit.editors.model.ProgramDocumentProvider;
import edu.rit.bridgit.edit.editors.model.ProgramEditorInput;
import edu.rit.bridgit.edit.editors.model.ProgramEditorModel;

public class ProgramEditor extends AbstractTextEditor {
	// The editor model of the program
	ProgramEditorModel m_EditorModel = null;
	
	/**
	 * Base constructor
	 */
	public ProgramEditor() {
		// Super call
		super();
		
		// Create a new editor model, which can be used for processing the program
		m_EditorModel = new ProgramEditorModel();
		this.setDocumentProvider(new ProgramDocumentProvider());
	}
	
	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
		if(input instanceof ProgramEditorInput) {
			
		}
	}
	
	@ Override 
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		// Create and set the layout
//		FillLayout layout = new FillLayout();
//		parent.setLayout(layout);
//		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
//		
//		// Create the text component and required objects for setup
//		StyledText text = new StyledText(parent,
//		SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
//		Color bg = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
//		
//		// Set the text values
//		text.setEditable(true);
//		text.setBackground(bg);
//		text.setText("");
	}
}
