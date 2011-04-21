package edu.rit.se.bridgit.execution.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class Console extends ViewPart
{
	@Override
	public void createPartControl(Composite parent)
	{
		GridLayout grid = new GridLayout(1, true);
		parent.setLayout(grid);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true, 1, 2));
	}

	@Override
	public void setFocus()
	{}
}
