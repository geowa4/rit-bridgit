package edu.rit.se.bridgit.edit.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

import edu.rit.bridgit.edit.editors.model.ProgramDocumentProvider;
import edu.rit.bridgit.edit.editors.model.ProgramEditorModel;
import edu.rit.se.bridgit.edit.views.ScopeView;

public class ProgramEditor extends AbstractDecoratedTextEditor
{
	// The editor model of the program
	ProgramEditorModel m_EditorModel = null;
	
	// The vertical scroll bar
	IVerticalRuler m_VerticalBar;
	
	// The source viewer for the editor
	ISourceViewer m_SourceViewer;
	
	// The static styled text that is used for all text input
	public static StyledText m_Text;
	
	/**
	 * Base constructor
	 */
	public ProgramEditor()
	{
		// Super call
		super();
		
		// Create a new editor model, which can be used for processing the program
		m_EditorModel = new ProgramEditorModel();
		this.setDocumentProvider(new ProgramDocumentProvider());
		configureInsertMode(SMART_INSERT, false);
	    this.enableOverwriteMode(true);
	}
	
	@Override
	protected boolean isLineNumberRulerVisible() {
	     return true;
	 }
	
	@ Override 
	public void createPartControl(Composite parent)
	{
		// Super call
		super.createPartControl(parent);
	
		// Create the source viewer
		m_SourceViewer = getSourceViewer();
		m_SourceViewer.setEditable(true);
		m_SourceViewer.showAnnotations(true);
		m_SourceViewer.setDocument(new Document());

		// Set the text object
		m_Text = m_SourceViewer.getTextWidget();
		
		// Set the initial text of the program
		m_SourceViewer.getDocument().set(
				"application Program\n" +
				"{\n" +
				"\tsetup\n" +
				"\t{\n" +
				"\t\t\n" +
				"\t}\n" +
				"\n" +
				"\tmain\n" +
				"\t{\n" +
				"\t\t\n" +
				"\t}\n" +
				"}\n");

		// Create a simple listener to handle when the caret changes in text
		CaretListener caretListener = new CaretListener()
		{
			// The parser we use
			private EditScopeParser m_Parser = new EditScopeParser();
			
			@Override
			public void caretMoved(CaretEvent event) {
				// Attempt to move the caret and parse the text
				try
				{
					// Parse the text
					ArrayList<ArrayList<String>> parsedValues = m_Parser.parseScope(m_Text.getText());
				
					// Set the values of the scope view
					ScopeView scopeView = (ScopeView) PlatformUI.getWorkbench().
						getActiveWorkbenchWindow().getActivePage().findView("edu.rit.se.bridgit.edit.views.objectsView");
				
					// If the values parsed are correct, set the values
					if(parsedValues.size() >= 3)
						scopeView.setScopeComponents(parsedValues.get(0), parsedValues.get(1), parsedValues.get(2));
				}
				// We failed
				catch(Exception e)
				{
					// Print the error and stack tract
					System.err.println("Failed parsing the program text editor. Details below: ");
					e.printStackTrace();
				}
			}
			
		};
		
		// Add it to the text area
		m_Text.addCaretListener(caretListener);
	}
}
