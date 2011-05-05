package edu.rit.se.bridgit.language.model.bridge;

import java.util.List;

import com.jme.math.Vector3f;

import edu.rit.se.bridgit.language.model.Type;

public class RotateOverTimeCommand extends Command {

	float totalTime = -1;
	float elapsedTime = 0;
	
	Vector3f offset;
	Vector3f startPos;
	
	public RotateOverTimeCommand(String methodName, List<Type> arguments) {
		super(methodName, arguments);
		
		offset = new Vector3f(Float.parseFloat(arguments.get(0).toString()), 
				Float.parseFloat(arguments.get(1).toString()), 
				Float.parseFloat(arguments.get(2).toString()));
	}

	boolean initialized()
	{
		return (totalTime != -1);
	}
	
	void setTime(float in_time)
	{
		totalTime = in_time;
	}
	
	void setInitialPosition(Vector3f in_initialPos)
	{
		startPos = in_initialPos;
	}
	
	Vector3f update(double delta)
	{
		elapsedTime += delta;
		if(elapsedTime > totalTime)
		{
			elapsedTime = totalTime;
		}
		
		float scaler = elapsedTime/totalTime;
		return startPos.add(offset.mult(scaler));
	}
	
	boolean finished()
	{
		return elapsedTime == totalTime;
	}
}
