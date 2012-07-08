package nl.wifidroid.network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import nl.wifidroid.model.AuthenticationRequest;
import nl.wifidroid.model.AuthenticationResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class DefaultWifiDroidClient implements WifiDroidClient {

	private Client client;

	public DefaultWifiDroidClient() {
		this.client = new Client();
		
		registerClasses();
	}

	private void registerClasses() {
		Kryo kryo = client.getKryo();
		kryo.register(Message.class);
		kryo.register(AuthenticationRequest.class);
		kryo.register(AuthenticationResponse.class);
	}

	@Override
	public void connect(String host, int timeout) throws IOException {
		client.start();
		client.connect(timeout, host, PORT_TCP, PORT_UDP);
	}

	@Override
	public void disconnect() throws IOException {
		client.close();
	}

	@Override
	public boolean isConnected() throws IOException {
		return client.isConnected();
	}

	@Override
	public void sendMessage(Message message) throws IOException {
		client.sendTCP(message);
	}

	@Override
	public List<String> discoverDevices(int timeout) throws IOException {
		List<String> results = new LinkedList<String>();

		for (InetAddress address : client.discoverHosts(PORT_UDP, timeout)) {
			results.add(address.toString());
		}

		return results;
	}

	@Override
	public void addMessageHandler(MessageHandler handler) {
		client.addListener(handler);
	}

	@Override
	public void removeMessageHandler(MessageHandler handler) {
		client.removeListener(handler);
	}

}
