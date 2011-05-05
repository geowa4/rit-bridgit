package edu.rit.se.bridgit.edit.content;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class StructuredCollectionContentProvider implements IStructuredContentProvider
{
	@Override
	public Object[] getElements(Object inputElement)
	{
		if(inputElement instanceof Collection<?>)
			return ((Collection<?>) inputElement).toArray();
		else
			return new Object[] {};
	}

	@Override
	public void dispose()
	{}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{}
}
