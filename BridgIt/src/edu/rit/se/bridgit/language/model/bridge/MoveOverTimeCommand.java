package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

import com.ardor3d.math.Vector3;


import edu.rit.se.bridgit.language.model.Type;

public abstract class MoveOverTimeCommand extends Command {

	float totalTime = -1;
	float elapsedTime = 0;
	
	Vector3 offset;
	Vector3 startPos;
	
	public MoveOverTimeCommand(String methodName, List<Type> arguments, GraphicalBridge inBridge) {
		super(methodName, arguments, inBridge);
		
		offset = new Vector3(Float.parseFloat(arguments.get(0).toString()), 
				Float.parseFloat(arguments.get(1).toString()), 
				Float.parseFloat(arguments.get(2).toString()));
	}

	public MoveOverTimeCommand() {
		super();
	}

	boolean initialized()
	{
		return (totalTime != -1);
	}
	
	void setTime(float in_time)
	{
		totalTime = in_time;
	}
	
	void setInitialPosition(Vector3 in_initialPos)
	{
		startPos = in_initialPos;
	}
	
	protected Vector3 update(double delta)
	{
		elapsedTime += delta;
		if(elapsedTime > totalTime)
		{
			elapsedTime = totalTime;
		}
		
		float scalar = elapsedTime/totalTime;
		return startPos.addLocal((offset.multiplyLocal(scalar)));
	}
	
	protected boolean finished()
	{
		return elapsedTime == totalTime;
	}

}
