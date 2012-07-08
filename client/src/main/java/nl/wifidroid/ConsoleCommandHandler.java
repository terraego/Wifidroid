package nl.wifidroid;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author Maarten Blokker
 */
public abstract class ConsoleCommandHandler {

	protected ConsoleController console;

	public void setConsole(ConsoleController console) {
		this.console = console;
	}

	public ConsoleController getConsole() {
		return console;
	}

	public void print(String message) {
		console.print(getClass().getSimpleName(), message);
	}

	public void error(Exception e) {
		console.print(getClass().getSimpleName(), e);
	}

	public abstract void handleCommand(String command);

}
