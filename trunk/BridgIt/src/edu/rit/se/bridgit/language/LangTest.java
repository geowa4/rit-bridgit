package edu.rit.se.bridgit.language;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.model.InvalidTypeException;

public class LangTest 
{
	public static void main(String...args) throws RecognitionException, InvalidTypeException
	{
		CharStream stream = new ANTLRStringStream("application Awesome { " +
				"setup { " +
//					"constant a:Integer = 0 \n" + 
//					"constant b:Integer = 1 \n" +
//					"constant c:Integer =2 \n" +

					"var x:Integer \n" +
					"var z:Integer = (4 * 8) + (6 + 1 / 3) \n" +

					"var s:String = \"hello\" \n" +
				"} \n" +

				"main {} \n" +
			"}"
		);
		PseudoLexer lexer = new PseudoLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		PseudoParser parser = new PseudoParser(tokens);
		Evaluator result = parser.application();
		System.out.println(result.evaluate(null));
	}
}
