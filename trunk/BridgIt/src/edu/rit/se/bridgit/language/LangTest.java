package edu.rit.se.bridgit.language;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;
import edu.rit.se.bridgit.language.model.NameConflictException;

public class LangTest 
{
	public static void main(String...args) 
	throws RecognitionException, InvalidTypeException, NameConflictException
	{
		CharStream stream = new ANTLRStringStream("                                         \n" + 
				"// this is a test program\n" + 
				"/*\n" + 
				"	don't mind me, \n" + 
				"	i'm just a multi-line comment\n" + 
				"*/\n" + 
				"application Awesome {\n" + 
				"	setup {" +
				"      var a:Integer = 0;" +
				"   }\n" + 
				"	main {\n" + 
				"		if a == 0 {\n" + 
				"			a = 7;\n" + 
				"		}\n" + 
				"	}\n" + 
				"}"
		);
		PseudoLexer lexer = new PseudoLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		PseudoParser parser = new PseudoParser(tokens);
		Evaluator result = parser.application();
		System.out.println(result.evaluate(null));
	}
}
