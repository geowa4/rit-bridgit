package edu.rit.se.bridgit.edit.views;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import edu.rit.se.bridgit.language.model.bridge.GraphicalModelBridgeFactory;

public class TypesView extends ViewPart implements ISelectionChangedListener
{
	Composite parent;
	ListViewer list;
	Label imagePreview;

	@Override
	public void createPartControl(Composite parent) 
	{
		this.parent = parent;
		GridLayout grid = new GridLayout(2, true);
		Shell shell = new Shell(parent.getDisplay());
		shell.setLayout(grid);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		list = new ListViewer(parent);
		list.setContentProvider(new TypeContentProvider());
		list.setLabelProvider(new TypeLabelProvider());
		list.setInput(GraphicalModelBridgeFactory.getAvailableClasses());
		list.addSelectionChangedListener(this);
		imagePreview = new Label(parent, 0);
	}

	@Override
	public void setFocus() 
	{
		list.getControl().setFocus();
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		imagePreview.setImage(new Image(parent.getDisplay(), 
				GraphicalModelBridgeFactory.getThumbnailForType(selection.getFirstElement().toString())));
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
	{}

	@Override
	public void dispose()
	{}

	@Override
	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener)
	{}

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