package edu.rit.se.bridgit.edit.views;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import edu.rit.se.bridgit.Activator;
import edu.rit.se.bridgit.edit.content.StructuredCollectionContentProvider;

public class SnippetView extends ViewPart implements ISelectionChangedListener
{
	private static final Logger log = Logger.getLogger(SnippetView.class);
	private ListViewer list;
	private Text preview;
	private Properties snippets;

	@Override
	public void createPartControl(Composite parent) 
	{
		GridLayout grid = new GridLayout(2, true);
		Shell shell = new Shell(parent.getDisplay());
		shell.setLayout(grid);
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		list = new ListViewer(parent);
		IStructuredContentProvider content = new StructuredCollectionContentProvider();
		list.setContentProvider(content);
		list.setInput(buildListInput());
		list.addSelectionChangedListener(this);
		preview = new Text(parent, SWT.MULTI | SWT.LEFT | SWT.READ_ONLY);
	}

	private Collection<String> buildListInput()
	{
		URL confURL = Activator.getDefault().getBundle().getEntry("snippets.properties");
		Collection<String> snippetSet = new TreeSet<String>();
		try
		{
			snippets = new Properties();
			snippets.load(new FileInputStream(FileLocator.toFileURL(confURL).getFile()));
			for(Object s : snippets.keySet())
			{
				snippetSet.add(s.toString().replaceAll("\\.", " "));
			}
		} 
		catch (IOException e)
		{
			log.trace("Could not load snippets.");
		}
		return snippetSet;
	}

	@Override
	public void setFocus()
	{
		list.getControl().setFocus();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event)
	{
		Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();
		if(selection != null)
		{
			String selectedSnippet = selection.toString();
			selectedSnippet = selectedSnippet.replaceAll("\\s+", ".");
			preview.setText(snippets.getProperty(selectedSnippet));
		}
	}

}
