package nl.wifidroid.network;

import com.esotericsoftware.kryonet.Connection;

public class DeviceConnection extends Connection {

	private String computerName;

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
}
