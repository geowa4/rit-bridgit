package edu.rit.se.bridgit.language.builtin.function;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import edu.rit.se.bridgit.execution.views.Console;
import edu.rit.se.bridgit.execution.views.ConsoleInputListener;
import edu.rit.se.bridgit.language.evaluator.Evaluator;
import edu.rit.se.bridgit.language.evaluator.Scope;
import edu.rit.se.bridgit.language.evaluator.function.Function;
import edu.rit.se.bridgit.language.model.StringType;
import edu.rit.se.bridgit.language.model.Type;
import edu.rit.se.bridgit.language.model.exception.InvalidTypeException;
import edu.rit.se.bridgit.language.model.exception.NameConflictException;

public class InputFunction extends Function
{
	private static final Logger log = Logger.getLogger(InputFunction.class);
	public static final String functionName = "input";

	private boolean inputReceived = true;
	
	public InputFunction()
	{
		setFunctionName(functionName);
		setReturnType(Type.STRING_TYPE);
		setReturnValue(new InputReturn());
	}
	
	private class InputReturn implements Evaluator, ConsoleInputListener
	{
		private Text inputElement;
		
		@Override
		public Type evaluate(Scope scope) throws InvalidTypeException, NameConflictException
		{
			Console console = null;
			try
			{
				console = (Console) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(Console.class.getName());
				console.receiveInput(this);
				ExecutorService executor = Executors.newSingleThreadExecutor();
				Future<String> retVal = executor.submit(new InputHandler(inputElement));
				return new StringType(retVal.get());
			} 
			catch (PartInitException e)
			{
				log.error("Could not attach to the console to receive input.");
			} 
			catch (InterruptedException e)
			{
				log.error("Could not attach to the console to receive input.");
			} 
			catch (ExecutionException e)
			{
				log.error("Could not attach to the console to receive input.");
			}
			return new StringType("");
		}
		
		@Override
		public void handleEvent(Event event)
		{
			if(event.keyCode == SWT.CR)
			{
				log.debug("You entered: " + inputElement.getText());
				inputElement.removeListener(SWT.KeyDown, this);
				inputReceived = true;
				inputElement.notifyAll();
			}
		}

		@Override
		public void setTextInputElement(Text inputElement)
		{
			this.inputElement = inputElement;
		}

		@Override
		public void validateType(Type t) throws InvalidTypeException
		{}
	}
	
	private class InputHandler implements Callable<String>, Listener
	{
		private Text inputElement;
		
		public InputHandler(Text inputElement)
		{
			this.inputElement = inputElement;
			this.inputElement.addListener(SWT.KeyDown, this);
		}

		@Override
		public void handleEvent(Event event)
		{
			if(event.keyCode == SWT.CR)
			{
				log.debug("You entered: " + inputElement.getText());
				inputElement.removeListener(SWT.KeyDown, this);
				inputReceived = true;
			}
		}

		@Override
		public String call() throws Exception
		{
			while(!inputReceived) 
			{
				System.out.println("Infinite loop!");
				Thread.sleep(1000);
			}
			this.inputElement.removeListener(SWT.KeyDown, this);
			return inputElement.getText();
		}
	}
}
