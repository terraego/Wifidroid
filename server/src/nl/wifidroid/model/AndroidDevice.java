package nl.wifidroid.model;

import java.io.Serializable;

public class AndroidDevice implements Serializable {

	private static final long serialVersionUID = 2645776247273624019L;

	private String model;
	private String brand;
	private int sdkLevel;

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
