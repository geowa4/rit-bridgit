package edu.rit.se.bridgit.edit.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

import edu.rit.bridgit.edit.editors.model.ProgramDocumentProvider;
import edu.rit.bridgit.edit.editors.model.ProgramEditorModel;

public class ProgramEditor extends AbstractDecoratedTextEditor {
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
	/*
	@ Override 
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		StyledText text = new StyledText(parent,
		SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setEditable(false);
		Color bg = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		text.setBackground(bg);
		text.setText("Here's some text!");
	}
	*/
}
