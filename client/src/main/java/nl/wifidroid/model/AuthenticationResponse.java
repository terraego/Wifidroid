package nl.wifidroid.model;

import java.io.Serializable;

import nl.wifidroid.network.Message;

public class AuthenticationResponse extends Message implements Serializable {

	private static final long serialVersionUID = 1625591167363803147L;
	
	private String model;
	private String brand;
	private int sdkLevel;

	public AuthenticationResponse() {
		this(null, null, 0);
	}

	public AuthenticationResponse(String model, String brand, int sdkLevel) {
		this.model = model;
		this.brand = brand;
		this.sdkLevel = sdkLevel;

		setType(TYPE_RESPONSE);
		setActionCommand("authenticate");
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getSdkLevel() {
		return sdkLevel;
	}

	public void setSdkLevel(int sdkLevel) {
		this.sdkLevel = sdkLevel;
	}

}
