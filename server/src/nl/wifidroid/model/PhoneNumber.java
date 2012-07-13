package nl.wifidroid.model;

public class PhoneNumber {

	private String number;
	private int type;

	public PhoneNumber() {

	}

	public PhoneNumber(String number, int type) {
		this.number = number;
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return number;
	}
}
