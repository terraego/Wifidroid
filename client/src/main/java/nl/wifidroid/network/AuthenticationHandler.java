package nl.wifidroid.network;

import java.net.InetAddress;

import com.esotericsoftware.kryonet.Connection;

import nl.wifidroid.ConsoleController;
import nl.wifidroid.model.AndroidDevice;
import nl.wifidroid.model.AuthenticationRequest;
import nl.wifidroid.model.AuthenticationResponse;

public class AuthenticationHandler extends MessageHandler {

	private ConsoleController console;

	public AuthenticationHandler(ConsoleController console) {
		this.console = console;
	}

	@Override
	public void connected(Connection connection) {
		String computerName = "Unknown";
		try {
			computerName = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
		}

		AuthenticationRequest request = new AuthenticationRequest();
		request.setComputerName(computerName);
		connection.sendTCP(request);
		console.print("AuthenticationHandler", "request sent");
	}

	@Override
	public int handleMessage(Connection con, Message message) {
		String actionCommand = message.getActionCommand();
		if (actionCommand.equals("authenticate")) {
			if (message.getType() == Message.TYPE_RESPONSE) {
				AuthenticationResponse response = (AuthenticationResponse) message;

				AndroidDevice device = new AndroidDevice();
				device.setModel(response.getModel());
				device.setBrand(response.getBrand());
				device.setSdkLevel(response.getSdkLevel());

				console.print("AuthenticationHandler",
						"authenticated with device: " + device);
			}
		}

		return 0;
	}

}
