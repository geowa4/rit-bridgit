package edu.rit.se.bridgit.edit.editors;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.sun.source.tree.Scope;

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
				"application\n" +
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
			@Override
			public void caretMoved(CaretEvent event) {			
				// Get the s position and set the values
				
				// Get the text of the field
				String programText = text.getText();
				
				// Clear out comments
				programText = programText.replaceAll("//.*\n*", "");
				programText = programText.replaceAll("/\\*(.|\\s)*?\\*/", "");
				
				// Declare the pattern and matcher for regex parsing
				Pattern variablePattern = Pattern.compile("\\s*var\\s*\\w*:\\w*");
				Pattern constantPattern = Pattern.compile("\\s*constant\\s*\\w*:\\w*");
				Pattern functionPattern = Pattern.compile("\\s*function\\s*\\w*(.*):\\w*");
				
				// Declare the matches for each
				Matcher variableMatcher = variablePattern.matcher(programText);
				Matcher constantMatcher = constantPattern.matcher(programText);
				Matcher functionMatcher = functionPattern.matcher(programText);
				
				// The lists for the values
				ArrayList<String> variables = new ArrayList<String>();
				ArrayList<String> constants = new ArrayList<String>();
				ArrayList<String> functions = new ArrayList<String>();
				
				// Add all the variables
				while(variableMatcher.find())
					for(int i = 0; i < variableMatcher.groupCount(); ++i)
						variables.add(variableMatcher.group(i).replaceAll("\\s*var\\s*", "").trim());
				
				// Add all the constants
				while(constantMatcher.find())
					for(int i = 0; i < constantMatcher.groupCount(); ++i)
						constants.add(constantMatcher.group(i).replaceAll("\\s*constant\\s*", "").trim());
				
				// Add all the functions
				while(functionMatcher.find())
					for(int i = 0; i < functionMatcher.groupCount(); ++i)
						functions.add(functionMatcher.group(i).replaceAll("\\s*function\\s*", "").trim());
				
				// Set the values of the 
				ScopeView scopeView = (ScopeView) PlatformUI.getWorkbench().
					getActiveWorkbenchWindow().getActivePage().findView("edu.rit.se.bridgit.edit.views.objectsView");
				
				// Set the model components
				scopeView.setScopeComponents(variables, constants, functions);
			}
			
		};
		text.addCaretListener(caretListener);
	}
}
