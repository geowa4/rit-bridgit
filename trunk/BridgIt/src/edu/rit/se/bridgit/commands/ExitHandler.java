package edu.rit.se.bridgit.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

public class ExitHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Exit the application and return null upon completion
		HandlerUtil.getActiveWorkbenchWindow(event).close();
		return null;
	}
}
