package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;


import edu.rit.se.bridgit.language.model.Type;

public class ScaleOverTimeCommand extends overTimeCommand {

	public ScaleOverTimeCommand(String methodName, List<Type> arguments, GraphicalBridge inBridge) {
		super(methodName, arguments, inBridge);
	}

	public ScaleOverTimeCommand() {
		super();
	}

	@Override
	public boolean execute(double delta) {
		
		bridge.render_node.setScale(update(delta));
		
		return finished();
	}

	@Override
	public Command clone(String inMethod, List<Type> inArguments,
			GraphicalBridge inBridge) {
		
		return new ScaleOverTimeCommand(inMethod, inArguments, inBridge);
	}
}
