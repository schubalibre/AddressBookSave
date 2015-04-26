package classes;

import interfaces.AddressBookInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import exceptions.DetailsNotFoundException;
import exceptions.DuplicateKeyException;

public class AddressBook implements AddressBookInterface {

	/*
	 * Die Idee des AddressBook ist es Kontakte mit zwei Schlüsseln zu
	 * speichern. Damit wir dies bewerkstelligen können speichern wir die
	 * Kontaktdaten zwei mal in namesMap ab. Somit kann unter dem Namen und
	 * unter dem Nachnamen gesucht werden.
	 * 
	 * robert -> Robert, Dziuba, 123456789, ..... dziuba -> Robert, Dziuba,
	 * 123456789, .....
	 * 
	 * Immer daran denken das man die ganze Maschine von außen nicht sieht ;)
	 */

	// eine Maps für name und lastName
	private Map<String, ContactDetails> namesMap;
	
	public AddressBook() {
		namesMap = new TreeMap<>();
	}
	
	public AddressBook(AddressBook obj) {
		namesMap = obj.namesMap;
	}
	
	@Override
	public ContactDetails getDetails(String name, String lastname, String phone) throws DetailsNotFoundException {
		
		String key = new AddressBookKey(name, lastname, phone).generateKey();

		if (!this.keyInUse(key))
			throw new DetailsNotFoundException("Leider konnten keine Kontakte mit dem Namen '" + name + "' gefunden werden!");
		// wenn ein Eintrag bei namesMap existiert wird dieser zurück gegeben,
		// sonst null
		return namesMap.get(key);
	}

	@Override
	public boolean keyInUse(String key) {
		// key wird hier aufbereitet trim toLowerCase
		key = this.getCleanKey(key);
		// es wird geschaut, ob namesMap den key besitzt (true oder false)
		return namesMap.containsKey(key);
	}

	@Override
	public void addDetails(ContactDetails details) {
		if (details != null) {

			String key = new AddressBookKey(details).generateKey();
			
			// Kontrolle ob der name oder lastName schon benutzt wird
			if (this.keyInUse(key))
				throw new DuplicateKeyException();
			// alles klar wir kreieren die zwei Elemente für unsere Map
			namesMap.put(key, details);
		}
	}
	
	@Override
	public void changeDetails(String oldKey, ContactDetails details) {
		// oldKey wird hier aufbereitet trim toLowerCase
		oldKey = this.getCleanKey(oldKey);
		// wenn der Eintrag existiert.....
		if (this.keyInUse(oldKey) && details!= null) {
			// wir löschen als erstes die alten Einträge
			this.removeDetails(oldKey);

			// dann nutzten wir unsere Methode addDetails um den geänderten
			// Eintrag neu einzutragen
			this.addDetails(details);

			/*
			 * WICHTIG! um unsere Daten konsistent zu halten verändern wir sie
			 * nicht sondern löschen die alten Einträge erst und kreieren dann
			 * den neuen Eintrag neu. Damit bleiben beide Maps konsistent
			 */
		}
	}

	@Override
	public ContactDetails[] search(String keyPrefix) {

		keyPrefix = this.getCleanKey(keyPrefix);

		if (keyPrefix.length() > 0) {
			List<ContactDetails> matchedDetails = new ArrayList<ContactDetails>();
			for (String key : namesMap.keySet()) {
				if (key.contains(keyPrefix)) {
					String[] keyArray = key.split("::");
					matchedDetails.add(this.getDetails(keyArray[0],keyArray[1],keyArray[2]));
				}
			}
			return matchedDetails.toArray( new ContactDetails[matchedDetails.size()] );
		}
		return null;
	}
	
	public ContactDetails[] getAllContacts() {
		List<ContactDetails> matchedDetails = new ArrayList<ContactDetails>();
		for (String key : namesMap.keySet()) {
				String[] keyArray = key.split("::");
				matchedDetails.add(this.getDetails(keyArray[0],keyArray[1],keyArray[2]));
		}
		return matchedDetails.toArray( new ContactDetails[matchedDetails.size()] );
	}

	@Override
	public int getNumberOfEntries() {
		// gibt die größe der Liste zurück
		return namesMap.size();
	}

	@Override
	public void removeDetails(String key) {
		// wir löschen aus beiden Maps die Einträge
		if (this.keyInUse(key)) {
			// und löschen mit name und lastName die Einträge aus Maps
			namesMap.remove(key);
		}
	}

	private String getCleanKey(String key) {
		if (key != null && !key.isEmpty()) {
			return key.trim().toLowerCase();
		}
		return key;
	}
	
	public String toString(){
		
		String allContacts = null;
		for(String key:namesMap.keySet()) {
			allContacts = allContacts + namesMap.get(key).toString();
		}
		
		return allContacts;
	}

}
