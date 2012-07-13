package nl.wifidroid.model;

public class Photo {
	private int id;
	private byte[] data;

	public Photo() {

	}

	public Photo(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
