package edu.rit.se.bridgit.edit.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

import edu.rit.bridgit.edit.editors.model.ProgramDocumentProvider;
import edu.rit.bridgit.edit.editors.model.ProgramEditorInput;
import edu.rit.bridgit.edit.editors.model.ProgramEditorModel;
import edu.rit.se.bridgit.edit.views.ScopeView;
import edu.rit.se.bridgit.language.PseudoParser;

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
	public void dispose() {
		// Base call
		super.dispose();
		
		// If we're on our last editor
		if(getSite().getPage().getEditorReferences().length <= 0)
		{
			// Try to open another one
			try
			{
				getSite().getPage().openEditor(new ProgramEditorInput(),
						"edu.rit.se.bridgit.edit.editors.programeditor");
			}
			catch (PartInitException e)
			{
				e.printStackTrace();
			}
		}		
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
		
		// Create the reconciler for the document
		PresentationReconciler reconciler = new PresentationReconciler();
		
		// Create the keyword detector
		IWordDetector keywordDetector = new IWordDetector()
		{
			@Override
			public boolean isWordStart(char c)
			{			
				// Return
				return Character.isLetter(c);
			}

			@Override
			public boolean isWordPart(char c)
			{		
				// Return
				return Character.isLetter(c);
			}
			
		};
		
		// The rules for the whole document
		IRule[] documentRules = new IRule[4];
		
		// Create the rules for keyword scanning
		WordRule keywordRule = new WordRule(keywordDetector);
		TextAttribute keywordAttribute = new TextAttribute(new Color(Display.getCurrent(), new RGB(171, 0, 85)), null, SWT.BOLD);
		Token keywordToken = new Token(keywordAttribute);
		for(int i = 0; i < PseudoParser.tokenNames.length; ++i)
		{
			// Get the string
			String parserValue = PseudoParser.tokenNames[i];
			
			// If it is in single quotes
			if(parserValue.matches("'.+'"))
			{
				// Extract the internal string
				parserValue = parserValue.substring(parserValue.indexOf('\'') + 1, parserValue.lastIndexOf('\''));
				
				// If the length is greater than 0
				if(parserValue.length() > 0)
				{
					// See if it's a keyword
					if(Character.isLetter(parserValue.charAt(0)))
						keywordRule.addWord(parserValue, keywordToken);
				}
			}
		}
		documentRules[0] = keywordRule;
		
		// Create the rules for number scanning
		NumberRule numberRule = new NumberRule(
				new Token(new TextAttribute(new Color(Display.getCurrent(), new RGB(190, 0, 0)))));
		documentRules[1] = numberRule;
		
		
		// Create the rules for multiline comment scanning
		documentRules[2] = new MultiLineRule("/*", "*/",
				new Token(new TextAttribute(new Color(Display.getCurrent(), new RGB(63, 95, 191)))), (char) 0, true);
		
		// Create the rules for single-line comment scanning
		documentRules[3] = new SingleLineRule("//", null,
				new Token(new TextAttribute(new Color(Display.getCurrent(), new RGB(63, 127, 95)))), (char) 0, true, true);
		
		// Create the keyword evaluators for the document
		RuleBasedScanner rulesScanner = new RuleBasedScanner();
		rulesScanner.setRules(documentRules);
		DefaultDamagerRepairer rulesDR = new DefaultDamagerRepairer(rulesScanner);
		reconciler.setDamager(rulesDR, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(rulesDR, IDocument.DEFAULT_CONTENT_TYPE);
		
		// Add the repairers to the reconciler
		
		// Add the reconciler to the source viewer
		reconciler.install(m_SourceViewer);
		m_SourceViewer.invalidateTextPresentation();
		
		// Add it to the text area
		m_Text.addCaretListener(caretListener);
	}
}
