package edu.rit.bridgit.edit.editors.model;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class ProgramEditorInput implements IEditorInput {
	private final String inputString = new String();
	
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Program Editor Input";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "Program Editor";
	}

}
