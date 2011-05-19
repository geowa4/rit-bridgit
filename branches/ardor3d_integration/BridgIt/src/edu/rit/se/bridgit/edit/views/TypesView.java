package edu.rit.se.bridgit.edit.views;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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

public class TypesView extends ViewPart implements ISelectionChangedListener, 
	KeyListener, MouseListener, ContentLoadedListener
{
	private static final Logger log = Logger.getLogger(TypesView.class);
	
	private Composite parent;
	private ListViewer list;
	private Label imagePreview;
	private String selectedType;

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
		list.getControl().addKeyListener(this);
		list.getControl().addMouseListener(this);
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
			selectedType = selection.toString();
			String imagePath = 
				GraphicalModelBridgeFactory.getThumbnailForType(selectedType);
			if(!imagePath.equals(""))
			{
				imagePreview.setImage(new Image(parent.getDisplay(), imagePath));
			}
		}
	}

	private void setClipboardContent()
	{
		StringSelection clipboardData = 
			new StringSelection("new " + selectedType + "()");
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(clipboardData, clipboardData);
		log.trace("Snippet \"" + selectedType + 
			"\" has been added to the clipboard.");
	}

	@Override
	public void contentLoaded(Collection<?> content)
	{
		list.setInput(content);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.keyCode == SWT.CR)
		{
			setClipboardContent();
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent e)
	{
		setClipboardContent();
	}

	@Override
	public void mouseDown(MouseEvent e)
	{}

	@Override
	public void mouseUp(MouseEvent e)
	{}
}
