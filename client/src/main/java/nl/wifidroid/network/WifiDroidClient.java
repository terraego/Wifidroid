package nl.wifidroid.network;

import java.io.IOException;
import java.util.List;

public interface WifiDroidClient {
	
	public static final int PORT_TCP = 12345;
	public static final int PORT_UDP = 12346;

	public void connect(String host, int timeout) throws IOException;

	public void disconnect() throws IOException;

	public boolean isConnected() throws IOException;

	public List<String> discoverDevices(int timeout) throws IOException;
	
	public void sendMessage(Message message) throws IOException;

	public void addMessageHandler(MessageHandler handler);

	public void removeMessageHandler(MessageHandler handler);
}
