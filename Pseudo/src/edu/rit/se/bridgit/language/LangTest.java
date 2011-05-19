package edu.rit.se.bridgit.language;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class LangTest 
{
	public static void main(String...args) 
	throws RecognitionException, PseudoException
	{
		CharStream stream = new ANTLRStringStream(
				"application Awesome {\n" + 
				"	setup {\n" +
				"\n" +
				"   }\n" +
				"\n" + 
				"	main {\n" +
				"		print(typeOf(1) == \"Integer\" != false);\n" +
				"	}\n" + 
				"}"
		);
		PseudoLexer lexer = new PseudoLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		PseudoParser parser = new PseudoParser(tokens);
		Evaluator result = parser.application();
		result.evaluate(null);
	}
}
