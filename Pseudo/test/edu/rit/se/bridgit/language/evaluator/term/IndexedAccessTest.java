package edu.rit.se.bridgit.language.evaluator.term;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.PseudoException;
import edu.rit.se.bridgit.language.model.exception.UnsupportedMemberException;

public class IndexedAccessTest
{
	private IndexedAccessEvaluator eval;
	private Scope scope;
	
	@Before
	public void setUp() 
	{
		eval = new IndexedAccessEvaluator();
		scope = new Scope(null);
	}
	
	@Test
	public void canAccessAnyElementInList() throws PseudoException 
	{
		eval.setTerm(new ListEvaluator() {{
			addTerm(new IntegerEvaluator(1));
			addTerm(new StringEvaluator("2"));
		}});
		eval.setIndex(new IntegerEvaluator(1));
		Type t = eval.evaluate(scope);
		assertEquals("The second element must be retrieved.", "2", t.getValue());
	}
	
	@Test
	public void canAccessAnyElementInString() throws PseudoException 
	{
		eval.setTerm(new StringEvaluator("12"));
		eval.setIndex(new IntegerEvaluator(0));
		Type t = eval.evaluate(scope);
		assertEquals("The first element must be retrieved.", "1", t.getValue());
	}
	
	@Test(expected=UnsupportedMemberException.class)
	public void errorAccessingElementInZeroLengthList() throws PseudoException 
	{
		eval.setTerm(new ListEvaluator());
		eval.setIndex(new IntegerEvaluator(0));
		eval.evaluate(scope);
	}
	
	@Test(expected=UnsupportedMemberException.class)
	public void errorAccessingElementInZeroLengthString() throws PseudoException 
	{
		eval.setTerm(new StringEvaluator(""));
		eval.setIndex(new IntegerEvaluator(1));
		eval.evaluate(scope);
	}
	
	@Test(expected=UnsupportedMemberException.class)
	public void errorAccessingElementAtIndexGreaterThanOrEqualToLengthOfList() throws PseudoException  
	{
		eval.setTerm(new ListEvaluator() {{
			addTerm(new IntegerEvaluator(1));
			addTerm(new StringEvaluator("2"));
		}});
		eval.setIndex(new IntegerEvaluator(2));
		eval.evaluate(scope);
	}

	@Test(expected=UnsupportedMemberException.class)
	public void errorAccessingElementAtIndexGreaterThanOrEqualToLengthOfString() throws PseudoException 
	{
		eval.setTerm(new StringEvaluator("12"));
		eval.setIndex(new IntegerEvaluator(2));
		eval.evaluate(scope);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void cannotAccessElementsInOtherTypes() throws PseudoException 
	{
		eval.setTerm(new DoubleEvaluator(12.0));
		eval.setIndex(new IntegerEvaluator(0));
		eval.evaluate(scope);
	}
	
	@Test(expected=InvalidTypeException.class)
	public void cannotUseAnythingButIntegerAsIndex() throws PseudoException
	{
		eval.setTerm(new StringEvaluator("12"));
		eval.setIndex(new DoubleEvaluator(0.0));
		eval.evaluate(scope);
	}
}
