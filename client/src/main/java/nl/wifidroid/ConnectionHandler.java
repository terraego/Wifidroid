package nl.wifidroid;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import nl.wifidroid.network.DefaultWifiDroidClient;
import nl.wifidroid.network.Message;
import nl.wifidroid.network.WifiDroidClient;

public class ConnectionHandler extends ConsoleCommandHandler {

	private WifiDroidClient client;

	public ConnectionHandler(WifiDroidClient client) {
		this.client = client;
	}

	@Override
	public void handleCommand(String command) {
		try {
			if (command.equals("discover")) {
				List<String> discovered = client.discoverDevices(5000);
				if (!discovered.isEmpty()) {
					print("devices found: ");
					for (String address : discovered) {
						print("   " + address);
					}
				} else {
					print("no devices found");
				}
			} else if (command.startsWith("connect")) {
				String ip = command.substring("set host".length());
				client.connect(ip, 5000);
			} else if (command.equals("disconnect")) {
				client.disconnect();
			} else if (command.startsWith("send message")) {
				if (!client.isConnected()) {
					print("not connected");
					return;
				}

				// retrieve the message to send
				String input = command.substring("send message".length());

				// actually send the method
				sendMessage(input);
			}
		} catch (Exception e) {
			error(e);
			try {
				client.disconnect();
			} catch (IOException ex) {
				error(e);
			}
		}
	}

	private void sendMessage(String message) throws IOException {
		if (!client.isConnected()) {
			throw new RuntimeException("not connected");
		}

		Message m = new Message();
		m.setActionCommand(message);
		client.sendMessage(m);
	}
}
