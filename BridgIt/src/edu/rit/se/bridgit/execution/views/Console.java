package edu.rit.se.bridgit.execution.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class Console extends ViewPart
{
	private Label consoleLabel;
	private Text consoleField;
	private boolean receiveInput;
	
	@Override
	public void createPartControl(Composite parent)
	{
		GridLayout grid = new GridLayout(1, true);
		parent.setLayout(grid);
		
		consoleLabel = new Label(parent, SWT.LEFT | SWT.BORDER_SOLID | SWT.BORDER); 
		consoleLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		consoleLabel.setEnabled(true);
		
		consoleField = new Text(parent, SWT.SINGLE | SWT.LEFT | SWT.BORDER_SOLID | SWT.BORDER | SWT.BOLD);
		consoleField.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		consoleField.setEnabled(false);
		
		parent.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true, 1, 2));
	}

	@Override
	public void setFocus()
	{
		if(receiveInput) 
		{
			consoleField.setEnabled(true);
			consoleField.setFocus();
		}
	}
	
	public void receiveInput(ConsoleInputListener listener)
	{
		receiveInput = true;
		setFocus();
		listener.setTextInputElement(consoleField);
	}
	
	public void ignoreInput()
	{
		receiveInput = false;
		consoleField.setEnabled(false);
	}
}
