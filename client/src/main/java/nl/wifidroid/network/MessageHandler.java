package nl.wifidroid.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public abstract class MessageHandler extends Listener {

	@Override
	public void received(Connection con, Object o) {
		if (o instanceof Message) {
			handleMessage(con, (Message) o);
		}
	}

	public abstract int handleMessage(Connection con, Message message);
}
