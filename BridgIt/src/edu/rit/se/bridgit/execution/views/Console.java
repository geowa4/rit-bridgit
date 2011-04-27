package edu.rit.se.bridgit.execution.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

public class Console extends ViewPart
{
	private StyledText consoleText;
	
	@Override
	public void createPartControl(Composite parent)
	{
		consoleText = new StyledText(parent, SWT.MULTI | SWT.LEFT | SWT.READ_ONLY);
		
		GridLayout grid = new GridLayout(1, true);
		Shell shell = new Shell(parent.getDisplay());
		shell.setLayout(grid);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
	
	public void append(String text)
	{
		consoleText.append(text);
	}

	@Override
	public void setFocus()
	{
		consoleText.setFocus();
	}

	public void clear()
	{
		consoleText.setText("");
	}
}
