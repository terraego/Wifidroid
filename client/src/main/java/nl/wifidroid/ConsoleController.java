package nl.wifidroid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * 
 * @author Maarten Blokker
 */
public class ConsoleController implements ActionListener {

	public static final String TAG = "User";
	
	private final List<ConsoleCommandHandler> handlers;
	private ConsoleView view;

	public ConsoleController() {
		handlers = new LinkedList<ConsoleCommandHandler>();
		view = new ConsoleView();
		view.setListener(this);
	}

	public void show() {
		JFrame frame = new JFrame();
		frame.setTitle("WifiDroid console");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(view);
		frame.setMinimumSize(new Dimension(800, 480));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void print(String tag, String message) {
		view.addMessage(tag, message);
	}

	public void print(String tag, Exception e) {
		print(tag, null, e);
	}

	public void print(String tag, String message, Exception e) {
		if (message != null) {
			view.addMessage(tag, message, Color.RED);
		}

		Writer writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		view.addMessage(tag, writer.toString(), Color.RED);
	}

	public void addConsoleCommandHandler(ConsoleCommandHandler handler) {
		synchronized (handlers) {
			if (!handlers.contains(handler)) {
				handlers.add(handler);
				handler.setConsole(this);
			}
		}
	}

	public void removeConsoleCommandHandler(ConsoleCommandHandler handler) {
		synchronized (handlers) {
			handlers.remove(handler);
			handler.setConsole(null);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		String command = view.getUserInput();
		print(TAG, command);
		synchronized (handlers) {
			try {
				for (ConsoleCommandHandler handler : handlers) {
					handler.handleCommand(command);
				}
			} catch (Exception e) {
				print("", "failed to proccess command", e);
			} finally {
				view.clearUserInput();
			}
		}
	}
}
