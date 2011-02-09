package edu.rit.se.bridgit.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.monklypse.core.JMEComposite;


import edu.rit.bridgit.edit.editors.model.ProgramEditorInput;
import edu.rit.se.bridgit.monklypse.RenderCanvas;

public class WorldView extends ViewPart {

	public WorldView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.None);
        container.setLayout(new GridLayout(1, true));
        //for (int i = 0; i < 4; ++i) {
                JMEComposite composite = new JMEComposite(container,
                                new RenderCanvas());
                composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        //}

             // Attempt to attach the program editor to this view
        		try {
        			IEditorPart editor = getSite().getPage().openEditor(new ProgramEditorInput(),
        					"edu.rit.se.bridgit.edit.editors.programeditor");
        		} catch (PartInitException e) {
        			e.printStackTrace();
        		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
