package nl.wifidroid.model;

import java.io.Serializable;

import nl.wifidroid.network.Message;

public class AuthenticationRequest extends Message implements Serializable {

	private static final long serialVersionUID = -4310287421793486531L;

	private String computerName;

	public AuthenticationRequest() {
		this(null);
	}

	public AuthenticationRequest(String computerName) {
		this.computerName = computerName;

		setType(TYPE_REQUEST);
		setActionCommand("authenticate");
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

}
