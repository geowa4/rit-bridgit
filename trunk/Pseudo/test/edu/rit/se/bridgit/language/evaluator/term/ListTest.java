package edu.rit.se.bridgit.language.evaluator.term;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.PseudoException;

public class ListTest
{
	private ListEvaluator eval;
	private Scope scope;
	
	@Before
	public void setUp()
	{
		eval = new ListEvaluator();
		scope = new Scope(null);
	}
	
	@Test
	public void listsCanAcceptAlTypesOfElements() throws PseudoException
	{
		eval.addTerm(new StringEvaluator("1"));
		eval.addTerm(new IntegerEvaluator(2));
		eval.addTerm(new DoubleEvaluator(3d));
		eval.addTerm(new ListEvaluator() {{
			addTerm(new ListEvaluator());
		}});
		Type t = eval.evaluate(scope);
		assertEquals("List must contain four elements.", 4, ((List<?>) t.getValue()).size());
	}
}
