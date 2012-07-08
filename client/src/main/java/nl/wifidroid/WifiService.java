package nl.wifidroid;

import nl.wifidroid.network.AuthenticationHandler;
import nl.wifidroid.network.DefaultWifiDroidClient;
import nl.wifidroid.network.WifiDroidClient;

/**
 * 
 * @author Maarten Blokker
 */
public class WifiService {
	
	private WifiDroidClient client;
	private ConsoleController console;

	public WifiService() {
		client = new DefaultWifiDroidClient();
		
		console = new ConsoleController();
		console.addConsoleCommandHandler(new ConnectionHandler(client));
		console.show();
		
		client.addMessageHandler(new AuthenticationHandler(console));
	}

	public void start() {
		
	}

	public static void main(String[] args) {
		WifiService server = new WifiService();
		server.start();
	}

}
