package nl.wifidroid;

import java.util.LinkedList;
import java.util.List;

import nl.wifidroid.model.Contact;
import nl.wifidroid.model.EmailAddress;
import nl.wifidroid.model.PhoneNumber;
import nl.wifidroid.model.Photo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class ContactsLoader {

	private static ContactsLoader loader = null;

	public static synchronized ContactsLoader getLoader() {
		if (loader == null) {
			loader = new ContactsLoader();
		}
		return loader;
	}

	public List<Contact> queryContacts(ContentResolver resolver,
			boolean fullQuery) {

		List<Contact> result = new LinkedList<Contact>();
		for (int contactId : queryContactIds(resolver)) {
			List<PhoneNumber> numbers = queryPhoneNumbers(resolver, contactId);
			if (!numbers.isEmpty()) {
				Contact contact = new Contact(contactId);
				contact.setPhoneNumbers(numbers);
				contact.setName(queryDisplayName(resolver, contactId));
				contact.setEmailsAddresses(queryEmailAddresses(resolver,
						contactId));
				contact.setPhoto(queryPhoto(resolver, contactId, fullQuery));
				result.add(contact);
			}
		}

		return result;
	}

	public List<Integer> queryContactIds(ContentResolver resolver) {
		final String[] projection = new String[] { RawContacts.CONTACT_ID,
				RawContacts.DELETED };

		final Cursor rawContacts = resolver.query(RawContacts.CONTENT_URI,
				projection, null, null, null);

		final int contactIdColumnIndex = rawContacts
				.getColumnIndex(RawContacts.CONTACT_ID);
		final int deletedColumnIndex = rawContacts
				.getColumnIndex(RawContacts.DELETED);

		List<Integer> result = new LinkedList<Integer>();
		try {
			if (rawContacts.moveToFirst()) {
				while (!rawContacts.isAfterLast()) {
					int id = rawContacts.getInt(contactIdColumnIndex);
					if (rawContacts.getInt(deletedColumnIndex) == 0
							&& !result.contains(id)) {
						result.add(id);
					}
					rawContacts.moveToNext();
				}
			}
		} finally {
			rawContacts.close();
		}

		return result;
	}

	private String queryDisplayName(ContentResolver resolver, int contactId) {
		final String[] projection = new String[] { Contacts.DISPLAY_NAME };

		String name = null;
		final Cursor cursor = resolver.query(Contacts.CONTENT_URI, projection,
				Contacts._ID + "=?", // filter entries on the basis of the
										// contact id
				new String[] { String.valueOf(contactId) }, // the parameter to
															// which the contact
															// id column is
															// compared to
				null);

		if (cursor.moveToFirst()) {
			name = cursor.getString(cursor
					.getColumnIndex(Contacts.DISPLAY_NAME));

		}

		cursor.close();
		return name;
	}

	private List<PhoneNumber> queryPhoneNumbers(ContentResolver resolver,
			int contactId) {
		List<PhoneNumber> result = new LinkedList<PhoneNumber>();
		final String[] projection = new String[] { Phone.NUMBER, Phone.TYPE, };

		final Cursor phone = resolver.query(Phone.CONTENT_URI, projection,
				Data.CONTACT_ID + "=?",
				new String[] { String.valueOf(contactId) }, null);

		if (phone.moveToFirst()) {
			final int contactNumberColumnIndex = phone
					.getColumnIndex(Phone.NUMBER);
			final int contactTypeColumnIndex = phone.getColumnIndex(Phone.TYPE);

			while (!phone.isAfterLast()) {
				final String number = phone.getString(contactNumberColumnIndex);
				final int type = phone.getInt(contactTypeColumnIndex);
				result.add(new PhoneNumber(number, type));

				phone.moveToNext();
			}

		}
		phone.close();

<<<<<<< HEAD
		return result;
	}

	private List<EmailAddress> queryEmailAddresses(ContentResolver resolver,
			int contactId) {
		List<EmailAddress> result = new LinkedList<EmailAddress>();
		final String[] projection = new String[] { CommonDataKinds.Email.DATA,
				CommonDataKinds.Email.TYPE };

		final Cursor email = resolver.query(CommonDataKinds.Email.CONTENT_URI,
				projection, Data.CONTACT_ID + "=?",
				new String[] { String.valueOf(contactId) }, null);

		if (email.moveToFirst()) {
			final int contactEmailColumnIndex = email
					.getColumnIndex(CommonDataKinds.Email.DATA);
			final int contactTypeColumnIndex = email
					.getColumnIndex(CommonDataKinds.Email.TYPE);

			while (!email.isAfterLast()) {
				final String address = email.getString(contactEmailColumnIndex);
				final int type = email.getInt(contactTypeColumnIndex);
				result.add(new EmailAddress(address, type));
				email.moveToNext();
=======
			Cursor emails = resolver.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
							+ contactId, null, null);
			while (emails.moveToNext()) {
				// This would allow you get several email addresses
				/*String emailAddress = emails
						.getString(emails
								.getColumnIndex(ontactsContract.CommonDataKinds.));*/
>>>>>>> 1a45c8f37dd933338371993d62e04c556cecbb74
			}

		}
		email.close();

		return result;
	}

	private Photo queryPhoto(ContentResolver resolver, int contactId,
			boolean queryData) {
		final String[] contactsProjection = new String[] { Contacts.PHOTO_ID };
		final String[] photoProjection = new String[] { CommonDataKinds.Photo.PHOTO };

		Photo photo = null;

		Cursor contactsCursor = null;
		Cursor photoCursor = null;
		try {
			contactsCursor = resolver.query(Contacts.CONTENT_URI,
					contactsProjection, Contacts._ID + "=?",
					new String[] { String.valueOf(contactId) }, null);
			if (contactsCursor.moveToFirst()) {
				final String photoId = contactsCursor.getString(contactsCursor
						.getColumnIndex(Contacts.PHOTO_ID));

				if (photoId != null && queryData) {
					photo = new Photo(Integer.valueOf(photoId));

					photoCursor = resolver.query(Data.CONTENT_URI,
							photoProjection, Data._ID + "=?",
							new String[] { photoId }, null);

					if (photoCursor.moveToFirst()) {
						byte[] photoBlob = photoCursor.getBlob(photoCursor
								.getColumnIndex(CommonDataKinds.Photo.PHOTO));
						photo.setData(photoBlob);
					}
					photoCursor.close();
				} else {
					photo = null;
				}
			}
		} catch (Exception ex) {
			photo = null;
		} finally {
			if (contactsCursor != null) {
				contactsCursor.close();
			}
			if (photoCursor != null) {
				photoCursor.close();
			}
		}
		return photo;
	}

}
