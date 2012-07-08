package nl.wifidroid.network;

import java.io.IOException;
import java.util.List;

import nl.wifidroid.model.AuthenticationRequest;
import nl.wifidroid.model.AuthenticationResponse;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class DefaultWifiDroidServer implements WifiDroidServer {

	private Server server;
	private boolean running;

	public DefaultWifiDroidServer() {
		server = new Server() {
			protected Connection newConnection() {
				return new DeviceConnection();
			}
		};
		
		registerClasses();
	}

	private void registerClasses() {
		Kryo kryo = server.getKryo();
		kryo.register(Message.class);
		kryo.register(AuthenticationRequest.class);
		kryo.register(AuthenticationResponse.class);
	}

	public void start() throws IOException {
		server.start();
		server.bind(PORT_TCP, PORT_UDP);
		running = true;
	}

	public void stop() throws IOException {
		server.close();
		running = false;
	}

	public boolean isRunning() throws IOException {
		return running;
	}

	public void addMessageHandler(MessageHandler handler) {
		server.addListener(handler);
	}

	public void removeMessageHandler(MessageHandler handler) {
		server.removeListener(handler);
	}
}
