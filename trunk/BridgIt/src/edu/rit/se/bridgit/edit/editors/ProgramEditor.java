package edu.rit.se.bridgit.edit.editors;

import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

import edu.rit.bridgit.model.ProgramEditorModel;

public class ProgramEditor extends AbstractDecoratedTextEditor {
	// The editor model of the program
	ProgramEditorModel m_EditorModel = null;
	
	/**
	 * Base constructor
	 * @param p_EditorModel - The editor model we're tied to, which
	 *                        is upated as text is added.
	 */
	public ProgramEditor(ProgramEditorModel p_EditorModel) {
		// Super call
		super();
		
		// Set our internal editor model
		m_EditorModel = p_EditorModel;
	}

}
