package nl.wifidroid;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author Maarten Blokker
 */
public abstract class ConsoleCommandHandler {
	protected ConsoleController console;

	public abstract void handleCommand(String command);

	public void setConsole(ConsoleController console) {
		this.console = console;
	}

	public ConsoleController getConsole() {
		return console;
	}

	public void print(String message) {
		console.print("[" + getClass().getSimpleName() + "] " + message);
	}

	public void error(Exception e) {
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));

		print(writer.toString());
	}
}
