package nl.wifidroid.model;

public class EmailAddress {
	private String address;
	private int type;

	public EmailAddress() {
	}

	public EmailAddress(String address, int type) {
		this.address = address;
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return getAddress();
	}
}
