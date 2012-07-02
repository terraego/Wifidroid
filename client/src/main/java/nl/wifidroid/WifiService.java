package nl.wifidroid;

/**
 * 
 * @author Maarten Blokker
 */
public class WifiService {

	private ConsoleController console;

	public WifiService() {
		console = new ConsoleController();
		console.addConsoleCommandHandler(new ConnectionHandler());
		console.show();
	}

	public void start() {
		console.print("show message");
	}

	public static void main(String[] args) {
		WifiService server = new WifiService();
		server.start();
	}

}
