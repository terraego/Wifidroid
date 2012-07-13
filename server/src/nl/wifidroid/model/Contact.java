package nl.wifidroid.model;

import java.util.List;

public class Contact {

	private int id;
	private String name;
	private List<PhoneNumber> phoneNumbers;
	private List<EmailAddress> emailsAddresses;
	private Photo photo;

	public Contact(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<EmailAddress> getEmailsAddresses() {
		return emailsAddresses;
	}

	public void setEmailsAddresses(List<EmailAddress> emailsAddresses) {
		this.emailsAddresses = emailsAddresses;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return getName();
	}
}
