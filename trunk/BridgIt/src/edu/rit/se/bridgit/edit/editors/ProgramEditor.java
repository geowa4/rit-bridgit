package edu.rit.se.bridgit.edit.editors;

import java.util.ArrayList;

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
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

import edu.rit.bridgit.edit.editors.model.ProgramDocumentProvider;
import edu.rit.bridgit.edit.editors.model.ProgramEditorModel;
import edu.rit.se.bridgit.edit.views.ScopeView;

public class ProgramEditor extends AbstractDecoratedTextEditor
{
	// The editor model of the program
	ProgramEditorModel m_EditorModel = null;
	
	// The static styled text that is used for all text input
	public static StyledText text;
	
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
		
		// Create and set the layout
		FillLayout layout = new FillLayout();
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Create the text component and required objects for setup
		text = new StyledText(parent,
		SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		Color bg = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
		
		// Set the text values
		text.setEditable(true);
		text.setBackground(bg);
		
		// Set the default text for a simple application
		text.setText(
				"application ProgramTitle\n" +
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
				"}\n"
		);
		
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
					ArrayList<ArrayList<String>> parsedValues = m_Parser.parseScope(text.getText());
				
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
		text.addCaretListener(caretListener);
	}
}
