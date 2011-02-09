package edu.rit.bridgit.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Represents the model for the program editor. This is a string
 * that represents the program being built, within the editor.
 * 
 * @author Bradley R. Blankenship
 *
 */
public class ProgramEditorModel implements PropertyChangeListener {
	// The program to be executed
	String ms_ProgramString;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Get the new value
		Object newValue = evt.getNewValue();
		
		// If it's a string, set our internal value to it
		if(evt.getNewValue() instanceof String)
			ms_ProgramString = (String) newValue;
	}
}
