package nl.wifidroid;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Maarten Blokker
 */
public class ConsoleController implements ActionListener {

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
        frame.setMinimumSize(new Dimension(650, 480));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void print(String message) {
        view.addMessage(message);
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
        view.addMessage("[User] " + command);
        synchronized (handlers) {
            try {
                for (ConsoleCommandHandler handler : handlers) {
                    handler.handleCommand(command);
                }
            } catch (Exception e) {
                view.addMessage("Failed to execute action: " + e.getLocalizedMessage());
            }
        }
        view.clearUserInput();
    }
}
