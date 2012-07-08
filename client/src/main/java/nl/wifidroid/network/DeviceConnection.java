package nl.wifidroid.network;

import com.esotericsoftware.kryonet.Connection;

import nl.wifidroid.model.AndroidDevice;

public class DeviceConnection extends Connection {

	private AndroidDevice device;

	public AndroidDevice getDevice() {
		return device;
	}

	public void setDevice(AndroidDevice device) {
		this.device = device;
	}
}
