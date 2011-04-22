package edu.rit.se.bridgit.edit.views;

import java.util.Collection;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import edu.rit.se.bridgit.edit.content.ContentLoadedListener;
import edu.rit.se.bridgit.edit.content.StructuredCollectionContentProvider;
import edu.rit.se.bridgit.language.model.bridge.GraphicalModelBridgeFactory;

public class TypesView extends ViewPart implements ISelectionChangedListener, ContentLoadedListener
{
	private Composite parent;
	private ListViewer list;
	private Label imagePreview;

	@Override
	public void createPartControl(Composite parent) 
	{
		this.parent = parent;
		GridLayout grid = new GridLayout(2, true);
		Shell shell = new Shell(parent.getDisplay());
		shell.setLayout(grid);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		list = new ListViewer(parent);
		IStructuredContentProvider content = new StructuredCollectionContentProvider();
		list.setContentProvider(content);
		list.setInput(GraphicalModelBridgeFactory.getAvailableClasses());
		list.addSelectionChangedListener(this);
		GraphicalModelBridgeFactory.addContentLoadedListener(this);
		imagePreview = new Label(parent, SWT.NO_FOCUS);
	}

	@Override
	public void setFocus() 
	{
		list.getControl().setFocus();
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();
		if(selection != null)
		{
			String imagePath = 
				GraphicalModelBridgeFactory.getThumbnailForType(selection.toString());
			if(!imagePath.equals(""))
			{
				imagePreview.setImage(new Image(parent.getDisplay(), imagePath));
			}
		}
	}

	@Override
	public void contentLoaded(Collection<?> content)
	{
		list.setInput(content);
	}
}
