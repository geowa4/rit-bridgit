package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

import com.ardor3d.math.Quaternion;
import com.ardor3d.math.Vector3;

import edu.rit.se.bridgit.language.model.Type;

public class RotateOverTimeCommand extends MoveOverTimeCommand {

	public RotateOverTimeCommand(String methodName, List<Type> arguments, GraphicalBridge inBridge) 
	{
		super(methodName, arguments, inBridge);
	}
	
	public RotateOverTimeCommand()
	{
		super();
	}

	@Override
	public boolean execute(double delta) {
		
		Vector3 yaw_pitch_roll = update(delta);
		
		Quaternion quat = new Quaternion();
		quat.fromEulerAngles(yaw_pitch_roll.getX(), yaw_pitch_roll.getY(), yaw_pitch_roll.getZ());
		bridge.render_node.setRotation(quat);
		
		return finished();
	}

	@Override
	public Command clone(String inMethod, List<Type> inArguments,
			GraphicalBridge inBridge) {
		
		return new RotateOverTimeCommand(inMethod, inArguments, inBridge);
	}
}
