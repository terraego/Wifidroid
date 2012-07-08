package nl.wifidroid.network;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 8450364740092622833L;

	public static final int TYPE_UNDEFINED = 0;
	public static final int TYPE_REQUEST = 1;
	public static final int TYPE_RESPONSE = 2;

	private int type;
	private String actionCommand;

	public Message() {

	}

	public Message(int type, String actionCommand) {
		this.type = type;
		this.actionCommand = actionCommand;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getActionCommand() {
		return actionCommand;
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

	@Override
	public String toString() {
		String typeString = "undefined";

		if (type == TYPE_REQUEST) {
			typeString = "request";
		} else if (type == TYPE_RESPONSE) {
			typeString = "response";
		}
		
		return "[command=" + actionCommand + ",type=" + typeString + "]";
	}
}
