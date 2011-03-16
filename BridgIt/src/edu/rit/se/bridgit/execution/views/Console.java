package edu.rit.se.bridgit.execution.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import edu.rit.se.bridgit.language.evaluator.Scope;

public class Console extends ViewPart
{
	// The text variables for the text value
	Label consoleLabel;
	Text consoleField;

	public Console()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent)
	{
		// Create the grid layout for the console
		GridLayout grid = new GridLayout(1, true);
		parent.setLayout(grid);
		
		// Add a large label for storing the console output
		consoleLabel = new Label(parent, SWT.LEFT | SWT.BORDER_SOLID | SWT.BORDER); 
		consoleLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		consoleLabel.setEnabled(true);
		
		// Add a text field for entering console commands
		consoleField = new Text(parent, SWT.SINGLE | SWT.LEFT | SWT.BORDER_SOLID | SWT.BORDER | SWT.BOLD);
		consoleField.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		
		// Set the layout data
		parent.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true, 1, 2));
	}

	@Override
	public void setFocus()
	{
		// Set focus to the console field
		consoleField.setFocus();
	}

}
