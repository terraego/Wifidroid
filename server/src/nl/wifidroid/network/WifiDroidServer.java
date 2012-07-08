package nl.wifidroid.network;

import java.io.IOException;

public interface WifiDroidServer {
	
	public static final int PORT_TCP = 12345;
	public static final int PORT_UDP = 12346;

	public void start() throws IOException;

	public void stop() throws IOException;

	public boolean isRunning() throws IOException;

	public void addMessageHandler(MessageHandler handler);

	public void removeMessageHandler(MessageHandler handler);

}
