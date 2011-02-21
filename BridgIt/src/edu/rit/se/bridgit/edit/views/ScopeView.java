package edu.rit.se.bridgit.edit.views;

import java.util.Set;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import edu.rit.se.bridgit.edit.content.StructuredCollectionContentProvider;
import edu.rit.se.bridgit.language.evaluator.Scope;

public class ScopeView extends ViewPart 
{
	private ListViewer varList;
	private ListViewer constList;
	private ListViewer functionList;
	
	public ScopeView() 
	{
		
	}

	@Override
	public void createPartControl(Composite parent) 
	{
		GridLayout grid = new GridLayout(1, true);
		parent.setLayout(grid);
		Scope currentScope = Scope.getCurrentScope();
		buildVariablePane(parent, currentScope);
		buildConstantPane(parent, currentScope);
		buildFunctionPane(parent, currentScope);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true, 1, 2));
	}
	
	public void buildVariablePane(Composite parent, Scope currentScope)
	{
		Label varLabel = new Label(parent, SWT.NO_FOCUS);
		varLabel.setText("Variables");
		varLabel.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		varList = new ListViewer(parent);
		varList.setContentProvider(new StructuredCollectionContentProvider());
		varList.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		if(currentScope != null)
		{
			Set<String> vars = currentScope.getVariables();
			if(vars != null) varList.setInput(vars);
		}
	}
	
	public void buildConstantPane(Composite parent, Scope currentScope)
	{
		Label constLabel = new Label(parent, SWT.NO_FOCUS);
		constLabel.setText("Constants");
		constLabel.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		constList = new ListViewer(parent);
		constList.setContentProvider(new StructuredCollectionContentProvider());
		constList.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		if(currentScope != null)
		{
			Set<String> cons = currentScope.getConstants();
			if(cons != null) constList.setInput(cons);
		}
	}
	
	public void buildFunctionPane(Composite parent, Scope currentScope)
	{
		Label funcLabel = new Label(parent, SWT.NO_FOCUS);
		funcLabel.setText("Functions");
		funcLabel.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
		functionList = new ListViewer(parent);
		functionList.setContentProvider(new StructuredCollectionContentProvider());
		functionList.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		if(currentScope != null)
		{
			Set<String> funcs = currentScope.getFunctions();
			if(funcs != null) functionList.setInput(funcs);
		}
	}

	@Override
	public void setFocus() 
	{
		varList.getControl().setFocus();
	}
}
