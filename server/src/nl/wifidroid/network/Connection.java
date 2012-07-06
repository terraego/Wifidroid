package nl.wifidroid.network;

import java.net.InetAddress;

public abstract class Connection {

	public abstract void Disconnect();
	public abstract void Connect(InetAddress address);
	public abstract void Close();
}
