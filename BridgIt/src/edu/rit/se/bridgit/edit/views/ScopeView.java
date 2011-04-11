package edu.rit.se.bridgit.edit.views;

import java.util.ArrayList;
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
	// The viewers used to view the current scope values
	private ListViewer varList;
	private ListViewer constList;
	private ListViewer functionList;
	
	// The model values used to update the viewers
	private ArrayList<String> variableModelList = new ArrayList<String>();
	private ArrayList<String> constantModelList = new ArrayList<String>();
	private ArrayList<String> functionModelList = new ArrayList<String>();
	
	public ScopeView() 
	{
		
	}

	@Override
	public void createPartControl(Composite parent) 
	{
		GridLayout grid = new GridLayout(1, true);
		parent.setLayout(grid);
		Scope currentScope = Scope.getCurrentScope();
		buildConstantPane(parent, currentScope);
		buildVariablePane(parent, currentScope);
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
		varList.setInput(variableModelList);
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
		constList.setInput(constantModelList);
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
		functionList.setInput(functionModelList);
		if(currentScope != null)
		{
			Set<String> funcs = currentScope.getFunctions();
			if(funcs != null) functionList.setInput(funcs);
		}
	}

	@Override
	public void setFocus() 
	{
		constList.getControl().setFocus();
	}
	
	/**
	 * Used to set the scope components directly for the scope.
	 * 
	 * @param variables - The variables in scope
	 * @param constants - The constants in scope
	 * @param functions - The functions in scope
	 */
	public void setScopeComponents(ArrayList<String> variables,
								   ArrayList<String> constants,
								   ArrayList<String> functions)
	{
		// Clear the model lists
		variableModelList.clear();
		constantModelList.clear();
		functionModelList.clear();
		
		// Set the values
		variableModelList.addAll(variables);
		constantModelList.addAll(constants);
		functionModelList.addAll(functions);
		
		// Refresh the views
		varList.refresh(false);
		constList.refresh(false);
		functionList.refresh(false);
	}
}
