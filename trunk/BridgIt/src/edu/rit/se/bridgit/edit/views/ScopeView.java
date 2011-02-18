package edu.rit.se.bridgit.edit.views;

import java.util.Set;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import edu.rit.se.bridgit.edit.content.StructuredCollectionContentProvider;
import edu.rit.se.bridgit.language.evaluator.Scope;

public class ScopeView extends ViewPart 
{
	private ListViewer list;
	
	public ScopeView() 
	{
		
	}

	@Override
	public void createPartControl(Composite parent) 
	{
		GridLayout grid = new GridLayout(2, true);
		Shell shell = new Shell(parent.getDisplay());
		shell.setLayout(grid);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		list = new ListViewer(parent);
		list.setContentProvider(new StructuredCollectionContentProvider());
		Scope current = Scope.getCurrentScope();
		if(current != null)
		{
			Set<String> vars = Scope.getCurrentScope().getVariables();
			if(vars != null) list.setInput(vars);
		}
	}

	@Override
	public void setFocus() 
	{
		list.getControl().setFocus();
	}
}
