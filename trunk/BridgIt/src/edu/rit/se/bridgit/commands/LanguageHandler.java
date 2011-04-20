package edu.rit.se.bridgit.commands;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import edu.rit.se.bridgit.edit.editors.ProgramEditor;
import edu.rit.se.bridgit.language.CustomPseudoParser;
import edu.rit.se.bridgit.language.PseudoLexer;
import edu.rit.se.bridgit.language.PseudoParser;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

/**
 * Special handler which is used by the play command to execute the given
 * program text.
 * 
 * @author Bradley R. Blankenship
 *
 */
public class LanguageHandler implements Runnable
{
	/**
	 * Evaluates the program.
	 * 
	 * @param program - The actual text of the program to evaluate.
	 * 
	 * @return Output from the evaluater.
	 */
	public static String evaluateProgram(String program)
	{
		String retVal = null;
		CharStream stream = new ANTLRStringStream(program);
		PseudoLexer lexer = new PseudoLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		PseudoParser parser = new CustomPseudoParser(tokens);
		Evaluator result;
		try
		{
			result = parser.application();
			retVal = result.evaluate(null).toString();
		}
		catch (RecognitionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		catch (InvalidTypeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NameConflictException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}

	@Override
	public void run() {
		// Call the handling function
		evaluateProgram(ProgramEditor.m_Text.getText());
	}
}
