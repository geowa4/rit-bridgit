package edu.rit.se.bridgit.edit.views;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import edu.rit.se.bridgit.language.model.bridge.GraphicalModelBridgeFactory;

public class TypesView extends ViewPart 
{
	ListViewer list;
	
	public TypesView() 
	{
		
	}

	@Override
	public void createPartControl(Composite parent) 
	{
		list = new ListViewer(parent);
		list.setContentProvider(new TypeContentProvider());
		list.setLabelProvider(new TypeLabelProvider());
		list.setInput(GraphicalModelBridgeFactory.getAvailableClasses());
	}

	@Override
	public void setFocus() 
	{
		list.getControl().setFocus();
	}
}

class TypeContentProvider implements IStructuredContentProvider
{
	@Override
	public Object[] getElements(Object inputElement)
	{
		return GraphicalModelBridgeFactory.getAvailableClasses().toArray();
	}

	@Override
	public void dispose()
	{}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{}
}

class TypeLabelProvider implements ILabelProvider
{

	@Override
	public void addListener(ILabelProviderListener listener)
	{
		
	}

	@Override
	public void dispose()
	{
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener)
	{
		
	}

	@Override
	public Image getImage(Object element)
	{
		return null;
	}

	@Override
	public String getText(Object element)
	{
		return element.toString();
	}
}