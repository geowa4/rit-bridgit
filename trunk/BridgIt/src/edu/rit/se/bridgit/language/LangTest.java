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
		CharStream stream = new ANTLRStringStream(
				"application Awesome {\n" + 
				"	setup {" +
				"		var a:Integer = 0;" +
				"		function test(x:Integer):Integer {\n" + 
				"			constant d:Integer = 2;\n" + 
				"			var one:Integer = x;\n" + 
				"			return a + one;\n" +
				"		}\n" +
				"   }\n" +
				"\n" + 
				"	main {\n" +
				"		print(typeOf(1));" +
				"		a = 7;" +
				"		a = test(a);" + 
				"		if a == 0 {\n" + 
				"			a = test(6);\n" +
				"		}\n" +
				"		print(a);" + 
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
