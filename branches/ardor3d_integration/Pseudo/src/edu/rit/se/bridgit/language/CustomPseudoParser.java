package edu.rit.se.bridgit.language;

import org.antlr.runtime.TokenStream;
import org.apache.log4j.Logger;

public class CustomPseudoParser extends PseudoParser
{
	private static final Logger log = Logger.getLogger(CustomPseudoParser.class);
	private static final String SYNTAX_ERROR = "Syntax error: ";
	
	public CustomPseudoParser(TokenStream input)
	{
		super(input);
	}

	@Override
	public void emitErrorMessage(String msg)
	{
		log.error(SYNTAX_ERROR + msg);
	}
}
