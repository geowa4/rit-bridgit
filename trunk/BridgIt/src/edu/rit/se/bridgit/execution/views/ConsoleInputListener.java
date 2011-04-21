package edu.rit.se.bridgit.execution.views;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public interface ConsoleInputListener extends Listener
{
	public void setTextInputElement(Text inputElement);
}
