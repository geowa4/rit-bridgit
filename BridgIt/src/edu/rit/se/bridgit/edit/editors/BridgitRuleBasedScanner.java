package edu.rit.se.bridgit.edit.editors;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

public class BridgitRuleBasedScanner extends RuleBasedPartitionScanner {
	// Define the partitions
	public static final String SINGLELINE_COMMENT = "Single-line Comment";
	public static final String MULTILINE_COMMENT = "Multi-line Comment";
	public static final String[] PARTITION_TYPES = { SINGLELINE_COMMENT, MULTILINE_COMMENT };

	/**
	 * JavaPartitionScanner constructor
	 */
	public BridgitRuleBasedScanner() {
		// Create the tokens to go with the partitions
		IToken singlelineComment = new Token(SINGLELINE_COMMENT);
		IToken multilineComment = new Token(MULTILINE_COMMENT);

		// Add rules
		IPredicateRule[] rules = new IPredicateRule[2];

		// Single Comments: Begin with //, end with a new line
		rules[0] = new MultiLineRule("//", "\n", singlelineComment, (char) 0, true);

		// Muli Comments: Begin with /*, and with */ later.
		rules[1] = new MultiLineRule("/*", "*/", multilineComment, (char) 0,
				true);

		// Set the rules
		setPredicateRules(rules);
	}
}
