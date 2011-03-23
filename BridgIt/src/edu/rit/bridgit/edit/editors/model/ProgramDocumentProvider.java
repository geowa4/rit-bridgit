package edu.rit.bridgit.edit.editors.model;

import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProviderExtension;
import org.eclipse.ui.texteditor.IElementStateListener;

public class ProgramDocumentProvider implements IDocumentProvider, IDocumentProviderExtension {
	// The internal document of the provider
	Document m_Document = new Document();
	AnnotationModel m_AnnotationModel = new AnnotationModel();
	
	
	@Override
	public void connect(Object element) throws CoreException {
		// TODO Auto-generated method stub
	}

	@Override
	public void disconnect(Object element) {
		// TODO Auto-generated method stub

	}

	@Override
	public IDocument getDocument(Object element) {
		// TODO Auto-generated method stub
		return m_Document;
	}

	@Override
	public void resetDocument(Object element) throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		try {
			FileWriter writer = new FileWriter("Output Program.txt");
			monitor.beginTask("Saving file...", 0);
			writer.write(document.get());
			monitor.done();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public long getModificationStamp(Object element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getSynchronizationStamp(Object element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDeleted(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mustSaveDocument(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSaveDocument(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IAnnotationModel getAnnotationModel(Object element) {
		// TODO Auto-generated method stub
		return m_AnnotationModel;
	}

	@Override
	public void aboutToChange(Object element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changed(Object element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addElementStateListener(IElementStateListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeElementStateListener(IElementStateListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReadOnly(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isModifiable(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void validateState(Object element, Object computationContext)
			throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStateValidated(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void updateStateCache(Object element) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCanSaveDocument(Object element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IStatus getStatus(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void synchronize(Object element) throws CoreException {
		// TODO Auto-generated method stub
		
	}

}

//package edu.rit.bridgit.edit.editors.model;
//
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.jface.operation.IRunnableContext;
//import org.eclipse.jface.text.IDocument;
//import org.eclipse.jface.text.source.AnnotationModel;
//import org.eclipse.jface.text.source.IAnnotationModel;
//import org.eclipse.ui.texteditor.AbstractDocumentProvider;
//
//public class ProgramDocumentProvider extends AbstractDocumentProvider {
//
//	@Override
//	protected IDocument createDocument(Object element) throws CoreException {
//		// TODO Auto-generated method stub
//		return new ProgramDocument();
//	}
//
//	@Override
//	protected IAnnotationModel createAnnotationModel(Object element)
//			throws CoreException {
//		// TODO Auto-generated method stub
//		return new AnnotationModel();
//	}
//
//	@Override
//	protected void doSaveDocument(IProgressMonitor monitor, Object element,
//			IDocument document, boolean overwrite) throws CoreException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
