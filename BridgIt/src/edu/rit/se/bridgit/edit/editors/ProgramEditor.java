package edu.rit.se.bridgit.edit.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import edu.rit.bridgit.edit.editors.model.ProgramDocumentProvider;
import edu.rit.bridgit.edit.editors.model.ProgramEditorModel;
import edu.rit.se.bridgit.edit.views.ScopeView;

public class ProgramEditor extends AbstractTextEditor
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
	}
	
	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException
	{
		super.init(site, input);
	}
	
	@ Override 
	public void createPartControl(Composite parent)
	{
		//super.createPartControl(parent);
		//this.showChangeInformation(true);
		
		// Create the ruler
		m_VerticalBar = this.createVerticalRuler();
		
		// Create the source viewer
		m_SourceViewer = this.createSourceViewer(parent, m_VerticalBar,
				SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		m_SourceViewer.setEditable(true);
		m_SourceViewer.showAnnotations(true);
		m_SourceViewer.setDocument(new Document());
		
		// Set the text object
		m_Text = m_SourceViewer.getTextWidget();
		
		// Create and set the layout
		FillLayout layout = new FillLayout();
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Create the text component and required objects for setup
		Color bg = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
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
		
		// Set the text values
		m_Text.setEditable(true);
		m_Text.setBackground(bg);

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
					
					// Highlight the current line we're on
					m_Text.setLineBackground(0, m_Text.getLineCount(), m_Text.getBackground());
					m_Text.setLineBackground(m_Text.getLineAtOffset(event.caretOffset), 1,
							new Color(Display.getCurrent(), 220, 255, 255));
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
